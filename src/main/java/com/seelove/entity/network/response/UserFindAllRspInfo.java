package com.seelove.entity.network.response;


import com.seelove.entity.local.user.User;
import com.seelove.entity.local.video.Video;
import com.seelove.entity.network.response.base.ResponseInfo;

import java.util.List;

/**
 * author : L.jinzhu
 * date : 2015/8/12
 * introduce : 响应实体
 */
public class UserFindAllRspInfo extends ResponseInfo {
    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}