package com.yanshang.car.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.UpdateResult;
import com.yanshang.car.bean.*;
import com.yanshang.car.commons.CharacterUtil;
import com.yanshang.car.commons.FileUtil;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.commons.ObjectUtils;
import com.yanshang.car.dao.MongodbDao;
import com.yanshang.car.repositories.*;
import com.yanshang.car.services.AccountService;
import com.yanshang.car.services.CarService;
import com.yanshang.car.services.ServerService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.DataInput;
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
    public NetMessage getRecommend(String brand) {
        List<Car> all = carRepository.findAll(new Specification<Car>() {
            @Override
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                // 品牌拼接
                if (!CharacterUtil.isEmpty(brand)) {
                    predicates.add(criteriaBuilder.equal(root.get("brand").as(String.class), brand));
                }
                // 标签拼接
                predicates.add(criteriaBuilder.like(root.get("label").as(String.class),"%"+CarRepository.LABEL_RECOMMEND+"%"));
//                CriteriaBuilder.In<Object> labels = criteriaBuilder.in(root.get("label"));
//                labels.value(CarRepository.LABEL_RECOMMEND);
//                predicates.add(labels);
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
        if (all != null && !all.isEmpty()) return NetMessage.successNetMessage("",all);
        return NetMessage.failNetMessage("","暂无主打车！！");
    }

    @Override
    public NetMessage saveInfo(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(data);
            JsonNode basic = jsonNode.get("basic");
            Car car = objectMapper.readValue(basic.toString(), Car.class);
            if(carRepository.getByName(car.getName()) == null) {
                HashMap<String,Object> hashMap = objectMapper.readValue(data, HashMap.class);
                car = carRepository.save(car);
                hashMap.put("basic", car);
                hashMap.put("_id",car.getCarid());
                mongoTemplate.save(hashMap,MONGODB_CAR_COLLECTION_NAME);
            } else {
                return NetMessage.failNetMessage("","该汽车信息已存在！！");
            }
            System.out.println(car);
        } catch (IOException e) {
            e.printStackTrace();
            return NetMessage.errorNetMessage();
        }
        return NetMessage.successNetMessage("","保存成功");
    }
    @Override
    public NetMessage publishComments(CarComment comment) {
        String failContent = "";
        if (comment == null) return NetMessage.failNetMessage("", "对象为空！！");
        if (CharacterUtil.isEmpty(comment.getTitle())) return NetMessage.failNetMessage("","缺少标题！！");
        if (CharacterUtil.isEmpty(comment.getContent()))return NetMessage.failNetMessage("","缺少内容！！");

        String carID = comment.getCarid();
        if (CharacterUtil.isEmpty(carID)) return NetMessage.failNetMessage("","缺少评论对象！！");
        Optional<Car> carOptional = carRepository.findById(Integer.parseInt(carID));
        if (carOptional == null || !carOptional.isPresent()) return NetMessage.failNetMessage("","评论对象不存在！！");

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
    public NetMessage getInfo(String identity) {
        if (CharacterUtil.isEmpty(identity)) identity = "0";
        Optional<Car> one = carRepository.findById(Integer.parseInt(identity));
        if(one != null && one.isPresent()) {
            return NetMessage.successNetMessage("",one.get());
        }
        return NetMessage.failNetMessage("","暂无该款汽车！！");
    }

    @Override
    public NetMessage getInfos(HashMap<String, String> data) {
        String no = data.get("no");
        String size = data.get("size");
        if (CharacterUtil.isEmpty(no) || CharacterUtil.isEmpty(size)) return NetMessage.failNetMessage("","需要获取数据定位参数！！");
        QPageRequest qPageRequest = new QPageRequest(Integer.parseInt(no),Integer.parseInt(size));
        Page<Car> all = carRepository.findAll(new Specification<Car>() {
            @Override
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                String label = data.get("label");
                if (!CharacterUtil.isEmpty(label)) {
                    list.add(criteriaBuilder.like(root.get("").as(String.class),"%"+label+"%"));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        }, qPageRequest);

        return (all.isEmpty()) ?
                NetMessage.failNetMessage("","没有您需要的信息！！"):
                NetMessage.successNetMessage("",all);
    }

    @Override
    public NetMessage getTypes() {
        Map<String,String> map = new HashMap<>();
        map.put("type","car_label");
        return serverService.codes(map);
    }

    @Override
    public NetMessage getDetails(String identity) {
        if (CharacterUtil.isEmpty(identity)) identity = "0";
        Query query = new Query(Criteria.where("_id").in(identity,Integer.parseInt(identity)));
        Object o = mongoTemplate.findOne(query,Object.class,MONGODB_CAR_COLLECTION_NAME);
        if(o != null) {
            return NetMessage.successNetMessage("",o);
        }
        return NetMessage.failNetMessage("","没有您需要的汽车信息！！");
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

        Optional<Car> carOptional = carRepository.findById(Integer.parseInt(carCart.getCarid()));
        if (!carOptional.isPresent()) return NetMessage.failNetMessage("","没有该款汽车！！");
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
        if (info.getStatus() == NetMessage.FAIl) return info;
        Car car = (Car) info.getContent();
        car.setLabel(label);
        carRepository.save(car);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage upColors(String carid, String color, MultipartFile file) {
        NetMessage info = check(carid);
        if (info.getStatus() == NetMessage.FAIl) return info;

        HashMap<String,Object> content = (HashMap<String,Object>)info.getContent();
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
        content.put(color,filePath);
        mongoTemplate.save(content, MONGODB_CAR_IMAGE_COLLECTION_NAME);
        return NetMessage.successNetMessage("","上传成功！！");
    }

    @Override
    public NetMessage getImages(String carid) {
        Query query = new Query(Criteria.where("_id").in(carid, Integer.parseInt(carid)));
        HashMap<String,Object> map = mongoTemplate.findOne(query, HashMap.class, MONGODB_CAR_IMAGE_COLLECTION_NAME);
        return  (map == null) ?
                NetMessage.failNetMessage("","没有您需要的信息！！"):
                NetMessage.successNetMessage("",map);
    }

    @Override
    public NetMessage upImage(String carid, MultipartFile... file) {
        if (file == null || file.length <= 0) return NetMessage.failNetMessage("","请上传汽车图片！！");
        NetMessage info = check(carid);
        if (info.getStatus() == NetMessage.FAIl) return info;
        HashMap<String,Object> content = (HashMap<String,Object>)info.getContent();
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
        content.put("images",fileNames);
        mongoTemplate.save(content,MONGODB_CAR_IMAGE_COLLECTION_NAME);
        return NetMessage.successNetMessage("","图片上传成功！！");
    }

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

    @Override
    public NetMessage saveCarPrice(String data) {
        HashMap<String, Object> priceObject = ObjectUtils.string2Map(data);
        String carid = priceObject.get("carid") + "";

        NetMessage info = getInfo(carid);
        priceObject.put("_id",carid);
        if (info.getStatus() == NetMessage.FAIl) return info;
        mongoTemplate.save(priceObject, MONGODB_CAR_PRICE_COLLECTION_NAME);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage getCarPrice(String carid) {
        Criteria criteria = Criteria.where("_id").in(carid,Integer.parseInt(carid));
        Query query = new Query(criteria);
        Object one = mongoTemplate.findOne(query, Object.class, MONGODB_CAR_PRICE_COLLECTION_NAME);
        if (one != null) return NetMessage.successNetMessage("",one);
        return NetMessage.failNetMessage("","暂无需要信息！！");
    }

    @Override
    public NetMessage addRentOrder(RentOrder rentOrder) {
        Optional<Car> byId = carRepository.findById(Integer.parseInt(rentOrder.getCarid()));
        NetMessage info = getInfo(rentOrder.getCarid());
        if (info.getStatus() == NetMessage.FAIl) return info;

       info = accountService.getUser(rentOrder.getUserid());
       if (info.getStatus() == NetMessage.FAIl)return info;

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
        if (info.getStatus() == NetMessage.FAIl) return info;
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

    @Value("${basic.project.img.home}")
    private String IMG_PATH;
    private String img_brand_path = "/brands";
    private String img_car_path = "/car";
    @Autowired
    private CarBrandRepository carBrandRepository;
    @Autowired
    private CarCommentRepository carCommentRepository;
    @Autowired
    private CarRepository carRepository;
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
    private ServerService serverService;
}
