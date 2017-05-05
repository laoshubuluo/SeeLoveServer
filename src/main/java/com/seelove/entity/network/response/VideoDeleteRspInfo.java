package com.seelove.entity.network.response;

import com.seelove.entity.network.response.base.ResponseInfo;

import java.util.List;

/**
 * author : L.jinzhu
 * date : 2015/8/12
 * introduce : 响应实体
 */
public class VideoDeleteRspInfo extends ResponseInfo {
    private List<Long> videoIdList;

    public List<Long> getVideoIdList() {
        return videoIdList;
    }

    public void setVideoIdList(List<Long> videoIdList) {
        this.videoIdList = videoIdList;
    }
}