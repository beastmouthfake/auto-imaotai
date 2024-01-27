package com.beastmouth.auto.imaotai.spi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beastmouth.auto.imaotai.BootstrapApplicationTest;
import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiUserEntity;
import com.beastmouth.auto.imaotai.util.IMaoTaiUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class IMaoTaiSpiAdapterTest extends BootstrapApplicationTest {
    private static final String mtVersion = "1.5.7";

    @Autowired
    private IMaoTaiSpiAdapter iMaoTaiSpiAdapter;

    // ==================== 测试过程中需要根据接口返回修改调整 ====================
    private final String MOBILE = "";
    private final String USER_ID = "";
    private final String TOKEN = "";
    private final String DEVICE_ID = "";

    @Test
    public void sendCode() {
        String deviceId = IMaoTaiUtil.deviceId();
        Boolean result = iMaoTaiSpiAdapter.sendCode(MOBILE, deviceId, mtVersion);
        log.info("[发送验证码] mobile:{}, deviceId:{}, result:{}", MOBILE, deviceId, result);
    }

    @Test
    public void login() {
        String code = "560088";
        IMaoTaiUserEntity iMaoTaiUser = iMaoTaiSpiAdapter.login(MOBILE, code, DEVICE_ID, mtVersion);
        log.info("[登陆] mobile:{}, code:{}, deviceId:{}, result:{}", MOBILE, code, DEVICE_ID, JSON.toJSONString(iMaoTaiUser));
    }

    @Test
    public void currentSessionId() {
        JSONObject currentSessionId = iMaoTaiSpiAdapter.getCurrentSessionId();
        log.info("[获取今日信息] currentSessionId:{}", JSON.toJSONString(currentSessionId));
    }

    @Test
    public void reservation() {
        String itemId = "10941";
        String shopId = "22330100025001";
        String currentSessionId = "905";
        String lat = "30.218297";
        String lng = "120.270261";
        JSONObject reservationResult = iMaoTaiSpiAdapter.reservation(itemId, shopId, currentSessionId, USER_ID, lat, lng, TOKEN, DEVICE_ID);
        log.info("[申购i茅台] 结果 result:{}", JSON.toJSONString(reservationResult));
    }
}
