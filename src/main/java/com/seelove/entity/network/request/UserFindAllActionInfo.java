package com.seelove.entity.network.request;


import com.seelove.entity.network.request.base.ActionInfo;

/**
 * author : L.jinzhu
 * date : 2015/8/12
 * introduce : 请求实体
 */
public class UserFindAllActionInfo extends ActionInfo {
    private int pageNumber;
    private int dataGetType;

    private long userId;
    private int ageStart = 0;
    private int ageEnd = 0;
    private String sex;
    private String cityCode;

    public UserFindAllActionInfo(int actionId, int pageNumber, int dataGetType, long userId, int ageStart, int ageEnd, String sex, String cityCode) {
        super(actionId);
        this.pageNumber = pageNumber;
        this.dataGetType = dataGetType;
        this.userId = userId;
        this.ageStart = ageStart;
        this.ageEnd = ageEnd;
        this.sex = sex;
        this.cityCode = cityCode;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public int getAgeStart() {
        return ageStart;
    }

    public void setAgeStart(int ageStart) {
        this.ageStart = ageStart;
    }

    public int getAgeEnd() {
        return ageEnd;
    }

    public void setAgeEnd(int ageEnd) {
        this.ageEnd = ageEnd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getDataGetType() {
        return dataGetType;
    }

    public void setDataGetType(int dataGetType) {
        this.dataGetType = dataGetType;
    }
}