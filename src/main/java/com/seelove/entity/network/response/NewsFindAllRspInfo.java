package com.seelove.entity.network.response;

import com.seelove.entity.local.user.UserDetail;
import com.seelove.entity.network.response.base.ResponseInfo;

import java.util.List;

/**
 * author : L.jinzhu
 * date : 2015/8/12
 * introduce : 响应实体
 */
public class NewsFindAllRspInfo extends ResponseInfo {
    private List<UserDetail> userDetailList;

    public List<UserDetail> getUserDetailList() {
        return userDetailList;
    }

    public void setUserDetailList(List<UserDetail> userDetailList) {
        this.userDetailList = userDetailList;
    }
}