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
        Video video = actionInfo.getVideo();
        if (null == video) {
            VideoCreateRspInfo rspInfo = new VideoCreateRspInfo();
            rspInfo.setActionId(actionInfo.getActionId());
            rspInfo.setStatusCode(ResponseType.PARAM_ERROR.getCode());
            rspInfo.setStatusMsg(ResponseType.PARAM_ERROR.getMessage());
            return rspInfo;
        }

        // TODO by L.jinzhu for id应该通过表索引反馈
        int id = new Random().nextInt(10000);
        video.setVideoId(id);

        // 保存video
        videoDao.create(video.getVideoId(), video.getVideoTitle(), video.getVideoTime(), video.getVideoImg(), video.getVideoUrl(), video.getVideoPlayTime(), video.getRemark());
        // 保存user_video
        videoDao.createUserVideo(video.getVideoId(), video.getUserId(), video.getIsDefault());

        VideoCreateRspInfo rspInfo = new VideoCreateRspInfo();
        rspInfo.setActionId(actionInfo.getActionId());
        rspInfo.setStatusCode(ResponseType.SUCCESS.getCode());
        rspInfo.setStatusMsg(ResponseType.SUCCESS.getMessage());

        return rspInfo;
    }
}

