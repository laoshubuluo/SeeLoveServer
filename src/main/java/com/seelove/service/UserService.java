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
import com.seelove.utils.StringUtil;
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

    public UserService() {
    }

    public UserCreateRspInfo create(UserCreateActionInfo actionInfo) {
        UserCreateRspInfo rspInfo = new UserCreateRspInfo();
        if (StringUtil.isNullOrBlank(actionInfo.getDataFromOtherPlatform())) {
            rspInfo.initError4Param(actionInfo.getActionId());
            return rspInfo;
        }

        User user = new User();
        user.setNickName(actionInfo.getUserName());
        user.setHeadUrl("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4023417989,1385713059&fm=117&gp=0.jpg");
        userDao.create(user);

        // 获取融云token并更新数据库
        TokenResult tokenResult = RongCloudManager.getInstance().getToken(String.valueOf(user.getUserId()), user.getNickName(), user.getHeadUrl());
        if (null != tokenResult && RequestCode.TOKEN_SUCCESS == tokenResult.getCode()) {
            user.setToken4RongCloud(tokenResult.getToken());
            userDao.updateToken(user);
        } else {
            rspInfo.initError4OtherPlatform(actionInfo.getActionId());
        }

        rspInfo.initSuccess(actionInfo.getActionId());
        rspInfo.setUser(user);
        return rspInfo;
    }

    public UserUpdateRspInfo update(UserUpdateActionInfo actionInfo) {
        UserUpdateRspInfo rspInfo = new UserUpdateRspInfo();
        User user = actionInfo.getUser();
        if (null == user) {
            rspInfo.initError4Param(actionInfo.getActionId());
            return rspInfo;
        }

        userDao.update(actionInfo.getUser());

        rspInfo.initSuccess(actionInfo.getActionId());
        return rspInfo;
    }


    public UserLoginRspInfo login(UserLoginActionInfo actionInfo) {
        User user = userDao.findById(actionInfo.getUserId());

        UserLoginRspInfo rspInfo = new UserLoginRspInfo();
        rspInfo.initSuccess(actionInfo.getActionId());
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
        rspInfo.initSuccess(actionInfo.getActionId());
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
        rspInfo.initSuccess(actionInfo.getActionId());
        rspInfo.setUserDetail(userDetail);
        return rspInfo;
    }
}

