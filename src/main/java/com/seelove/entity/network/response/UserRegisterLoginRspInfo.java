package com.seelove.entity.network.response;


import com.seelove.entity.local.user.User;
import com.seelove.entity.network.response.base.ResponseInfo;

/**
 * author : L.jinzhu
 * date : 2015/8/12
 * introduce : 响应实体
 */
public class UserRegisterLoginRspInfo extends ResponseInfo {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserRegisterLoginRspInfo{" +
                "actionId=" + actionId +
                ", statusCode=" + statusCode +
                ", statusMsg='" + statusMsg + '\'' +
                '}';
    }
}