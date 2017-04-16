package com.seelove.service;

import com.seelove.common.RequestCode;
import com.seelove.dao.UserDao;
import com.seelove.entity.enums.ResponseType;
import com.seelove.entity.local.user.User;
import com.seelove.entity.network.request.UserCreateActionInfo;
import com.seelove.entity.network.request.UserFindAllActionInfo;
import com.seelove.entity.network.request.UserLoginActionInfo;
import com.seelove.entity.network.response.UserCreateRspInfo;
import com.seelove.entity.network.response.UserFindAllRspInfo;
import com.seelove.entity.network.response.UserLoginRspInfo;
import com.seelove.manager.RongCloudManager;
import io.rong.models.TokenResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private Random random;

    public UserService() {
        random = new Random();
    }

    public UserCreateRspInfo create(UserCreateActionInfo actionInfo) {
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
        List<User> userList = userDao.findAll(actionInfo.getAgeStart(), actionInfo.getAgeEnd(), actionInfo.getSex(), actionInfo.getCityCode());

        UserFindAllRspInfo rspInfo = new UserFindAllRspInfo();
        rspInfo.setActionId(actionInfo.getActionId());
        rspInfo.setStatusCode(ResponseType.SUCCESS.getCode());
        rspInfo.setStatusMsg(ResponseType.SUCCESS.getMessage());
        rspInfo.setUserList(userList);

        return rspInfo;
    }
}

