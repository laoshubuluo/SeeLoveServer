package com.seelove.service;

import com.seelove.dao.VideoDao;
import com.seelove.entity.enums.ResponseType;
import com.seelove.entity.local.video.Video;
import com.seelove.entity.network.request.VideoFindByUserActionInfo;
import com.seelove.entity.network.response.VideoFindByUserRspInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    public VideoFindByUserRspInfo findByUser(VideoFindByUserActionInfo actionInfo) {
        List<Video> videoList = videoDao.findByUser(actionInfo.getUserId());

        VideoFindByUserRspInfo rspInfo = new VideoFindByUserRspInfo();
        rspInfo.setActionId(actionInfo.getActionId());
        rspInfo.setStatusCode(ResponseType.SUCCESS.getCode());
        rspInfo.setStatusMsg(ResponseType.SUCCESS.getMessage());
        rspInfo.setVideoList(videoList);

        return rspInfo;
    }
}

