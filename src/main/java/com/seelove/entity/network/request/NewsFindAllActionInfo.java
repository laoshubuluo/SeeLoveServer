package com.seelove.entity.network.request;


import com.seelove.entity.network.request.base.ActionInfo;

/**
 * author : L.jinzhu
 * date : 2015/8/12
 * introduce : 请求实体
 */
public class NewsFindAllActionInfo extends ActionInfo {

    private long userId; // 用户id,唯一标示

    public NewsFindAllActionInfo(int actionId, long userId) {
        super(actionId);
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }}