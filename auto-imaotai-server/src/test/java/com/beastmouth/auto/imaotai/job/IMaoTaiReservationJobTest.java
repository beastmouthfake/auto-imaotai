package com.beastmouth.auto.imaotai.job;

import com.beastmouth.auto.imaotai.BootstrapApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

@Slf4j
public class IMaoTaiReservationJobTest extends BootstrapApplicationTest {
    @Resource
    private IMaoTaiReservationJob iMaoTaiReservationJob;

    @Test
    public void reservationBatchTask() {
        iMaoTaiReservationJob.reservationBatchTask();
    }
}
