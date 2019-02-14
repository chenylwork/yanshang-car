package com.yanshang.car.services.impl;

import com.yanshang.car.bean.*;
import com.yanshang.car.bean.view.FansView;
import com.yanshang.car.bean.view.RecommendView;
import com.yanshang.car.commons.*;
import com.yanshang.car.config.ValueBean;
import com.yanshang.car.repositories.*;
import com.yanshang.car.services.AccountService;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/*
 * @ClassName AccountServiceImpl
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/15- 15:44
 * @Version 1.0
 **/
@Service
public class AccountServiceImpl implements AccountService {

    private final String headPath = "/"+DigestUtils.md5Hex("user")+"/"+DigestUtils.md5Hex("head");
    @Override
    public NetMessage saveInfo(Account account, MultipartFile file) {
        Integer id = account.getId();
        Optional<Account> byId = accountRepository.findById(id);
        if (id != null && byId.isPresent()) {
            Account saveUser = byId.get();
            if (file != null && !file.isEmpty()) {
                if (!FileUtil.isImage(file)) return NetMessage.failNetMessage("","请上传图片文件");
                String createtime = saveUser.getCreatetime();
                String year = createtime.substring(0,4);
                String month = createtime.substring(5,7);
                String day = createtime.substring(8,10);
                String head = headPath+"/"+year+"/"+month+"/"+day+"/"+id+"";
                String fileName = FileUtil.getFileName(file, System.currentTimeMillis() + "");
                head = head + fileName;
                try {
                    file.transferTo(FileUtil.createFile(ValueBean.IMG_PATH+head));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                saveUser.setHead(head);
            }
            if (!CharacterUtil.isEmpty(account.getName())){
                saveUser.setName(account.getName());
            }
            accountRepository.save(saveUser);
        } else {
            return NetMessage.failNetMessage("","缺少账号唯一标识！！");
        }
        return NetMessage.successNetMessage("","信息录入成功！！");
    }

    @Override
    public NetMessage getInfo(Account account,MPage<Account> mPage) {
        Integer id = account.getId();
        if (id != null) {
            Optional<Account> byId = accountRepository.findById(id);
            if (byId.isPresent()) return NetMessage.successNetMessage("",byId.get());
        }
        int no = mPage.getNo();
        int length = mPage.getLength();
        length = (length == 0) ? 10:length ;
        int start = no*length;
        QPageRequest qPageRequest = new QPageRequest(start,length);
        Page<Account> all = accountRepository.findAll(qPageRequest);
        if (all != null && !all.isEmpty()){
            mPage.setCount(all.getTotalElements());
            mPage.setData(all.getContent());
            return NetMessage.successNetMessage("",mPage);
        }
        return NetMessage.failNetMessage("","没有需要的信息！！");
    }

    @Override
    public NetMessage register(Account account, String code) {
        if (!CharacterUtil.checkPhone(account.getPhone())) {
            return NetMessage.failNetMessage("","请输入正确的手机号！！");
        }
        if (CharacterUtil.isEmpty(account.getPassword())) {
            return NetMessage.failNetMessage("","缺少密码！！");
        }
        if (code != null && !code.equals("")) {
            NetMessage netMessage = PhoneCodeUtil.checkCode(account.getPhone(), code);
            if (netMessage.getStatus() == NetMessage.FAIl) {
                return netMessage;
            }
        }
        account.setPassword(CharacterUtil.passEncode(account.getPassword()));
        try{
            // 生成推荐码
            String referrerCode = redisTemplate.opsForValue().get(REFERRER_CODE_NUM_KEY);
            if (CharacterUtil.isEmpty(referrerCode)) referrerCode = "0";
            account.setCode(CharacterUtil.referrerCode(referrerCode));
            account.setCreatetime(CharacterUtil.dataTime());
            account.setName(account.getPhone());
            accountRepository.save(account);
            redisTemplate.opsForValue().increment(REFERRER_CODE_NUM_KEY);
            return NetMessage.successNetMessage("","注册成功！！");
        } catch (DataIntegrityViolationException exception){
            return NetMessage.failNetMessage("","该账户已注册！！");
        } catch (Exception e) {
            e.printStackTrace();
            return NetMessage.errorNetMessage();
        }
    }

    @Override
    public NetMessage referrer(String phone, String name, String referrerPhone) {
        if (phone == null || phone.equals("")) return NetMessage.failNetMessage("","需要用户手机号参数！！");
        Account account = accountRepository.getByPhone(phone);
        if (account != null || account.getId() == null) return NetMessage.failNetMessage("","该账号不存在！！");
//        account.setReferrerName(name);
//        account.setReferrerPhone(referrerPhone);
        account = accountRepository.save(account);
        try{
            accountRepository.save(account);
            return NetMessage.successNetMessage("","推荐人录入成功！！");
        } catch (Exception e) {
            e.printStackTrace();
            return NetMessage.errorNetMessage();
        }
    }

    @Override
    public NetMessage login(String phone, String password) {
        if (CharacterUtil.isEmpty(phone) || CharacterUtil.isEmpty(password)){
            return NetMessage.failNetMessage("","缺少参数！！");
        }
        Account account = accountRepository.getByPhoneAndPassword(phone, CharacterUtil.passEncode(password));
        if (account != null && account.getId() != null) {
            return NetMessage.successNetMessage("","登录成功");
        } else {
            return NetMessage.successNetMessage("","输入的信息有误！！");
        }
    }

    @Override
    public NetMessage sendCode(String phone) {
        return PhoneCodeUtil.sendCode(phone);
    }

    @Override
    public NetMessage checkCode(String phone, String code) {
        return PhoneCodeUtil.checkCode(phone,code);
    }

    @Override
    public NetMessage changePass(String phone, String password) {
        if (CharacterUtil.isEmpty(phone)) return NetMessage.failNetMessage("","需要手机号参数！！");
        Account account = accountRepository.getByPhone(phone);
        if (account == null || account.getId() == null) {
            return NetMessage.failNetMessage("","该账户还未注册！！");
        }
        try {
            accountRepository.save(account);
        } catch (Exception e){
            e.printStackTrace();
            return NetMessage.errorNetMessage();
        }
        return NetMessage.successNetMessage("","密码修改成功！！");
    }

    @Override
    public NetMessage getUser(String userid) {
        if (userid == null || "".equals(userid)) return NetMessage.failNetMessage("","缺少用户编号！！");
        Optional<Account> byId = accountRepository.findById(Integer.parseInt(userid));
        if (!byId.isPresent()) return NetMessage.failNetMessage("","暂无该用户");
        return NetMessage.successNetMessage("",byId.get());
    }

    @Override
    public NetMessage saveFans(Fans fans) {
        String from = fans.getFromuser();
        String to = fans.getTouser();
        NetMessage info = getUser(from);
        if (info.getStatus() == NetMessage.FAIl)
            return NetMessage.failNetMessage("","被关注的用户不存在！！");
        info = getUser(to);
        if (info.getStatus() == NetMessage.FAIl)
            return NetMessage.failNetMessage("","缺少用户信息！！");

        if (fansRepository.getByFromuserAndTouser(from,to) != null){
            return NetMessage.failNetMessage("","已关注该用户！！");
        }
        fansRepository.save(fans);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage getFans(String accountid, String type) {
        List<FansView> fansList = null;
        if (FANS_FROM.equals(type)) {
            fansList = fansViewRepository.getByFromuser(accountid);
        }
        if (FANS_TO.equals(type)) {
            fansList = fansViewRepository.getByTouser(accountid);
        }
        return (fansList != null && !fansList.isEmpty()) ?
                NetMessage.successNetMessage("",fansList):
                NetMessage.failNetMessage("","没有您需要的信息！！");
    }

    @Override
    public NetMessage getFansSize(String accountid, String type) {
        long fansLength = 0L;
        if (FANS_FROM.equals(type)) {
            fansLength = fansRepository.countByFromuser(accountid);
        }
        if (FANS_TO.equals(type)) {
            fansLength = fansRepository.countByTouser(accountid);
        }
        return NetMessage.successNetMessage("",fansLength);
    }

    @Override
    public NetMessage saveIdea(Idea idea) {
        idea.setIdeaid(UUID.randomUUID().toString().replace("-","").toLowerCase());
        ideaRepository.save(idea);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage getIdea(String ideaid) {
        Object result = null;
        if (CharacterUtil.isEmpty(ideaid)) {
            result = ideaRepository.findAll();
        } else {
            result = ideaRepository.findById(ideaid);
        }
        System.out.println(result);
        return (result != null)?
                NetMessage.successNetMessage("",result):
                NetMessage.failNetMessage("","没有反馈意见！！");
    }

    @Override
    public NetMessage saveRecommend(Recommend recommend) {
        String fromuser = recommend.getFromuser();
        String touser = recommend.getTouser();

        NetMessage info = getUser(fromuser);
        if (info.getStatus() == NetMessage.FAIl) return info.setContent("被推荐人不存在！！");

        info = getUser(touser);
        if (info.getStatus() == NetMessage.FAIl) {
            return info.setContent("推荐人不存在！！");
        } else {
            Account account = (Account) info.getContent();
            String referrerCode = account.getCode();
            String code = recommend.getCode();
            if (!referrerCode.equals(code))
                return NetMessage.failNetMessage("","推荐码错误！！");
        }
        recommendRepository.save(recommend);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage getRecommend(Map<String, String> data) {
        List<RecommendView> recommendViewList = null;
        if (data.containsKey("to")) {
            recommendViewList = recommendViewRepository.getByTouser(data.get("to"));
        }
        if (data.containsKey("from")) {
            recommendViewList = recommendViewRepository.getByFromuser(data.get("from"));
        }
        if (data == null || data.isEmpty())
            return NetMessage.failNetMessage("","缺少请求参数！！");
        return (recommendViewList != null && !recommendViewList.isEmpty()) ?
                NetMessage.successNetMessage("",recommendViewList):
                NetMessage.failNetMessage("","没有需要的信息！！");
    }



    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private FansRepository fansRepository;
    @Autowired
    private FansViewRepository fansViewRepository;
    @Autowired
    private IdeaRepository ideaRepository;
    @Autowired
    private RecommendRepository recommendRepository;
    @Autowired
    private RecommendViewRepository recommendViewRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;
}
