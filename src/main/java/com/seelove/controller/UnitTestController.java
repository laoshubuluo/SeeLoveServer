package com.seelove.controller;

import com.seelove.common.RequestCode;
import com.seelove.entity.local.user.Follow;
import com.seelove.entity.local.user.User;
import com.seelove.entity.local.video.Video;
import com.seelove.entity.network.request.*;
import com.seelove.entity.network.request.base.RequestInfo;
import com.seelove.entity.network.response.UserFindAllRspInfo;
import com.seelove.utils.GsonUtil;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.ServletContext;

/**
 * 测试接口
 *
 * @author L.jinzhu 2017/3/30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:spring/applicationContext.xml"})
public class UnitTestController {

    @Autowired
    RequestController requestController;

    @Autowired
    ServletContext context;

    MockMvc mockMvc;

    // 测试相关
    RequestInfo requestInfo;
    String requestUrl = "/seelove/request";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(requestController).build();
        requestInfo = new RequestInfo();
    }

    @org.junit.Test
    public void userCreateTest() throws Exception {
        UserCreateActionInfo actionInfo = new UserCreateActionInfo(RequestCode.USER_CREATE, "sdddddddd", "sssss");
        requestInfo.setActionInfo(actionInfo);
        String postJson = GsonUtil.toJson(requestInfo);
        System.out.println("=============== 参数准备完成 =============================================");
        System.out.println("====" + postJson);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
                .accept(MediaType.APPLICATION_JSON)
                .content(postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=============== 请求获得响应 =============================================");
        System.out.println("====" + result);
    }

    @org.junit.Test
    public void userFindAllTest() throws Exception {
//        UserFindAllActionInfo actionInfo = new UserFindAllActionInfo(RequestCode.USER_FIND_ALL, 1, 33, "", "");
//        UserFindAllActionInfo actionInfo = new UserFindAllActionInfo(RequestCode.USER_FIND_ALL, 1, 33, "1", "");
        UserFindAllActionInfo actionInfo = new UserFindAllActionInfo(RequestCode.USER_FIND_ALL, 1, 33, "1", "111");
        requestInfo.setActionInfo(actionInfo);
        String postJson = GsonUtil.toJson(requestInfo);
        System.out.println("=============== 参数准备完成 =============================================");
        System.out.println("====" + postJson);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
                .accept(MediaType.APPLICATION_JSON)
                .content(postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        UserFindAllRspInfo rsp = GsonUtil.fromJson(result, UserFindAllRspInfo.class);
        System.out.println("=============== 请求获得响应 =============================================");
        System.out.println("====" + rsp.getUserDetailList().size() + " | " + result);
    }

    @org.junit.Test
    public void userUpdateTest() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setNickName("啥的");
        user.setHeadUrl("啥的");
        user.setToken4RongCloud("啥的");
        user.setWorkName("啥的");
        user.setAccountType(1);
        user.setAge(22);
        user.setBigImg("啥的");
        user.setCityCode("啥的");
        user.setCityName("啥的");
        user.setEducationCode("啥的");
        user.setEducationName("啥的");
        user.setFollowCount(11);
        user.setFollowedCount(121);
        user.setHouseCode("啥的");
        user.setHouseName("啥的");
        user.setIntroduce("啥的");
        user.setMarriageCode("啥的");
        user.setMarriageName("啥的");
        user.setRemark("啥的");
        user.setSex("啥的");
        user.setVideoCount(1212);

        UserUpdateActionInfo actionInfo = new UserUpdateActionInfo(RequestCode.USER_UPDATE, user);
        requestInfo.setActionInfo(actionInfo);
        String postJson = GsonUtil.toJson(requestInfo);
        System.out.println("=============== 参数准备完成 =============================================");
        System.out.println("====" + postJson);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
                .accept(MediaType.APPLICATION_JSON)
                .content(postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=============== 请求获得响应 =============================================");
        System.out.println("====" + result);
    }

    @org.junit.Test
    public void userFindDetailTest() throws Exception {
        UserFindDetailActionInfo actionInfo = new UserFindDetailActionInfo(RequestCode.USER_FIND_DETAIL, 1);
        requestInfo.setActionInfo(actionInfo);
        String postJson = GsonUtil.toJson(requestInfo);
        System.out.println("=============== 参数准备完成 =============================================");
        System.out.println("====" + postJson);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
                .accept(MediaType.APPLICATION_JSON)
                .content(postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=============== 请求获得响应 =============================================");
        System.out.println("====" + result);
    }

    @org.junit.Test
    public void videoCreateTest() throws Exception {
        Video video = new Video();
        video.setUserId(2);
        video.setIsDefault("1");
        video.setVideoTitle("单元测试title");
        video.setVideoImg("http://a.hiphotos.baidu.com/baike/whfpf%3D180%2C140%2C50/sign=20fced2fde2a283443f3654b3d88f8d2/4bed2e738bd4b31cc7a469898dd6277f9e2ff86c.jpg");
        video.setVideoTime("212112");
        video.setVideoUrl("http://7xrjck.com1.z0.glb.clouddn.com/FtB4jjP1vmy27u2aHgVuVGf1GDXt");
        video.setVideoPlayTime("23423432");

        VideoCreateActionInfo actionInfo = new VideoCreateActionInfo(RequestCode.VIDEO_CREATE, video);
        requestInfo.setActionInfo(actionInfo);
        String postJson = GsonUtil.toJson(requestInfo);
        System.out.println("=============== 参数准备完成 =============================================");
        System.out.println("====" + postJson);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
                .accept(MediaType.APPLICATION_JSON)
                .content(postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=============== 请求获得响应 =============================================");
        System.out.println("====" + result);
    }

    @org.junit.Test
    public void followTest() throws Exception {
        Follow follow = new Follow();
        follow.setUserId(1);
        follow.setFollowUserId(2);
        FollowActionInfo actionInfo = new FollowActionInfo(RequestCode.FOLLOW, FollowActionInfo.FOLLOW_TYPE_CANCLE, follow);
        requestInfo.setActionInfo(actionInfo);
        String postJson = GsonUtil.toJson(requestInfo);
        System.out.println("=============== 参数准备完成 =============================================");
        System.out.println("====" + postJson);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
                .accept(MediaType.APPLICATION_JSON)
                .content(postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=============== 请求获得响应 =============================================");
        System.out.println("====" + result);
    }


    @org.junit.Test
    public void followFindAllByUserTest() throws Exception {
        FollowFindAllActionInfo actionInfo = new FollowFindAllActionInfo(RequestCode.FOLLOW_FIND_BY_USER, 1);
        requestInfo.setActionInfo(actionInfo);
        String postJson = GsonUtil.toJson(requestInfo);
        System.out.println("=============== 参数准备完成 =============================================");
        System.out.println("====" + postJson);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
                .accept(MediaType.APPLICATION_JSON)
                .content(postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=============== 请求获得响应 =============================================");
        System.out.println("====" + result);
    }

    @org.junit.Test
    public void followFindAllByFollowedUserTest() throws Exception {
        FollowFindAllActionInfo actionInfo = new FollowFindAllActionInfo(RequestCode.FOLLOW_FIND_BY_FOLLOWED_USER, 1);
        requestInfo.setActionInfo(actionInfo);
        String postJson = GsonUtil.toJson(requestInfo);
        System.out.println("=============== 参数准备完成 =============================================");
        System.out.println("====" + postJson);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
                .accept(MediaType.APPLICATION_JSON)
                .content(postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=============== 请求获得响应 =============================================");
        System.out.println("====" + result);
    }

    @org.junit.Test
    public void newsFindAllTest() throws Exception {
        NewsFindAllActionInfo actionInfo = new NewsFindAllActionInfo(RequestCode.NEWS_FIND_ALL, 1);
        requestInfo.setActionInfo(actionInfo);
        String postJson = GsonUtil.toJson(requestInfo);
        System.out.println("=============== 参数准备完成 =============================================");
        System.out.println("====" + postJson);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
                .accept(MediaType.APPLICATION_JSON)
                .content(postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=============== 请求获得响应 =============================================");
        System.out.println("====" + result);
    }
}