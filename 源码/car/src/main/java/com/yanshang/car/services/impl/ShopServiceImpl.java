package com.yanshang.car.services.impl;

import com.yanshang.car.bean.ShoppingAddress;
import com.yanshang.car.bean.ShoppingCart;
import com.yanshang.car.bean.ShoppingOrder;
import com.yanshang.car.bean.ShoppingScore;
import com.yanshang.car.commons.CharacterUtil;
import com.yanshang.car.commons.FileUtil;
import com.yanshang.car.commons.MPage;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.repositories.ShoppingAddressRepository;
import com.yanshang.car.repositories.ShoppingCartRepository;
import com.yanshang.car.repositories.ShoppingOrderRepository;
import com.yanshang.car.repositories.ShoppingScoreRepository;
import com.yanshang.car.services.AccountService;
import com.yanshang.car.services.ShopService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;

/*
 * @ClassName ShopServiceImpl
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/2- 16:57
 * @Version 1.0
 **/
@Service("shopService")
public class ShopServiceImpl implements ShopService {
    @Value("${basic.project.img.home}")
    private String IMG_PATH;
    private String GOODS_PATH = "/goods";

    /**
     * 商品库存数 {@link ShopService#GOODS_COUNT_KEY}
     * 商品名称 name
     * 商品价格 price
     * 商品颜色 colors
     * 商品图片 images
     * @param map
     * @return
     */
    @Override
    public NetMessage saveGoods(Map<String,Object> map, MultipartFile... files) {
        String id = (String)map.get("_id");
        if (CharacterUtil.isEmpty(id)) { // 添加
            String name = (String) map.get("name");
            if (CharacterUtil.isEmpty(name)) return NetMessage.failNetMessage("","缺少商品名称参数！！");
            id = DigestUtils.md5Hex(name);
            map.put("_id",id);
        }
        if (files != null && files.length > 0) {
            boolean isImage = FileUtil.isImage(files);
            if (!isImage) return NetMessage.failNetMessage("","请选择图片上传！！");

            String dirPath = IMG_PATH + GOODS_PATH+"/"+id;
            // 删掉旧图片
            FileUtil.deleteFile(FileUtil.createFile(dirPath));

            List<String> images = new ArrayList<>();
            // 保存新图片
            for(int i=0; i<files.length; i++) {
                String fileName = FileUtil.getFileName(files[i], id + i);
                File file = FileUtil.createFile(dirPath+ "/" + fileName);
                images.add(GOODS_PATH+"/"+id+"/"+fileName);
                try {
                    files[i].transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            map.put("images",images);
        }
        mongoTemplate.save(map,GOODS_COLLECTIONS_NAME);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage getGoods(Map<String, String> data) {
        int no = 0;
        int size = 10;
        String goodsis = data.get("goodsid");
        if (data.containsKey("goodsid")){
            if (!CharacterUtil.isEmpty(goodsis)){
                HashMap<String,Object> goods = mongoTemplate.findById(goodsis, HashMap.class,GOODS_COLLECTIONS_NAME);
                if (goods != null) {
                    return NetMessage.successNetMessage("",goods);
                }
            }
        } else{
            Query query = new Query();
            long count = mongoTemplate.count(query, Object.class, GOODS_COLLECTIONS_NAME);
            long skip = size*no;
            query.skip(skip).limit(size);
            List<Object> objectList = mongoTemplate.find(query, Object.class, GOODS_COLLECTIONS_NAME);
            MPage<Object> page = new MPage<>(no,size,count,objectList);
            if (objectList != null || !objectList.isEmpty()) {
                return NetMessage.successNetMessage("",page);
            }
        }
        return NetMessage.failNetMessage("","没有您需要的信息！！");
    }

    private NetMessage goodsIsExists(String goodsid) {
        Map<String,String> map = new HashMap<>();
        map.put("goodsid",goodsid);
        return getGoods(map);
    }

    @Override
    public NetMessage getKeyword(String keyword) {
        if (CharacterUtil.isEmpty(keyword)) return NetMessage.failNetMessage("","关键字为空");
        Set<ZSetOperations.TypedTuple<String>> zset = redisTemplate.opsForZSet().reverseRangeWithScores(GOODS_KEYWORD_KEY, 0, -1);
        Map<String,String> map = new LinkedHashMap<>();
        zset.forEach(data -> {
            String value = data.getValue();
            double doubleScore = data.getScore();
            int score = (int) doubleScore;
            if (value.indexOf(keyword) != -1) map.put(value,score+"");
        });
        return NetMessage.successNetMessage("",map);
    }

    @Override
    public NetMessage saveKeyword(String keyword) {
        if (CharacterUtil.isEmpty(keyword)) return NetMessage.failNetMessage("","关键字为空");
        redisTemplate.opsForZSet().incrementScore(GOODS_KEYWORD_KEY,keyword,1);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    @Transactional
    @Modifying
    public NetMessage delShoppingCart(String... cartids) {
        for (String cartid : cartids) cartRepository.deleteById(Integer.parseInt(cartid));
        return NetMessage.successNetMessage("","删除成功！！");
    }

    @Override
    public NetMessage saveShoppingCart(ShoppingCart shoppingCart) {
        NetMessage info = accountService.getUser(shoppingCart.getAccountid());
        if(info.getStatus() == NetMessage.FAIl) return info;

        String goodsid = shoppingCart.getGoodsid();
        info = goodsIsExists(goodsid);
        if (info.getStatus() == NetMessage.FAIl) return info.setContent("没有该商品！！");
        shoppingCart.setTime(CharacterUtil.dataTime());
        cartRepository.save(shoppingCart);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage getShoppingCart(Map<String, String> data) {
        String cartid = data.get("cartid");
        if (!CharacterUtil.isEmpty(cartid)) {
            Optional<ShoppingCart> byId = cartRepository.findById(Integer.parseInt(cartid));
            if (byId.isPresent()){
                return NetMessage.successNetMessage("",byId.get());
            }
        }
        String accountid = data.get("accountid");
        if (!CharacterUtil.isEmpty(accountid)) {
            List<ShoppingCart> cartList = cartRepository.getByAccountid(accountid);
            if (cartList != null && !cartList.isEmpty()) {
                return NetMessage.successNetMessage("",cartList);
            }
        }
        return NetMessage.failNetMessage("","没有您需要的信息！！");
    }

    @Override
    public NetMessage saveShoppingAddress(ShoppingAddress shoppingAddress) {
        String accountid = shoppingAddress.getAccountid();
        NetMessage info = accountService.getUser(accountid);
        if (info.getStatus() == NetMessage.FAIl) return info;
        if (shoppingAddress.getDirectionid() == null) { // 保存
            Map<String,String> map = new HashMap<>();
            map.put("accountid",accountid);
            info = getShoppingAddress(map);
            if (info.getStatus() == NetMessage.SUCCESS) {
                List<ShoppingAddress> content = (List<ShoppingAddress>)info.getContent();
                if (content.size() >= 5)
                    return NetMessage.failNetMessage("","收货地址最多只能设置5个！！");
            }
        }
        if (CharacterUtil.isEmpty(shoppingAddress.getPhone()))
            return NetMessage.failNetMessage("","缺少收货电话！！");
        if (CharacterUtil.isEmpty(shoppingAddress.getAddress()))
            return NetMessage.failNetMessage("","缺少详细收货地址！！");
        addressRepository.save(shoppingAddress);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage getShoppingAddress(Map<String, String> data) {
        String accountid = data.get("accountid");
        if (!CharacterUtil.isEmpty(accountid)) {
            List<ShoppingAddress> addressList = addressRepository.getByAccountid(accountid);
            if (addressList != null && !addressList.isEmpty())
                return NetMessage.successNetMessage("",addressList);
        }
        String addressid = data.get("addressid");
        if (!CharacterUtil.isEmpty(addressid)) {
            Optional<ShoppingAddress> byId = addressRepository.findById(Integer.parseInt(addressid));
            if (byId.isPresent()) return NetMessage.successNetMessage("",byId.get());
        }
        return NetMessage.failNetMessage("","没有您需要的信息！！");
    }

    @Override
    @Transactional
    public NetMessage saveOrder(ShoppingOrder order) {
        int oldCount = 0;
        int newCount = 0;
        HashMap<String,Object> goods = null;
        Optional<ShoppingOrder> byId = orderRepository.findById(order.getOrderCode());
        if (!byId.isPresent()) { // 保存
            order.setOrderCode(CharacterUtil.orderCode());
            String accountid = order.getAccountid();
            NetMessage info = accountService.getUser(accountid);
            if(info.getStatus() == NetMessage.FAIl) return info;

            String goodsid = order.getGoodsid();
            info = goodsIsExists(goodsid);
            if (info.getStatus() == NetMessage.FAIl) return info;

            int size = order.getSize();
            goods = (HashMap)info.getContent();
            oldCount = Integer.parseInt((String) goods.get(ShopService.GOODS_COUNT_KEY));
            if (oldCount < size) return NetMessage.failNetMessage("","该商品库存不足！！");
            newCount = oldCount-size;
            goods.put(GOODS_COUNT_KEY,newCount+"");
            saveGoods(goods);
            order.setCreateTime(CharacterUtil.dataTime());
        } else {
            ShoppingOrder oldOrder = byId.get();
            oldOrder.setCreateTime(oldOrder.getCreateTime());
            oldOrder.setPaymentTime(oldOrder.getPaymentTime());
            oldOrder.setSendTime(oldOrder.getSendTime());
            oldOrder.setEndTime(oldOrder.getEndTime());

            if ("1".equals(order.getPaymentTime()))
                order.setPaymentTime(CharacterUtil.dataTime());
            if ("1".equals(order.getSendTime()))
                order.setSendTime(CharacterUtil.dataTime());
            if ("1".equals(order.getEndTime()))
                order.setEndTime(CharacterUtil.dataTime());
        }
        try{
            orderRepository.save(order);
        } catch (Exception e){
            goods.put(GOODS_COUNT_KEY,oldCount+"");
            saveGoods(goods);
            return NetMessage.errorNetMessage();
        }
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage getOrder(Map<String, String> data) {
        String accountid = data.get("accountid");
        if (data.containsKey("accountid")) {
            if (!CharacterUtil.isEmpty(accountid)) {
                List<ShoppingOrder> orderList = orderRepository.getByAccountid(accountid);
                if (orderList != null && !orderList.isEmpty())
                    return NetMessage.successNetMessage("",orderList);
            }
        } else {
            String orderid = data.get("orderid");
            if (CharacterUtil.isEmpty(orderid)) {
                Optional<ShoppingOrder> byId = orderRepository.findById(orderid);
                if (byId.isPresent())return NetMessage.successNetMessage("",byId.get());
            }
        }
        return NetMessage.failNetMessage("","没有您需要的信息！！");
    }

    @Override
    public NetMessage delOrder(Map<String, String> data) {
        String id = data.get("orderid");
        orderRepository.deleteById(id);
        return NetMessage.successNetMessage("","删除成功！！");
    }

    @Override
    public NetMessage saveShoppingScore(ShoppingScore shoppingScore) {
        String accountid = shoppingScore.getAccountid();
        NetMessage info = accountService.getUser(accountid);
        if (info.getStatus() == NetMessage.FAIl) return info;

        shoppingScore.setTime(CharacterUtil.dataTime());
        scoreRepository.save(shoppingScore);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage dayIsSignin(String accountid) {
        List<ShoppingScore> all = scoreRepository.findAll(new Specification<ShoppingScore>() {
            @Override
            public Predicate toPredicate(Root<ShoppingScore> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
//                if ()
                list.add(criteriaBuilder.equal(root.get("accountid").as(String.class), accountid));
                list.add(criteriaBuilder.equal(root.get("name").as(String.class), "签到"));
                String time = CharacterUtil.data();
                list.add(criteriaBuilder.like(root.get("time").as(String.class), "%" + time + "%"));
                return criteriaQuery.where(list.toArray(new Predicate[list.size()])).getRestriction();
            }
        });
        return (all != null && !all.isEmpty()) ?
                NetMessage.successNetMessage("","今日已签到！！"):
                NetMessage.failNetMessage("","今日未签到！！");
    }

    @Override
    public NetMessage getShoppingScore(String accountid) {
        List<ShoppingScore> scoreList = scoreRepository.getByAccountid(accountid);
        if (scoreList != null && !scoreList.isEmpty())
            return NetMessage.successNetMessage("",scoreList);
        return NetMessage.failNetMessage("","没有您需要的信息！！");
    }

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ShoppingCartRepository cartRepository;
    @Autowired
    private ShoppingAddressRepository addressRepository;
    @Autowired
    private ShoppingOrderRepository orderRepository;
    @Autowired
    private ShoppingScoreRepository scoreRepository;

}
