package com.seelove.service;

import com.seelove.dao.VideoDao;
import com.seelove.entity.enums.ResponseType;
import com.seelove.entity.local.video.Video;
import com.seelove.entity.network.request.VideoCreateActionInfo;
import com.seelove.entity.network.response.VideoCreateRspInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

/**
 * 视频服务
 *
 * @author L.jinzhu 2017/3/30
 */
@Service
public class VideoService {

    private static Logger logger = LoggerFactory.getLogger(VideoService.class);
    @Resource
    private VideoDao videoDao;

    public VideoService() {
    }

    public VideoCreateRspInfo create(VideoCreateActionInfo actionInfo) {
        VideoCreateRspInfo rspInfo = new VideoCreateRspInfo();
        Video video = actionInfo.getVideo();
        if (null == video) {
            rspInfo.initError4Param(actionInfo.getActionId());
            return rspInfo;
        }

        // 保存video
        videoDao.create(video);
        // 保存user_video
        videoDao.createUserVideo(video.getVideoId(), video.getUserId(), video.getIsDefault());

        rspInfo.initSuccess(actionInfo.getActionId());
        rspInfo.setVideo(video);
        return rspInfo;
    }
}

