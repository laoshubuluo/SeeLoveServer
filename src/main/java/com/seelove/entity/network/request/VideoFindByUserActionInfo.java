package com.seelove.entity.network.request;


import com.seelove.entity.network.request.base.ActionInfo;

/**
 * author : L.jinzhu
 * date : 2015/8/12
 * introduce : 请求实体
 */
public class VideoFindByUserActionInfo extends ActionInfo {
    private long userId;
    public VideoFindByUserActionInfo(int actionId) {
        super(actionId);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}