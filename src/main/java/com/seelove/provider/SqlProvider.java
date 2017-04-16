package com.seelove.provider;

import com.seelove.utils.StringUtil;

import java.util.Map;

/**
 * sql provider
 * Created by liangjinzhu on 17/4/16.
 */
public class SqlProvider {
    public String userFindAll(Map<String, Object> para) {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from userinfo where 1=1");

        // 年齡
        if (null != para.get("ageStart") && null != para.get("ageEnd")) {
            int ageStart = (int) para.get("ageStart");
            int ageEnd = (int) para.get("ageEnd");
            if (ageEnd >= ageStart && 0 != ageEnd) {
                sql.append(" and age between " + ageStart + " and " + ageEnd);
            }
        }
        // 性别
        if (null != para.get("sex")) {
            String sex = (String) para.get("sex");
            if (StringUtil.isNotBlank(sex)) {
                sql.append(" and sex = " + sex);
            }
        }
        // 城市
        if (null != para.get("cityCode")) {
            String cityCode = (String) para.get("cityCode");
            if (StringUtil.isNotBlank(cityCode)) {
                sql.append(" and cityCode = " + cityCode);
            }
        }
        return sql.toString();
    }
}
