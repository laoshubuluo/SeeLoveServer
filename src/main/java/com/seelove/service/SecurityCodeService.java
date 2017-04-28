package com.seelove.service;

import com.seelove.dao.SecurityCodeDao;
import com.seelove.entity.network.request.*;
import com.seelove.entity.network.response.*;
import com.seelove.manager.AliDaYuManager;
import com.seelove.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

/**
 * 验证码服务
 *
 * @author L.jinzhu 2017/3/30
 */
@Service
public class SecurityCodeService {

    private static Logger logger = LoggerFactory.getLogger(SecurityCodeService.class);
    @Resource
    private SecurityCodeDao securityCodeDao;

    public SecurityCodeService() {
    }

    public SecurityCodeSendRspInfo create(SecurityCodeSendActionInfo actionInfo) {
        SecurityCodeSendRspInfo rspInfo = new SecurityCodeSendRspInfo();
        if (StringUtil.isNullOrBlank(actionInfo.getPhoneNumber())) {
            // TODO by L.jinzhu for  校验手机号合法性
            rspInfo.initError4Param(actionInfo.getActionId());
            return rspInfo;
        }

        Random random = new Random();
        String code = String.valueOf(random.nextInt(9999));
        String phoneNumber = actionInfo.getPhoneNumber();
        AliDaYuManager.getInstance().sendSMS(phoneNumber, code);
        // 验证码入库
        securityCodeDao.create(phoneNumber, code, actionInfo.getType(), new Date().toString());

        rspInfo.initSuccess(actionInfo.getActionId());
        return rspInfo;
    }
}

