package com.beastmouth.auto.imaotai.util;

import com.beastmouth.auto.imaotai.BootstrapApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class IMaoTailUtilTest extends BootstrapApplicationTest {
    @Test
    public void guid() {
        String guid = IMaoTaiUtil.deviceId();
        log.info("guid:{}", guid);
    }
}
