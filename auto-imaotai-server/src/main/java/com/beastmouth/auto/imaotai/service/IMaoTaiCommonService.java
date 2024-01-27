package com.beastmouth.auto.imaotai.service;

import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiUserEntity;

public interface IMaoTaiCommonService {
    void initRefresh();

    String getMTVersion();

    String getSessionId();

    String sendCode(String mobile);

    IMaoTaiUserEntity login(String mobile, String code, String deviceId);
}
