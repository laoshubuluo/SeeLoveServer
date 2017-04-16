package com.seelove.controller;

import com.seelove.common.RequestCode;
import com.seelove.entity.network.request.UserFindAllActionInfo;
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
    RequestInfo requestInfo;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(requestController).build();
        requestInfo = new RequestInfo();
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

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/seelove/request")
                .accept(MediaType.APPLICATION_JSON)
                .content(postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        UserFindAllRspInfo rsp = GsonUtil.fromJson(result, UserFindAllRspInfo.class);
        System.out.println("=============== 请求获得响应 =============================================");
        System.out.println("====" + rsp.getUserList().size() + " | " + result);
    }
}