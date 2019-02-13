package com.yanshang.car.services.impl;

import com.yanshang.car.bean.*;
import com.yanshang.car.bean.view.AccountActivityView;
import com.yanshang.car.commons.FileUtil;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.config.ValueBean;
import com.yanshang.car.repositories.*;
import com.yanshang.car.services.AccountService;
import com.yanshang.car.services.ServerService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/*
 * @ClassName ServerService
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/31- 17:18
 * @Version 1.0
 **/
@Service("serverService")
public class ServerServiceImpl implements ServerService {
    private static String ACTIVITY_PATH = "/activity/";
    @Override
    public NetMessage codes(Map<String, String> data) {
        String type = data.get("type");
        List<Code> codes = codeRepository.getByType(type);
        return  (codes == null || codes.isEmpty()) ?
                NetMessage.failNetMessage("","没有您需要的数据！！"):
                NetMessage.successNetMessage("",codes);
    }

    @Override
    public NetMessage saveActivity(Activity activity, MultipartFile... files) {
        if (files != null && files.length > 0) {
            String title = activity.getTitle();
            String md5Hex = DigestUtils.md5Hex(title);
            StringBuilder image = new StringBuilder();
            List<String> filePathList = new ArrayList<>();
            for (int i=0; i < files.length; i++) {
                String fileName = FileUtil.getFileName(files[i],md5Hex+i);
                String filePath = ValueBean.IMG_PATH + ACTIVITY_PATH + fileName;
                File file = FileUtil.createFile(filePath);

                try {
                    files[i].transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    FileUtil.removeFilesByPath(filePathList);
                }
                image.append(ACTIVITY_PATH + fileName+";");
                filePathList.add(filePath);
            }
            activity.setImage(image.toString());
        }
        activityRepository.save(activity);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage initActivity(AccountActivity accountActivity) {
        String accountid = accountActivity.getAccountid();
        NetMessage info = accountService.getUser(accountid);
        if (info.getStatus() == NetMessage.FAIl) return info;

        String activityid = accountActivity.getActivityid();
        Optional<Activity> byId = activityRepository.findById(Integer.parseInt(activityid));
        if (!byId.isPresent()) return NetMessage.failNetMessage("","该活动不存在！！");

        accountActivityRepository.save(accountActivity);
        return NetMessage.successNetMessage("","成功加入活动！！");
    }

    @Override
    public NetMessage getActivity(Map<String, String> data) {
        if (data.containsKey("activityid")){
            String activityid = data.get("activityid");
            Optional<Activity> byId = activityRepository.findById(Integer.parseInt(activityid));
            if (byId.isPresent())return NetMessage.successNetMessage("",byId.get());
        }
        if (data.containsKey("accountid")){
            String accountid = data.get("accountid");
            List<AccountActivityView> activityViews = accountActivityVRepository.getByAccountid(accountid);
            if (activityViews != null || !activityViews.isEmpty())
                return NetMessage.successNetMessage("",activityViews);
        }
        if (data == null || data.isEmpty()){
            List<Activity> all = activityRepository.findAll();
            if (all != null && !all.isEmpty())
                return NetMessage.successNetMessage("",all);
        }
        return NetMessage.failNetMessage("","没有您需要的信息！！");
    }

    @Override
    public NetMessage saveInform(Inform inform) {
        inform.setInformid(UUID.randomUUID().toString().replace("-","").toLowerCase());
        String version = redisTemplate.opsForValue().get(INFORM_VERSION_KEY);
        if (version == null || version.equals("")) {
            version = "1";
            redisTemplate.opsForValue().set(INFORM_VERSION_KEY,"1");
        }
        inform.setVersion(version);
        informRepository.save(inform);
        redisTemplate.opsForValue().increment(INFORM_VERSION_KEY);
        return NetMessage.successNetMessage("","系统通知保存成功！！");
    }

    @Override
    public NetMessage getInform(Map<String, String> data) {
        if (data.containsKey("version")){
            String version = data.get("version");
            List<Inform> informList = informRepository.getByVersionAfter(version);
            if (informList != null && !informList.isEmpty())
                return NetMessage.successNetMessage("",informList);
        }
        if (data.containsKey("accountid")){
            String accountid = data.get("accountid");
            NetMessage info = accountService.getUser(accountid);
            if (info.getStatus() == NetMessage.FAIl) return info;
            Account user = (Account)info.getContent();
            int noticeVersion = user.getInformVersion();
            data.clear();
            data.put("version",noticeVersion+"");
            getInform(data);
        }
        if (data.isEmpty()){
            List<Inform> all = informRepository.findAll();
            if (all != null && !all.isEmpty())
                return NetMessage.successNetMessage("",all);
        }
        return NetMessage.failNetMessage("","暂无未读系统通知！！");
    }

    @Override
    public NetMessage saveNotice(Notice notice) {
        notice.setNoticeid(UUID.randomUUID().toString().replace("-","").toLowerCase());
        String version = redisTemplate.opsForValue().get(NOTICE_VERSION_KEY);
        if (version == null || version.equals("")) {
            version = "1";
            redisTemplate.opsForValue().set(NOTICE_VERSION_KEY,"1");
        }
        notice.setVersion(version);
        noticeRepository.save(notice);
        redisTemplate.opsForValue().increment(NOTICE_VERSION_KEY);
        return NetMessage.successNetMessage("","系统公告保存成功！！");
    }

    @Override
    public NetMessage getNotice(Map<String, String> data) {
        if (data.containsKey("noticeid")){
            String noticeid = data.get("noticeid");
            Optional<Notice> byId = noticeRepository.findById(noticeid);
            if (byId.isPresent()) return NetMessage.successNetMessage("",byId.get());
        }
        if (data.containsKey("version")){
            String version = data.get("version");
            List<Notice> noticeList = noticeRepository.getByVersionAfter(version);
            if (noticeList != null && !noticeList.isEmpty())
                return NetMessage.successNetMessage("",noticeList);
        }
        if (data.containsKey("accountid")){
            String accountid = data.get("accountid");
            NetMessage info = accountService.getUser(accountid);
            if (info.getStatus() == NetMessage.FAIl) return info;
            Account user = (Account)info.getContent();
            int noticeVersion = user.getNoticeVersion();
            data.clear();
            data.put("version","".equals(noticeVersion)? "0":noticeVersion+"");
            getNotice(data);
        }
        if (data.isEmpty()){
            List<Notice> all = noticeRepository.findAll();
            if (all != null && !all.isEmpty())
                return NetMessage.successNetMessage("",all);
        }
        return NetMessage.failNetMessage("","暂无未读系统通知！！");
    }
    /**
     * accountid 用户编号
     * version 消息版本
     */
    @Override
    public NetMessage readInform(Map<String, String> data) {
        return readMessage(data,1);
    }
    /**
     * accountid 用户编号
     * version 消息版本
     */
    @Override
    public NetMessage readNotice(Map<String, String> data) {
        return readMessage(data,2);
    }
    private NetMessage readMessage(Map<String, String> data,int type) {
        if (!data.containsKey("accountid") || !data.containsKey("version"))
            return NetMessage.failNetMessage("","缺少参数！！");
        String accountid = data.get("accountid");
        NetMessage info = accountService.getUser(accountid);
        if (info.getStatus() == NetMessage.FAIl) return info.setContent("用户不存在！！");
        Account user = (Account)info.getContent();
        int version = Integer.parseInt(data.get("version"));
        int oldVersion = 0;
        if (type == 1) {
            oldVersion = user.getInformVersion();
            user.setInformVersion(version);
        }
        if (type == 2) {
            oldVersion = user.getNoticeVersion();
            user.setNoticeVersion(version);
        }
        if (oldVersion >= version) return NetMessage.failNetMessage("","该消息已经读取！！");
        accountRepository.save(user);
        return NetMessage.successNetMessage("","消息读取成功！！");
    }
    @Autowired
    private CodeRepository codeRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private AccountActivityRepository accountActivityRepository;
    @Autowired
    private AccountActivityVRepository accountActivityVRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private InformRepository informRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;
}
