package com.seelove.service;

import com.seelove.dao.FollowDao;
import com.seelove.dao.UserDao;
import com.seelove.dao.VideoDao;
import com.seelove.entity.local.user.User;
import com.seelove.entity.local.user.UserDetail;
import com.seelove.entity.local.video.Video;
import com.seelove.entity.network.request.*;
import com.seelove.entity.network.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态服务
 *
 * @author L.jinzhu 2017/3/30
 */
@Service
public class NewsService {

    private static Logger logger = LoggerFactory.getLogger(NewsService.class);
    @Resource
    private UserDao userDao;
    @Resource
    private FollowDao followDao;
    @Resource
    private VideoDao videoDao;

    public NewsService() {
    }

    public NewsFindAllRspInfo findAll(NewsFindAllActionInfo actionInfo) {
        List<UserDetail> userDetailList = new ArrayList<>();
        // 获取我关注的所有人
        List<User> userList = followDao.findByUserId(actionInfo.getUserId());
        if (null == userList || 0 == userList.size()) {
            return findDefalut(actionInfo);
        }
        // 放在map中，后续使用
        Map<Long, User> userMap = new HashMap<>();
        for (User user : userList) {
            userMap.put(user.getUserId(), user);
        }
        // 获取所有人的所有视频
        List<Video> videoList = videoDao.findAllByUserList(userList);
        if (null == videoList || 0 == videoList.size()) {
            return findDefalut(actionInfo);
        }
        // 拼接视频和人物
        for (Video video : videoList) {
            UserDetail userDetail = new UserDetail();
            userDetail.setUser(userMap.get(video.getUserId()));
            userDetail.setDefultVideo(video);
            userDetailList.add(userDetail);
        }

        NewsFindAllRspInfo rspInfo = new NewsFindAllRspInfo();
        rspInfo.initSuccess(actionInfo.getActionId());
        rspInfo.setUserDetailList(userDetailList);
        return rspInfo;
    }

    /**
     * 获取视频库最新的五条视频
     *
     * @param actionInfo
     * @return
     */
    private NewsFindAllRspInfo findDefalut(NewsFindAllActionInfo actionInfo) {
        List<UserDetail> userDetailList = new ArrayList<>();
        List<Video> videoList = videoDao.findCount5();
        for (Video video : videoList) {
            UserDetail userDetail = new UserDetail();
            userDetail.setUser(userDao.findById(video.getUserId()));
            userDetail.setDefultVideo(video);
            userDetailList.add(userDetail);
        }
        NewsFindAllRspInfo rspInfo = new NewsFindAllRspInfo();
        rspInfo.initSuccess(actionInfo.getActionId());
        rspInfo.setUserDetailList(userDetailList);
        return rspInfo;
    }
}
