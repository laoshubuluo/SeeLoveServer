package com.seelove.manager;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import io.rong.RongCloud;
import io.rong.models.TokenResult;

/**
 * Created by liangjinzhu on 17/4/10.
 * 阿里大鱼管理器
 */
public class AliDaYuManager {
    String serverUrl = "http://gw.api.taobao.com/router/rest";
    String appKey = "23778669"; // 可替换为您的沙箱环境应用的AppKey
    String appSecret = "586aabdc52d57440a1d46edd9dad8c23"; // 可替换为您的沙箱环境应用的AppSecret

    private TaobaoClient client;

    private static AliDaYuManager instance;

    public AliDaYuManager() {
        client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
    }

    //单例模式：同步锁，保证线程安全，双重检查，避免同步锁引起的性能问题
    public static AliDaYuManager getInstance() {
        if (instance == null) {
            synchronized (AliDaYuManager.class) {
                if (instance == null) {
                    instance = new AliDaYuManager();
                }
            }
        }
        return instance;
    }

    /**
     * 发送消息
     */
    public AlibabaAliqinFcSmsNumSendResponse sendSMS(String phoneNumber, String code) {
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend(phoneNumber);// 透传参数，非必填
        req.setSmsType("normal");
        req.setSmsFreeSignName("视爱科技");
        req.setSmsParamString("{\"code\":\"" + code + "\"}");
        req.setRecNum(phoneNumber);
        req.setSmsTemplateCode("SMS_63925179");
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("send sms : " + rsp.getBody());
        return rsp;
    }
}
