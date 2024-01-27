package com.beastmouth.auto.imaotai.service;

import com.alibaba.fastjson.JSON;
import com.beastmouth.auto.imaotai.BootstrapApplicationTest;
import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class IMaoTaiCommonServiceTest extends BootstrapApplicationTest {
    @Autowired
    private IMaoTaiCommonService iMaoTaiCommonService;

    // ==================== 测试过程中需要根据接口返回修改调整 ====================
    private final String MOBILE = "";
    private final String USER_ID = "";
    private final String TOKEN = "";
    private final String DEVICE_ID = "";

    @Test
    public void getMtVersion() {
        String mtVersion = iMaoTaiCommonService.getMTVersion();
        log.info("mtVersion:{}", mtVersion);
    }

    @Test
    public void getSessionId() {
        String sessionId = iMaoTaiCommonService.getSessionId();
        log.info("sessionId:{}", sessionId);
    }

    @Test
    public void sendCode() {
        String deviceId = iMaoTaiCommonService.sendCode(MOBILE);
        log.info("mobile:{}, deviceId:{}", MOBILE, deviceId);
    }

    @Test
    public void login() {
        String code = "239075";
        IMaoTaiUserEntity iMaoTaiUser = iMaoTaiCommonService.login(MOBILE, code, DEVICE_ID);
        log.info("[登陆i茅台] mobile:{}, code:{}, deviceId:{}, iMaoTaiUser:{}", MOBILE, code, DEVICE_ID, JSON.toJSONString(iMaoTaiUser));
    }
}
