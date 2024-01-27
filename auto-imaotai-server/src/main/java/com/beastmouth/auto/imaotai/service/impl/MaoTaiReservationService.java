package com.beastmouth.auto.imaotai.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beastmouth.auto.imaotai.mapper.entity.IMaoTaiUserDO;
import com.beastmouth.auto.imaotai.service.IMaoTaiCommonService;
import com.beastmouth.auto.imaotai.service.IMaoTaiReservationService;
import com.beastmouth.auto.imaotai.service.IMaoTaiUserService;
import com.beastmouth.auto.imaotai.spi.IMaoTaiSpiAdapter;
import com.beastmouth.auto.imaotai.util.PushPlusUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MaoTaiReservationService implements IMaoTaiReservationService {
    @Resource
    private IMaoTaiUserService iMaoTaiUserService;
    @Resource
    private IMaoTaiSpiAdapter iMaoTaiSpiAdapter;
    @Resource
    private IMaoTaiCommonService iMaoTaiCommonService;

    @Override
    public void reservation(Integer offset) {
        String sessionId = iMaoTaiCommonService.getSessionId();
        List<IMaoTaiUserDO> iMaoTaiUserDOS = iMaoTaiUserService.selectByOffset(offset);
        iMaoTaiUserDOS.forEach(iMaoTaiUserDO -> {
            try {
                // 预定
                String itemCodes = iMaoTaiUserDO.getItemCode();
                List<String> itemCodeList = JSON.parseArray(itemCodes, String.class);
                if (CollUtil.isEmpty(itemCodeList)) return;
                itemCodeList.forEach(itemCode -> {
                    JSONObject result = iMaoTaiSpiAdapter.reservation(itemCode, iMaoTaiUserDO.getShopId(),
                            sessionId, iMaoTaiUserDO.getUserId().toString(),
                            iMaoTaiUserDO.getLat(), iMaoTaiUserDO.getLng(), iMaoTaiUserDO.getToken(),
                            iMaoTaiUserDO.getDeviceId());
                    try {
                        PushPlusUtil.sendMsg(iMaoTaiUserDO.getPushPlusToken(), "预约申购结果", result.toJSONString());
                    } catch (Exception e) {
                        log.error("[定时任务预约] 推送消息失败, mobile:{}, msg:{}", iMaoTaiUserDO.getMobileShow(), e.getMessage());
                    }
                });
                // 预定结束, 修改下次预约偏移量
                iMaoTaiUserService.updateOffset(iMaoTaiUserDO);
                TimeUnit.SECONDS.sleep(3L);
            } catch (Exception e) {
                log.error("[定时任务预约] 预约失败, mobile:{}, msg:{}", iMaoTaiUserDO.getMobileShow(), e.getMessage());
            }
        });
    }
}
