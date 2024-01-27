package com.beastmouth.auto.imaotai.spi;

import com.alibaba.fastjson.JSONObject;
import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiUserEntity;

public interface IMaoTaiSpiAdapter {
    String getMTVersion();

    Boolean sendCode(String mobile, String deviceId, String mtVersion);

    IMaoTaiUserEntity login(String mobile, String code, String deviceId, String mtVersion);

    JSONObject getCurrentSessionId();

    JSONObject reservation(String itemId, String shopId, String currentSessionId,
                           String userId, String lat, String lng, String token, String deviceId);
}
