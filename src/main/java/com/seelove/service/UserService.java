package com.seelove.service;

import com.seelove.common.RequestCode;
import com.seelove.dao.UserDao;
import com.seelove.dao.VideoDao;
import com.seelove.entity.enums.ResponseType;
import com.seelove.entity.local.user.User;
import com.seelove.entity.local.user.UserDetail;
import com.seelove.entity.local.video.Video;
import com.seelove.entity.network.request.*;
import com.seelove.entity.network.response.*;
import com.seelove.manager.RongCloudManager;
import io.rong.models.TokenResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 用户服务
 *
 * @author L.jinzhu 2017/3/30
 */
@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Resource
    private UserDao userDao;
    @Resource
    private VideoDao videoDao;
    private Random random;

    public UserService() {
        random = new Random();
    }

    public UserCreateRspInfo create(UserCreateActionInfo actionInfo) {
        // TODO by L.jinzhu for id应该通过表索引反馈
        int id = random.nextInt(10000);
        User user = new User();
        user.setUserId(id);
        user.setNickName(actionInfo.getUserName() + id);
        user.setHeadUrl("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4023417989,1385713059&fm=117&gp=0.jpg");
        // 获取融云token
        TokenResult tokenResult = RongCloudManager.getInstance().getToken(String.valueOf(user.getUserId()), user.getNickName(), user.getHeadUrl());
        if (null != tokenResult && RequestCode.TOKEN_SUCCESS == tokenResult.getCode()) {
            user.setToken4RongCloud(tokenResult.getToken());
        } else {
            // TODO by L.jinzhu for 获取token失败
        }
        userDao.create(Long.parseLong(String.valueOf(id)), user.getNickName(), user.getToken4RongCloud(), user.getHeadUrl());

        UserCreateRspInfo rspInfo = new UserCreateRspInfo();
        rspInfo.setActionId(actionInfo.getActionId());
        rspInfo.setStatusCode(ResponseType.SUCCESS.getCode());
        rspInfo.setStatusMsg(ResponseType.SUCCESS.getMessage());
        rspInfo.setUser(user);

        return rspInfo;
    }

    public UserUpdateRspInfo update(UserUpdateActionInfo actionInfo) {
        User user = actionInfo.getUser();
        userDao.update(user.getUserId(), user.getNickName(), user.getToken4RongCloud(), user.getHeadUrl());

        UserUpdateRspInfo rspInfo = new UserUpdateRspInfo();
        rspInfo.setActionId(actionInfo.getActionId());
        rspInfo.setStatusCode(ResponseType.SUCCESS.getCode());
        rspInfo.setStatusMsg(ResponseType.SUCCESS.getMessage());

        return rspInfo;
    }


    public UserLoginRspInfo login(UserLoginActionInfo actionInfo) {
        User user = userDao.findById(actionInfo.getUserId());

        UserLoginRspInfo rspInfo = new UserLoginRspInfo();
        rspInfo.setActionId(actionInfo.getActionId());
        rspInfo.setStatusCode(ResponseType.SUCCESS.getCode());
        rspInfo.setStatusMsg(ResponseType.SUCCESS.getMessage());
        rspInfo.setUser(user);

        return rspInfo;
    }

    public UserFindAllRspInfo findAll(UserFindAllActionInfo actionInfo) {
        List<UserDetail> userDetailList = new ArrayList<>();
        List<User> userList = userDao.findAll(actionInfo.getAgeStart(), actionInfo.getAgeEnd(), actionInfo.getSex(), actionInfo.getCityCode());
        // 拼接数据
        for (User user : userList) {
            UserDetail userDetail = new UserDetail();
            // 綁定用户
            userDetail.setUser(user);
            // 绑定默认视频
            Video defaultVideo = videoDao.findDefault(user.getUserId());
            userDetail.setDefultVideo(defaultVideo);

            userDetailList.add(userDetail);
        }

        UserFindAllRspInfo rspInfo = new UserFindAllRspInfo();
        rspInfo.setActionId(actionInfo.getActionId());
        rspInfo.setStatusCode(ResponseType.SUCCESS.getCode());
        rspInfo.setStatusMsg(ResponseType.SUCCESS.getMessage());
        rspInfo.setUserDetailList(userDetailList);

        return rspInfo;
    }

    public UserFindDetailRspInfo findDetail(UserFindDetailActionInfo actionInfo) {
        UserDetail userDetail = new UserDetail();
        // 綁定用户
        User user = userDao.findById(actionInfo.getUserId());
        userDetail.setUser(user);
        // 绑定视频列表
        List<Video> videoList = videoDao.findByUser(user.getUserId());
        userDetail.setVideoList(videoList);

        UserFindDetailRspInfo rspInfo = new UserFindDetailRspInfo();
        rspInfo.setActionId(actionInfo.getActionId());
        rspInfo.setStatusCode(ResponseType.SUCCESS.getCode());
        rspInfo.setStatusMsg(ResponseType.SUCCESS.getMessage());
        rspInfo.setUserDetail(userDetail);

        return rspInfo;
    }
}

