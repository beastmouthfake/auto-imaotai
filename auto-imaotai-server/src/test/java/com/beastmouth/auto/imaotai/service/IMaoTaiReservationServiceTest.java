package com.beastmouth.auto.imaotai.service;

import com.beastmouth.auto.imaotai.BootstrapApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

@Slf4j
public class IMaoTaiReservationServiceTest extends BootstrapApplicationTest {
    @Resource
    private IMaoTaiReservationService iMaoTaiReservationService;

    @Test
    public void reservation() {
        iMaoTaiReservationService.reservation(15);
    }
}
