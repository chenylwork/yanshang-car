package com.yanshang.car.services.impl;

import com.yanshang.car.bean.Code;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.repositories.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @ClassName ServerService
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/31- 17:18
 * @Version 1.0
 **/
@Service("serverService")
public class ServerServiceImpl implements com.yanshang.car.services.ServerService {


    @Override
    public NetMessage codes(Map<String, String> data) {
        String type = data.get("type");
        List<Code> codes = codeRepository.getByType(type);
        return  (codes == null || codes.isEmpty()) ?
                NetMessage.failNetMessage("","没有您需要的数据！！"):
                NetMessage.successNetMessage("",codes);
    }
    @Autowired
    private CodeRepository codeRepository;
}
