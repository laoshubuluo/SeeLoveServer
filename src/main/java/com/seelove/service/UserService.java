package com.seelove.service;

import com.seelove.common.RequestCode;
import com.seelove.dao.SecurityCodeDao;
import com.seelove.dao.UserDao;
import com.seelove.dao.VideoDao;
import com.seelove.entity.enums.ResponseType;
import com.seelove.entity.local.system.SecurityCode;
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
    @Resource
    private SecurityCodeDao securityCodeDao;

    public UserService() {
    }

    public UserRegisterLoginRspInfo login(UserRegisterLoginActionInfo actionInfo) {
        UserRegisterLoginRspInfo rspInfo = new UserRegisterLoginRspInfo();
        if (0 == actionInfo.getAccountType()) {
            rspInfo.initError4Param(actionInfo.getActionId());
            return rspInfo;
        }
        // 查询是否已经存在用户
        User user = null;
        UserDetail userDetail = null;
        switch (actionInfo.getAccountType()) {
            case User.ACCOUNT_TYPE_PHONE:
                // 验证码校验失败
                SecurityCode securityCode = securityCodeDao.find(actionInfo.getPhoneNumber(), SecurityCode.CODE_TYPE_REGISTER_LOGIN, actionInfo.getCode());
                if (null == securityCode) {
                    rspInfo.initError(actionInfo.getActionId(), ResponseType.ERROR_4_SECRITY_CODE_ERROR);
                    return rspInfo;
                }
                // 验证码校验成功
                user = userDao.findByAccount(User.ACCOUNT_TYPE_PHONE, actionInfo.getPhoneNumber());
                // 清理同类型旧验证码
                securityCodeDao.delete(actionInfo.getPhoneNumber(), SecurityCode.CODE_TYPE_REGISTER_LOGIN);
                break;
            case User.ACCOUNT_TYPE_WECHAT:
                user = userDao.findByAccount(User.ACCOUNT_TYPE_WECHAT, actionInfo.getDataFromOtherPlatform());
                break;
            case User.ACCOUNT_TYPE_QQ:
                user = userDao.findByAccount(User.ACCOUNT_TYPE_QQ, actionInfo.getDataFromOtherPlatform());
                break;
        }

        // 存在用户，登录
        if (null != user) {
            userDetail = new UserDetail();
            userDetail.setUser(user);
            List<Video> videoList = videoDao.findByUser(user.getUserId());
            userDetail.setVideoList(videoList);

            rspInfo.initSuccess(actionInfo.getActionId());
            rspInfo.setUserDetail(userDetail);
            return rspInfo;
        }
        // 不存在用户，注册
        else {
            user = new User();
            switch (actionInfo.getAccountType()) {
                case User.ACCOUNT_TYPE_PHONE:
                    user.setAccountType(User.ACCOUNT_TYPE_PHONE);
                    user.setAccount(actionInfo.getPhoneNumber());
                    user.setNickName(actionInfo.getPhoneNumber());
                    break;
                case User.ACCOUNT_TYPE_WECHAT:
                    //  TODO by L.jinzhu  for 带解析
                    user.setAccountType(User.ACCOUNT_TYPE_WECHAT);
                    user.setAccount(actionInfo.getDataFromOtherPlatform());
                    user.setNickName(actionInfo.getDataFromOtherPlatform());
                    break;
                case User.ACCOUNT_TYPE_QQ:
                    //  TODO by L.jinzhu  for 带解析
                    user.setAccountType(User.ACCOUNT_TYPE_QQ);
                    user.setAccount(actionInfo.getDataFromOtherPlatform());
                    user.setNickName(actionInfo.getDataFromOtherPlatform());
                    break;
            }
            userDao.create(user);

            // 获取融云token并更新数据库
            user.setHeadUrl(StringUtil.isNullOrBlank(user.getHeadUrl()) ? "temp.jpg" : user.getHeadUrl());
            TokenResult tokenResult = RongCloudManager.getInstance().getToken(String.valueOf(user.getUserId()), user.getNickName(), user.getHeadUrl());
            if (null != tokenResult && RequestCode.TOKEN_SUCCESS == tokenResult.getCode()) {
                user.setToken4RongCloud(tokenResult.getToken());
                userDao.updateToken(user);
            } else {
                rspInfo.initError4OtherPlatform(actionInfo.getActionId());
                return rspInfo;
            }

            userDetail = new UserDetail();
            userDetail.setUser(user);

            rspInfo.initSuccess(actionInfo.getActionId());
            rspInfo.setUserDetail(userDetail);
            return rspInfo;
        }
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
            if (null == defaultVideo) {
                continue;// 没有默认视频的用户不予展示
            }
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

