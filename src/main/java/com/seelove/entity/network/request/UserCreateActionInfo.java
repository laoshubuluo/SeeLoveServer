package com.seelove.entity.network.request;


import com.seelove.entity.network.request.base.ActionInfo;

/**
 * author : L.jinzhu
 * date : 2015/8/12
 * introduce : 请求实体
 */
public class UserCreateActionInfo extends ActionInfo {
    private String userName;
    private String dataFromOtherPlatform;// 第三方平台返回的用户信息体

    public UserCreateActionInfo(int actionId) {
        super(actionId);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDataFromOtherPlatform() {
        return dataFromOtherPlatform;
    }

    public void setDataFromOtherPlatform(String dataFromOtherPlatform) {
        this.dataFromOtherPlatform = dataFromOtherPlatform;
    }
}