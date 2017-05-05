package com.seelove.service;

import com.seelove.dao.VideoDao;
import com.seelove.entity.enums.ResponseType;
import com.seelove.entity.local.video.Video;
import com.seelove.entity.network.request.VideoCreateActionInfo;
import com.seelove.entity.network.request.VideoDeleteActionInfo;
import com.seelove.entity.network.response.VideoCreateRspInfo;
import com.seelove.entity.network.response.VideoDeleteRspInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
        // 更新之前的所有视频为非默认视频
        videoDao.updateUserVideoSetAllNotDefault(video.getUserId());
        // 保存user_video,并设定为默认视频
        videoDao.createUserVideo(video.getVideoId(), video.getUserId(), "1");

        rspInfo.initSuccess(actionInfo.getActionId());
        rspInfo.setVideo(video);
        return rspInfo;
    }

    public VideoDeleteRspInfo delete(VideoDeleteActionInfo actionInfo) {
        VideoDeleteRspInfo rspInfo = new VideoDeleteRspInfo();
        List<Long> videoIdList = actionInfo.getVideoIdList();
        if (null == videoIdList || 0 == videoIdList.size()) {
            rspInfo.initError4Param(actionInfo.getActionId());
            return rspInfo;
        }

        // 删除video
        videoDao.deleteVideo(videoIdList);
        // 删除video_user
        videoDao.deleteUserVideo(videoIdList);
        // TODO by L.jinzhu for 如果删除的是默认视频，则需要匹配新的默认视频。重新查看是否存在默认视频
        //        actionInfo.getUserId()

        rspInfo.initSuccess(actionInfo.getActionId());
        rspInfo.setVideoIdList(videoIdList);
        return rspInfo;
    }
}

