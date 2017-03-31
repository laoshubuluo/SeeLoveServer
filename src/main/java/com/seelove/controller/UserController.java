package com.seelove.controller;

import com.alibaba.fastjson.JSONObject;
import com.seelove.dao.UserDao;
import com.seelove.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 请求回调接口
 * @author L.jinzhu 2017/3/30
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final String STATUS_CODE = "statusCode";
    private static final String STATUS_MSG = "statusMsg";
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserDao userDao;

    @RequestMapping(value = "/get", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> get(@RequestParam(value = "id", required = false) Long id) {
        logger.info("[---request---] user/get?id = " + id);
        JSONObject result = new JSONObject();
        if (null != id) {
            User user = userDao.findById(id);
            result.put("userName", user.getName());
        }
        result.put(STATUS_CODE, "0");
        result.put(STATUS_MSG, "success");
        logger.info("[---response---] success: " + result.toJSONString());
        return result;
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> delete(@RequestParam(value = "id", required = false) Long id) {
        logger.info("[---request---] user/delete?id = " + id);
        JSONObject result = new JSONObject();
        if (null == id) {
            result.put(STATUS_CODE, "-1");
            result.put(STATUS_MSG, "请输入待删除用户id");
        } else {
            result.put(STATUS_CODE, "0");
            result.put(STATUS_MSG, "用户删除成功。id = " + id);
        }

        logger.info("[---response---] success: " + result.toJSONString());
        return result;
    }
}

