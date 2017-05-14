package com.seelove.service;

import com.seelove.common.Constant;
import com.seelove.entity.network.request.NewVersionActionInfo;
import com.seelove.entity.network.response.NewVersionRspInfo;
import com.seelove.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        NewVersionRspInfo rspInfo = new NewVersionRspInfo();
        if (StringUtil.isNullOrBlank(Constant.versionCode) || StringUtil.isNullOrBlank(Constant.versionName)) {
            rspInfo.initError4System(actionInfo.getActionId());
        } else {
            rspInfo.initSuccess(actionInfo.getActionId());
            rspInfo.setVersionCode(Constant.versionCode);
            rspInfo.setVersionName(Constant.versionName);
            rspInfo.setIsForced(Constant.isForced);
            rspInfo.setDownloadUrl(Constant.downloadUrl);
            rspInfo.setDes(Constant.des);
        }
        return rspInfo;
    }
}

