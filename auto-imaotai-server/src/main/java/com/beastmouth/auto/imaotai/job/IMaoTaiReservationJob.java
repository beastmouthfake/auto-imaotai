package com.beastmouth.auto.imaotai.job;

import cn.hutool.core.date.DateUtil;
import com.beastmouth.auto.imaotai.service.IMaoTaiCommonService;
import com.beastmouth.auto.imaotai.service.IMaoTaiReservationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class IMaoTaiReservationJob {
    @Resource
    private IMaoTaiCommonService iMaoTaiCommonService;
    @Resource
    private IMaoTaiReservationService iMaoTaiReservationService;

    @Scheduled(cron = "0 0/1 9 ? * *")
    public void reservationBatchTask() {
        int minute = DateUtil.minute(new Date());
        iMaoTaiReservationService.reservation(minute);
    }

    @Scheduled(cron = "0 10,55 7,8 ? * * ")
    public void refreshData() {
        iMaoTaiCommonService.initRefresh();
    }
}
