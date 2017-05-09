package com.seelove.controller;

import com.alibaba.fastjson.JSONObject;
import com.seelove.common.Constant;
import com.seelove.common.RequestCode;
import com.seelove.entity.network.request.*;
import com.seelove.entity.network.request.base.RequestInfo;
import com.seelove.entity.network.response.base.ResponseInfo;
import com.seelove.service.*;
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
    @Resource
    private FollowService followService;
    @Resource
    private NewsService newsService;
    @Resource
    private SecurityCodeService securityCodeService;
    @Resource
    private SystemService systemService;

    @RequestMapping(value = "/request", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String run(@RequestBody(required = false) String json) {
        logger.info(Constant.LOG_REQUEST + json);

        RequestInfo requestInfo = GsonUtil.fromJson(json, RequestInfo.class);
        ResponseInfo response = null;
        // 请求解析异常
        if (null == requestInfo || null == requestInfo.getActionInfo()) {
            response = new ResponseInfo();
            response.initError4Param(0);
            logger.info(Constant.LOG_RESPONSE + ": " + response.toString());
            return GsonUtil.toJson(response);
        }
        // 请求解析正常
        JSONObject jsonObject = JSONObject.parseObject(json);
        String actionInfoStr = jsonObject.getString("actionInfo");
        switch (requestInfo.getActionInfo().getActionId()) {
            // 发送验证码
            case RequestCode.SEND_SECURITY_CODE:
                SecurityCodeSendActionInfo securityCodeSendActionInfo = GsonUtil.fromJson(actionInfoStr, SecurityCodeSendActionInfo.class);
                response = securityCodeService.send(securityCodeSendActionInfo);
                break;
            // 注册登录
            case RequestCode.USER_REGISTER_LOGIN:
                UserRegisterLoginActionInfo userRegisterLoginActionInfo = GsonUtil.fromJson(actionInfoStr, UserRegisterLoginActionInfo.class);
                response = userService.login(userRegisterLoginActionInfo);
                break;
            // 用户更新
            case RequestCode.USER_UPDATE:
                UserUpdateActionInfo userUpdateActionInfo = GsonUtil.fromJson(actionInfoStr, UserUpdateActionInfo.class);
                response = userService.update(userUpdateActionInfo);
                break;
            // 获取所有用户
            case RequestCode.USER_FIND_ALL:
                UserFindAllActionInfo userFindAllActionInfo = GsonUtil.fromJson(actionInfoStr, UserFindAllActionInfo.class);
                response = userService.findAll(userFindAllActionInfo);
                break;
            // 获取用户详情
            case RequestCode.USER_FIND_DETAIL:
                UserFindDetailActionInfo userFindDetailActionInfo = GsonUtil.fromJson(actionInfoStr, UserFindDetailActionInfo.class);
                response = userService.findDetail(userFindDetailActionInfo);
                break;
            // 创建视频
            case RequestCode.VIDEO_CREATE:
                VideoCreateActionInfo videoCreateActionInfo = GsonUtil.fromJson(actionInfoStr, VideoCreateActionInfo.class);
                response = videoService.create(videoCreateActionInfo);
                break;
            // 删除视频
            case RequestCode.VIDEO_DELETE:
                VideoDeleteActionInfo videoDeleteActionInfo = GsonUtil.fromJson(actionInfoStr, VideoDeleteActionInfo.class);
                response = videoService.delete(videoDeleteActionInfo);
                break;
            // 关注、取消关注
            case RequestCode.FOLLOW:
                FollowActionInfo followActionInfo = GsonUtil.fromJson(actionInfoStr, FollowActionInfo.class);
                response = followService.follow(followActionInfo);
                break;
            // 获取user主动关注的人
            case RequestCode.FOLLOW_FIND_BY_USER:
                FollowFindAllActionInfo followFindAllByUserActionInfo = GsonUtil.fromJson(actionInfoStr, FollowFindAllActionInfo.class);
                response = followService.findAllByUserId(followFindAllByUserActionInfo);
                break;
            // 获取关注user的人
            case RequestCode.FOLLOW_FIND_BY_FOLLOWED_USER:
                FollowFindAllActionInfo followFindAllByFollowedUserActionInfo = GsonUtil.fromJson(actionInfoStr, FollowFindAllActionInfo.class);
                response = followService.findAllByFollowedUserId(followFindAllByFollowedUserActionInfo);
                break;
            // 获取动态
            case RequestCode.NEWS_FIND_ALL:
                NewsFindAllActionInfo newsFindAllActionInfo = GsonUtil.fromJson(actionInfoStr, NewsFindAllActionInfo.class);
                response = newsService.findAll(newsFindAllActionInfo);
                break;
            // 最新版本
            case RequestCode.SYSTEM_NEW_VERSION:
                NewVersionActionInfo newVersionActionInfo = GsonUtil.fromJson(actionInfoStr, NewVersionActionInfo.class);
                response = systemService.getNewVersion(newVersionActionInfo);
                break;
            default:
                response = new ResponseInfo();
                response.initError4Param(requestInfo.getActionInfo().getActionId());
                break;
        }
        logger.info(Constant.LOG_RESPONSE + ": " + response.toString());
        return GsonUtil.toJson(response);
    }
}

