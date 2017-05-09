package com.seelove.service;

import com.seelove.entity.network.request.NewVersionActionInfo;
import com.seelove.entity.network.response.NewVersionRspInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;

/**
 * 系统服务
 *
 * @author L.jinzhu 2017/3/30
 */
@Service
public class SystemService {

    private static Logger logger = LoggerFactory.getLogger(SystemService.class);

    public SystemService() {
    }

    /**
     * 最新版本
     *
     * @param actionInfo
     * @return
     */
    public NewVersionRspInfo getNewVersion(NewVersionActionInfo actionInfo) {
        String propFile = getClass().getResource("/").getPath() + File.separator + "system" + File.separator + "newVersion.properties";
        NewVersionRspInfo rspInfo = new NewVersionRspInfo();
        Properties prop = new Properties();
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(propFile), "UTF-8");
            prop.load(in);

            rspInfo.initSuccess(actionInfo.getActionId());
            rspInfo.setVersionCode(prop.getProperty("versionCode"));
            rspInfo.setVersionName(prop.getProperty("versionName"));
            rspInfo.setIsForced(prop.getProperty("isForced"));
            rspInfo.setDownloadUrl(prop.getProperty("downloadUrl"));
            rspInfo.setDes(prop.getProperty("des"));

            in.close();
        } catch (Throwable e) {
            rspInfo.initError4System(actionInfo.getActionId());
        }
        return rspInfo;
    }
}

