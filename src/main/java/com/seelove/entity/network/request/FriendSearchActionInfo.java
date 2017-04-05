package com.seelove.entity.network.request;


import com.seelove.entity.network.request.base.ActionInfo;

import java.util.List;

/**
 * author : L.jinzhu
 * date : 2015/8/12
 * introduce : 请求实体
 */
public class FriendSearchActionInfo extends ActionInfo {
    private List<String> numberList;//["手机号1","手机号1","手机号1"],//已在阿门通信列表中的，可以不用上传
    private String keyWord;//"uid 或昵称"
    private int totalPage;//总页数，numberList查询不用上传
    private int currentPage;//当前页，numberList查询不用上传
    private String pageType;//down下一页 up上一页

    public FriendSearchActionInfo(int actionId, List<String> numberList, String keyWord, int totalPage, int currentPage, String pageType) {
        super(actionId);
        this.numberList = numberList;
        this.keyWord = keyWord;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.pageType = pageType;
    }
}