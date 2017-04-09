package com.seelove.controller;

import com.seelove.dao.UserDao;
import com.seelove.entity.enums.ResponseType;
import com.seelove.entity.local.user.User;
import com.seelove.entity.network.request.UserCreateActionInfo;
import com.seelove.entity.network.response.UserCreateRspInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Random;

/**
 * 请求回调接口
 *
 * @author L.jinzhu 2017/3/30
 */
@Controller
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserDao userDao;

    public UserCreateRspInfo create(UserCreateActionInfo actionInfo) {
        Long id = new Random(500).nextLong();
        User user = new User();
        user.setUserId(id);
        user.setNickName("用户 " + id + actionInfo.getUserName());
        user.setWorkName(actionInfo.getDataFromOtherPlatform());
//        userDao.create(user.getUserId(), user.getNickName());

        UserCreateRspInfo rspInfo = new UserCreateRspInfo();
        rspInfo.setActionId(actionInfo.getActionId());
        rspInfo.setStatusCode(ResponseType.SUCCESS.getCode());
        rspInfo.setStatusMsg(ResponseType.SUCCESS.getMessage());
        rspInfo.setUser(user);

        return rspInfo;
    }

//    @RequestMapping(value = "/get", method = {RequestMethod.POST, RequestMethod.GET})
//    @ResponseBody
//    public Map<String, Object> get(@RequestParam(value = "id", required = false) Long id) {
//        logger.info("[---request---] user/get?id = " + id);
//        JSONObject result = new JSONObject();
//        if (null != id) {
//            User user = userDao.findById(id);
//            result.put("userName", user.getName());
//        }
//        result.put(STATUS_SUCCESS, "0");
//        result.put(STATUS_MSG, "success");
//        logger.info("[---response---] success: " + result.toJSONString());
//        return result;
//    }
//


//    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
//    @ResponseBody
//    public Map<String, Object> delete(@RequestParam(value = "id", required = false) Long id) {
//        logger.info("[---request---] user/delete?id = " + id);
//        JSONObject result = new JSONObject();
//        if (null == id) {
//            result.put(STATUS_SUCCESS, "-1");
//            result.put(STATUS_MSG, "请输入待删除用户id");
//        } else {
//            result.put(STATUS_SUCCESS, "0");
//            result.put(STATUS_MSG, "用户删除成功。id = " + id);
//        }
//
//        logger.info("[---response---] success: " + result.toJSONString());
//        return result;
//    }
}

