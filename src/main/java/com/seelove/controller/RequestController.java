package com.seelove.controller;

import com.alibaba.fastjson.JSONObject;
import com.seelove.common.Constant;
import com.seelove.common.RequestCode;
import com.seelove.entity.enums.ResponseType;
import com.seelove.entity.network.request.UserCreateActionInfo;
import com.seelove.entity.network.request.UserFindAllActionInfo;
import com.seelove.entity.network.request.UserLoginActionInfo;
import com.seelove.entity.network.request.VideoFindByUserActionInfo;
import com.seelove.entity.network.request.base.RequestInfo;
import com.seelove.entity.network.response.base.ResponseInfo;
import com.seelove.service.UserService;
import com.seelove.service.VideoService;
import com.seelove.utils.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 请求回调接口
 *
 * @author L.jinzhu 2017/3/30
 */
@Controller
@RequestMapping("/seelove")
public class RequestController {

    private static Logger logger = LoggerFactory.getLogger(RequestController.class);
    @Resource
    private UserService userService;
    @Resource
    private VideoService videoService;

    @RequestMapping(value = "/request", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String test(@RequestBody(required = false) String json) {
        logger.info(Constant.LOG_REQUEST + json);

        RequestInfo requestInfo = GsonUtil.fromJson(json, RequestInfo.class);
        ResponseInfo response = null;
        // 请求解析异常
        if (null == requestInfo || null == requestInfo.getActionInfo()) {
            response = new ResponseInfo();
            response.setActionId(0);
            response.setStatusCode(ResponseType.PARAM_ERROR.getCode());
            response.setStatusMsg(ResponseType.PARAM_ERROR.getMessage());

            logger.info(Constant.LOG_RESPONSE + ResponseType.PARAM_ERROR.getMessage() + ": " + json);
            return GsonUtil.toJson(response);
        }
        // 请求解析正常
        JSONObject jsonObject = JSONObject.parseObject(json);
        String actionInfoStr = jsonObject.getString("actionInfo");
        switch (requestInfo.getActionInfo().getActionId()) {
            // 用户创建
            case RequestCode.USER_CREATE:
                UserCreateActionInfo userCreateActionInfo = GsonUtil.fromJson(actionInfoStr, UserCreateActionInfo.class);
                response = userService.create(userCreateActionInfo);
                break;
            // 用户登录
            case RequestCode.USER_LOGIN:
                UserLoginActionInfo userLoginActionInfo = GsonUtil.fromJson(actionInfoStr, UserLoginActionInfo.class);
                response = userService.login(userLoginActionInfo);
                break;
            // 获取所有用户
            case RequestCode.USER_FIND_ALL:
                UserFindAllActionInfo userFindAllActionInfo = GsonUtil.fromJson(actionInfoStr, UserFindAllActionInfo.class);
                response = userService.findAll(userFindAllActionInfo);
                break;
            // 获取用户视频
            case RequestCode.VIDEO_FIND_BY_USER:
                VideoFindByUserActionInfo videoFindByUserActionInfo = GsonUtil.fromJson(actionInfoStr, VideoFindByUserActionInfo.class);
                response = videoService.findByUser(videoFindByUserActionInfo);
                break;
            default:
                break;
        }
        logger.info(Constant.LOG_RESPONSE + ResponseType.SUCCESS.getMessage() + ": " + response.toString());
        return GsonUtil.toJson(response);
    }
}

