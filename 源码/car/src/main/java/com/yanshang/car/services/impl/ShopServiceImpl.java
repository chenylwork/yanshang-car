package com.yanshang.car.services.impl;

import com.yanshang.car.bean.Goods;
import com.yanshang.car.bean.ShoppingAddress;
import com.yanshang.car.bean.ShoppingCart;
import com.yanshang.car.commons.CharacterUtil;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.commons.ObjectUtils;
import com.yanshang.car.services.ShopService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/*
 * @ClassName ShopServiceImpl
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/2- 16:57
 * @Version 1.0
 **/
@Service("shopService")
public class ShopServiceImpl implements ShopService {

    @Override
    public NetMessage saveGoods(String goods) {
        HashMap<String, Object> map = ObjectUtils.string2Map(goods);
        String id = (String)map.get("_id");
        if (CharacterUtil.isEmpty(id)) { // 添加
            String name = (String) map.get("name");
            if (CharacterUtil.isEmpty(name)) return NetMessage.failNetMessage("","缺少商品名称参数！！");
            id = DigestUtils.md5Hex(name);
            map.put("_id",id);
        }
        mongoTemplate.save(map);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage getGoods(Map<String, String> data) {
        int no = 0;
        int size = 10;
        String goodsis = data.get("goodsid");
        if (CharacterUtil.isEmpty(goodsis))
        return null;
    }

    @Override
    public NetMessage keyword() {
        return null;
    }

    @Override
    public NetMessage saveShoppingCart(ShoppingCart shoppingCart) {
        return null;
    }

    @Override
    public NetMessage getShoppingCart(Map<String, String> data) {
        return null;
    }

    @Override
    public NetMessage saveShoppingAddress(ShoppingAddress shoppingAddress) {
        return null;
    }

    @Override
    public NetMessage getShoppingAddress(Map<String, String> data) {
        return null;
    }
    @Autowired
    private MongoTemplate mongoTemplate;
}
