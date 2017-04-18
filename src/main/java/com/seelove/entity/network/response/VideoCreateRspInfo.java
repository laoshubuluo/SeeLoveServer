package com.seelove.entity.network.response;

import com.seelove.entity.local.video.Video;
import com.seelove.entity.network.response.base.ResponseInfo;

/**
 * author : L.jinzhu
 * date : 2015/8/12
 * introduce : 响应实体
 */
public class VideoCreateRspInfo extends ResponseInfo {
    private Video video;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}