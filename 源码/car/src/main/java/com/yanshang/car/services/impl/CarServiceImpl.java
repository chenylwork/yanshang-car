package com.yanshang.car.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.DBObject;
import com.yanshang.car.bean.*;
import com.yanshang.car.commons.*;
import com.yanshang.car.config.ValueBean;
import com.yanshang.car.repositories.*;
import com.yanshang.car.services.AccountService;
import com.yanshang.car.services.CarService;
import com.yanshang.car.services.ServerService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.querydsl.QPageRequest;
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
 * @ClassName CarServiceImpl
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/17- 9:57
 * @Version 1.0
 **/
@Service("carService")
public class CarServiceImpl implements CarService {


    @Override
    public NetMessage getAllCarBrand() {
        List<CarBrand> all = carBrandRepository.findAll();
        if (all != null && !all.isEmpty()) return NetMessage.successNetMessage("",all);
        return NetMessage.failNetMessage("","暂无品牌！！");
    }

    @Override
    @Deprecated
    public NetMessage getRecommend(String brand) {
//        List<Car> all = carRepository.findAll(new Specification<Car>() {
//            @Override
//            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query,
//                                         CriteriaBuilder criteriaBuilder) {
//                List<Predicate> predicates = new ArrayList<>();
//                // 品牌拼接
//                if (!CharacterUtil.isEmpty(brand)) {
//                    predicates.add(criteriaBuilder.equal(root.get("brand").as(String.class), brand));
//                }
//                // 标签拼接
//                predicates.add(criteriaBuilder.like(root.get("label").as(String.class),"%"+CarRepository.LABEL_RECOMMEND+"%"));
////                CriteriaBuilder.In<Object> labels = criteriaBuilder.in(root.get("label"));
////                labels.value(CarRepository.LABEL_RECOMMEND);
////                predicates.add(labels);
//                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//            }
//        });
//        if (all != null && !all.isEmpty()) return NetMessage.successNetMessage("",all);
        return NetMessage.failNetMessage("","暂无主打车！！");
    }


    @Override
    public NetMessage saveInfo(String data) {
//        JsonNode jsonNode = null;
//        HashMap<String, Object> map = null;
//        try {
//            jsonNode = ObjectUtils.objectMapper.readTree(data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        HashMap<String, Object> basic = ObjectUtils.string2Map(jsonNode.get("basic").toString());

        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> basic = ObjectUtils.string2Map(data);

        String carid = "";
        if (!basic.containsKey("carid")) { // 添加
            if (!basic.containsKey("name") || basic.get("name") == null)
                return NetMessage.failNetMessage("","缺少汽车名称信息！！");

            Query query = new Query(Criteria.where("basic.name").is(basic.get("name")));

            if(mongoTemplate.exists(query, MONGODB_CAR_COLLECTION_NAME)) {
                return NetMessage.failNetMessage("","已有该名称的汽车信息！！");
            }
            carid = DigestUtils.md5Hex(basic.get("name").toString()+System.currentTimeMillis());
            basic.put("carid",carid);
        } else {
            carid = (String) basic.get("carid");
            try{
                map = mongoTemplate.findById(carid, HashMap.class, MONGODB_CAR_COLLECTION_NAME);
            } catch (Exception e){
                return NetMessage.failNetMessage("","不存在该汽车信息！！");
            }
            HashMap<String,Object> oldBasic = (HashMap<String,Object>)map.get("basic");
            basic.forEach((key,value) -> {
                oldBasic.put(key,value);
            });
            basic = oldBasic;
        }
        map.put("_id",carid);
        map.put("basic", basic);
        mongoTemplate.save(map,MONGODB_CAR_COLLECTION_NAME);
        return NetMessage.successNetMessage("","保存成功");
    }

    @Override
    public NetMessage saveDetails(String carid, String name, MultipartFile file) {
        NetMessage info = getInfo(carid);
        if (info.getStatus() == NetMessage.FAIl) return info;
        if (!FileUtil.isImage(file))
            return NetMessage.failNetMessage("","请上传图片文件！！");

        HashMap<String,Object> map = (HashMap)info.getContent();
        String fileName = FileUtil.getFileName(file, DigestUtils.md5Hex(name));
        File saveFile = FileUtil.createFile(ValueBean.IMG_PATH + img_car_path + "/" + carid + "/" + fileName);
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            return NetMessage.errorNetMessage();
        }
        map.put(name,img_car_path+"/"+carid+"/"+fileName);

        mongoTemplate.save(map,MONGODB_CAR_COLLECTION_NAME);
        return NetMessage.successNetMessage("","配置信息保存成功！！");
    }

    @Override
    public NetMessage publishComments(CarComment comment) {
        String failContent = "";
        if (comment == null) return NetMessage.failNetMessage("", "对象为空！！");
        if (CharacterUtil.isEmpty(comment.getTitle())) return NetMessage.failNetMessage("","缺少标题！！");
        if (CharacterUtil.isEmpty(comment.getContent()))return NetMessage.failNetMessage("","缺少内容！！");

        String carID = comment.getCarid();
        if (CharacterUtil.isEmpty(carID)) return NetMessage.failNetMessage("","缺少评论对象！！");
        NetMessage info = getInfo(carID);
        if (info.getStatus() == NetMessage.FAIl) return info;

        String accountID = comment.getObserver();
        if (CharacterUtil.isEmpty(accountID)) return NetMessage.failNetMessage("","缺少评论人！！");

        NetMessage netMessage = accountService.getUser(accountID);
        if (netMessage.getStatus() == NetMessage.FAIl) return netMessage;

        comment.setTime(CharacterUtil.dataTime());
        carCommentRepository.save(comment);
        return NetMessage.successNetMessage("","发表成功！！");
    }

    @Override
    public NetMessage getComments(String carid, int start, int end) {
        QPageRequest pageRequest = new QPageRequest(start,end);
        Page<CarComment> all = carCommentRepository.findAll(new Specification<CarComment>() {
            @Override
            public Predicate toPredicate(Root<CarComment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("carid").as(String.class),carid));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageRequest);
        if (all != null && !all.isEmpty()) {
            return NetMessage.successNetMessage("",all);
        }
        return NetMessage.failNetMessage("","暂无评论！！");
    }

    @Override
    public NetMessage getInfo(String carid) {
        Map<String,String> map = new HashMap<>();
        map.put("carid",carid);
        return getDetails(map);
    }

    @Override
    public NetMessage getInfos(HashMap<String, String> data) {
        return getDetails(data);
    }

    @Override
    public NetMessage getTypes() {
        Map<String,String> map = new HashMap<>();
        map.put("type","car_label");
        return serverService.codes(map);
    }

    @Override
    public NetMessage getDetails(Map<String,String> data) {
        if (data == null || data.isEmpty()) return NetMessage.failNetMessage("","不明查询信息！！");
        Criteria criteria = null;
        Query query = new Query();
        // 获取唯一汽车信息
        String carid = data.get("carid");
        if (carid != null && !"".equals(carid)) {
            criteria = Criteria.where("_id").is(carid);
            query.addCriteria(criteria);
            Object o = mongoTemplate.findOne(query,Object.class,MONGODB_CAR_COLLECTION_NAME);

            return (o != null) ?
                    NetMessage.successNetMessage("",o) :
                    NetMessage.failNetMessage("","没有您需要的信息！！");
        }

        // 获取信息集合
        String no = data.containsKey("no") ? data.get("no") : "0";
        String size = data.containsKey("size") ? data.get("size") : "10";

        String label = data.get("label");
        if (!CharacterUtil.isEmpty(label)){
            criteria = Criteria.where("basic.label").regex("^.*"+label+".*$");
            query.addCriteria(criteria);
        }

        if (data.containsKey("brand")) {
            String brand = data.get("brand");
            criteria = (criteria == null) ?
                    Criteria.where("basic.brand").is(brand):
                    criteria.and("basic.brand").is(brand);
            query.addCriteria(criteria);
        }

        long start = (Integer.parseInt(no) - 1) * Integer.parseInt(size);
        query = query.skip(start).limit(Integer.parseInt(size));
        List<DBObject> list = mongoTemplate.find(query, DBObject.class,MONGODB_CAR_COLLECTION_NAME);
//        Page<DBObject> PageImpl = new PageImpl<>(list,new QPageRequest(Integer.parseInt(no),Integer.parseInt(size)),count);
        if (list != null && !list.isEmpty()) {
            long count = mongoTemplate.count(query, DBObject.class,MONGODB_CAR_COLLECTION_NAME);
            MPage<DBObject> page = new MPage(Integer.parseInt(no), Integer.parseInt(size));
            page.setCount(count);
            page.setData(list);
            return NetMessage.successNetMessage("",page);
        } else {
            return NetMessage.failNetMessage("","没有您需要的信息！！");
        }
    }



    @Override
    @Transactional
    public NetMessage saveBrand(CarBrand carBrand, MultipartFile file) {
        if (file == null || file.isEmpty()) return NetMessage.failNetMessage("","需要品牌标志图片！！！");
        if (carBrand == null) return NetMessage.failNetMessage("","无信息！！");
        String name = carBrand.getName();
        if (name == null || "".equals(name)) return NetMessage.failNetMessage("","请录入品牌名称！！");
        CarBrand oldCarBrand = carBrandRepository.getByName(name);
        if (carBrand.getBrandid() == null) {
            if (oldCarBrand != null && oldCarBrand.getBrandid() != null) return NetMessage.failNetMessage("","该品牌已存在！！");
        }
        String fileName = FileUtil.getFileName(file,name);
//        if (file.getOriginalFilename().lastIndexOf(".") == -1);
//        String originalFilename = file.getOriginalFilename();
//        int i = originalFilename.lastIndexOf(".");
//        String suffix = (i == -1) ? "" : originalFilename.substring(i);
//        String fileName = name+suffix;
        try {
            file.transferTo(FileUtil.createFile(IMG_PATH+img_brand_path+"/"+fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return NetMessage.failNetMessage("","保存失败,品牌图片保存失败！！");
        }
        carBrand.setImages(img_brand_path+"/"+fileName);
        carBrandRepository.save(carBrand);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage saveCarCart(CarCart carCart) {
        String userid = carCart.getUserid();
        NetMessage netMessage = accountService.getUser(userid);
        if (netMessage.getStatus() == NetMessage.FAIl) return netMessage;

        NetMessage info = getInfo(carCart.getCarid());
        if (info.getStatus() == NetMessage.FAIl) return info;
        carCart.setTime(CharacterUtil.dataTime());
        carCartRepository.save(carCart);
        return NetMessage.successNetMessage("","成功加入购物车！");
    }

    @Override
    public NetMessage getCarCart(String userid) {
        List<CarCart> carCarts = carCartRepository.getByUseridOrderByTimeDesc(userid);
        if (carCarts != null && !carCarts.isEmpty()){
            return NetMessage.successNetMessage("",carCarts);
        }
        return NetMessage.failNetMessage("","暂无商品！！");
    }

    @Override
    public NetMessage saveLabel(String carid, String label) {
        NetMessage info = getInfo(carid);
        if (info.getStatus() == NetMessage.FAIl) {
            info.setContent("没有对应的汽车，标签保存失败！！");
            return info;
        }
        HashMap<String, Object> content = (HashMap) info.getContent();
        HashMap<String, Object> basic = (HashMap<String, Object>)content.get("basic");
        basic.put("label",label);
        content.put("basic",basic);
        mongoTemplate.save(content,MONGODB_CAR_COLLECTION_NAME);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage upColors(String carid, String color, MultipartFile file) {
        NetMessage info = getInfo(carid);
        if (info.getStatus() == NetMessage.FAIl) return info.setContent("图片上传失败，没有对应汽车信息！！");

        HashMap<String,Object> content = (HashMap)info.getContent();
        HashMap<String,Object> basic = (HashMap<String, Object>)content.get("basic");
        HashMap<String,Object> colors = (HashMap<String, Object>)basic.get("colors");
        String filePath = img_car_path+"/"+carid+"/"+FileUtil.getFileName(file, DigestUtils.md5Hex(color));
        File file1 = FileUtil.createFile(IMG_PATH + filePath);
        if(!FileUtil.isImage(file)){
            return NetMessage.failNetMessage("","文件非图片文件！！");
        }
        try {
            file.transferTo(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        colors.put(color,filePath);
        mongoTemplate.save(content, MONGODB_CAR_COLLECTION_NAME);
        return NetMessage.successNetMessage("","上传成功！！");
    }

    @Override
    @Deprecated
    public NetMessage getImages(String carid) {
//        Query query = new Query(Criteria.where("_id").in(carid, Integer.parseInt(carid)));
//        HashMap<String,Object> map = mongoTemplate.findOne(query, HashMap.class, MONGODB_CAR_IMAGE_COLLECTION_NAME);
//        return  (map == null) ?
//                NetMessage.failNetMessage("","没有您需要的信息！！"):
//                NetMessage.successNetMessage("",map);
        return null;
    }

    @Override
    public NetMessage upImage(String carid, MultipartFile... file) {
        if (file == null || file.length <= 0) return NetMessage.failNetMessage("","请上传汽车图片！！");
        NetMessage info = getInfo(carid);
        if (info.getStatus() == NetMessage.FAIl) return info.setContent("上传失败，没有对应汽车信息！！");
//        info = check(carid);
        HashMap<String,Object> content = (HashMap)info.getContent();
        HashMap<String,Object> basic = (HashMap<String, Object>)content.get("basic");
        List<String> filePaths = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        for (int i =0; i<file.length; i++){
            String fileName = img_car_path+"/"+carid+"/"+FileUtil.getFileName(file[i], DigestUtils.md5Hex(i+""));
            fileNames.add(fileName);
            String filePath = IMG_PATH + fileName;
            if(!FileUtil.isImage(file[i])){
                FileUtil.removeFilesByPath(filePaths);
                return NetMessage.failNetMessage("","文件有非图片文件！！");
            }
            filePaths.add(filePath);
            try {
                file[i].transferTo(FileUtil.createFile(filePath));
            } catch (Exception e) {
                e.printStackTrace();
                FileUtil.removeFilesByPath(filePaths);
                return NetMessage.failNetMessage("","文件上传异常！！");
            }

        }
        basic.put("images",fileNames);
        mongoTemplate.save(content,MONGODB_CAR_COLLECTION_NAME);
        return NetMessage.successNetMessage("","图片上传成功！！");
    }
    @Deprecated
    private NetMessage check(String carid) {
        NetMessage info = getImages(carid);
        HashMap<String,Object> map = null;
        if (info.getStatus() == NetMessage.FAIl){
            info = getInfo(carid);
            if (info.getStatus() == NetMessage.FAIl) return info;
            map = new HashMap<>();
            map.put("_id",carid);
            info.setContent(map);
        } else {
            map = (HashMap<String, Object>) info.getContent();
            info.setContent(map);
        }
        return info;
    }

    /**
     * 保存汽车价钱信息
     * data.money 裸车价钱
     * data.insurance 保险
     * @param carid 汽车编号
     * @param data 汽车价钱信息
     * @return
     */
    @Override
    public NetMessage saveCarPrice(String carid, Map<String, String> data) {
        NetMessage info = getInfo(carid);
        if (info.getStatus() == NetMessage.FAIl) return info.setContent("价钱信息保存失败，没有该汽车信息！！");
        HashMap<String,Object> content = (HashMap)info.getContent();
        HashMap<String,Object> basic = (HashMap<String, Object>)content.get("basic");

        basic.put("price",data);

        if (info.getStatus() == NetMessage.FAIl) return info;
        mongoTemplate.save(content, MONGODB_CAR_COLLECTION_NAME);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    @Deprecated
    public NetMessage getCarPrice(String carid) {
        Criteria criteria = Criteria.where("_id").in(carid,Integer.parseInt(carid));
        Query query = new Query(criteria);
        Object one = mongoTemplate.findOne(query, Object.class, MONGODB_CAR_PRICE_COLLECTION_NAME);
        if (one != null) return NetMessage.successNetMessage("",one);
        return NetMessage.failNetMessage("","暂无需要信息！！");
    }

    @Override
    public NetMessage saveCarOrder(CarOrder carOrder) {
        String accountid = carOrder.getAccountid();
        NetMessage info = accountService.getUser(accountid);
        if (info.getStatus() == NetMessage.FAIl) return info;

        String carid = carOrder.getCarid();
        info = getInfo(carid);
        if (info.getStatus() == NetMessage.FAIl) return info;
        if ("1".equals(carOrder.getEnd())){
            carOrder.setEnd(CharacterUtil.dataTime());
        }
        if (CharacterUtil.isEmpty(carOrder.getOrderCode())){
            carOrder.setOrderCode(CharacterUtil.orderCode());
            carOrder.setStart(CharacterUtil.dataTime());
        }

        carOrderRepository.save(carOrder);
        return NetMessage.successNetMessage("","订单提交成功！！");
    }

    @Override
    public NetMessage getCarOrder(CarOrder carOrder,MPage<CarOrder> page) {
        String accountid = carOrder.getAccountid();
        if (!CharacterUtil.isEmpty(accountid)){
            List<CarOrder> carOrderList = carOrderRepository.getByAccountid(accountid);
            if (carOrderList != null && !carOrderList.isEmpty())
                return NetMessage.successNetMessage("",carOrderList);
        }
        int no = page.getNo();
        int length = page.getLength();
        length = (length == 0) ? 10 : length;
        int start = no*length;
        QPageRequest pageRequest= new QPageRequest(start,length);
        Page<CarOrder> all = carOrderRepository.findAll(pageRequest);
        if (all != null && !all.isEmpty()){
            MPage mPage = new MPage(no,length,all.getTotalElements(),all.getContent());
            return NetMessage.successNetMessage("",mPage);
        }
        return NetMessage.failNetMessage("","没有您需要的信息！！");
    }

    @Override
    public NetMessage addRentOrder(RentOrder rentOrder) {
        NetMessage info = getInfo(rentOrder.getCarid());
        if (info.getStatus() == NetMessage.FAIl) return info;

       info = accountService.getUser(rentOrder.getUserid());
       if (info.getStatus() == NetMessage.FAIl)return info;

        Optional<RentOrder> byId = carRentOrderRepository.findById(rentOrder.getOrdercode());
        if (!byId.isPresent()) {
            rentOrder.setCreateTime(CharacterUtil.dataTime());
        } else {
            RentOrder oldRentOrder = byId.get();
            rentOrder.setCreateTime(oldRentOrder.getCreateTime());
        }
        carRentOrderRepository.save(rentOrder);
        return NetMessage.successNetMessage("","保存成功");
    }

    @Override
    public NetMessage getRentOrder(RentOrder rentOrder) {
        List<RentOrder> rentOrders = carRentOrderRepository.findAll(new Specification<RentOrder>() {
            @Override
            public Predicate toPredicate(Root<RentOrder> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("userid").as(String.class), rentOrder.getUserid()));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        });
        return  (rentOrders != null && !rentOrders.isEmpty()) ?
                NetMessage.successNetMessage("",rentOrders) :
                NetMessage.failNetMessage("","暂无租车信息！！");
    }

    @Override
    public NetMessage saveTestOrder(TestOrder testOrder) {
        NetMessage info = accountService.getUser(testOrder.getUserid());
        if (info.getStatus() == NetMessage.FAIl) return info;

        info = getInfo(testOrder.getCarid());
        if (info.getStatus() == NetMessage.FAIl) return info.setContent("没有该汽车信息！！");
        Optional<TestOrder> byId = carTestOrderRepository.findById(testOrder.getOrdercode());

        if (!byId.isPresent()) {
            testOrder.setCreateTime(CharacterUtil.dataTime());
        } else {
            TestOrder oldOrder = byId.get();
            testOrder.setCreateTime(oldOrder.getCreateTime());
        }
        carTestOrderRepository.save(testOrder);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage getTestOrders(TestOrder testOrder) {

        List<TestOrder> all = carTestOrderRepository.findAll(new Specification<TestOrder>() {
            @Override
            public Predicate toPredicate(Root<TestOrder> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> list = new ArrayList<>();
                if (!CharacterUtil.isEmpty(testOrder.getUserid())) {
                    list.add(criteriaBuilder.equal(root.get("userid").as(String.class),testOrder.getUserid()));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        });
        return (all != null && !all.isEmpty()) ?
                NetMessage.successNetMessage("",all):
                NetMessage.failNetMessage("","没有您需要的信息！！");
    }

    @Override
    public NetMessage saveCarFans(CarFans carFans) {
        String accountid = carFans.getAccountid();
        NetMessage info = accountService.getUser(accountid);
        if (info.getStatus() == NetMessage.FAIl) return info;
        String carid = carFans.getCarid();
        info = getInfo(carid);
        if (info.getStatus() == NetMessage.FAIl) return info.setContent("关注汽车不存在！！");

        carFansRepository.save(carFans);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage getCarFans(String accountid) {
        List<CarFans> carFansList = carFansRepository.getByAccountid(accountid);
        return (carFansList != null && !carFansList.isEmpty()) ?
                NetMessage.successNetMessage("",carFansList):
                NetMessage.failNetMessage("","没有需要的信息！！");
    }


    @Value("${basic.project.img.home}")
    private String IMG_PATH;
    private String img_brand_path = "/brands";
    private String img_car_path = "/car";
    @Autowired
    private CarBrandRepository carBrandRepository;
    @Autowired
    private CarCommentRepository carCommentRepository;
//    @Autowired
//    private CarRepository carRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CarCartRepository carCartRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CarRentOrderRepository carRentOrderRepository;
    @Autowired
    private CarTestOrderRepository carTestOrderRepository;
    @Autowired
    private CarFansRepository carFansRepository;
    @Autowired
    private ServerService serverService;
    @Autowired
    private CarOrderRepository carOrderRepository;
}
