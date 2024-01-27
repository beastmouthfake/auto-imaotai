package com.beastmouth.auto.imaotai.runner;

import com.beastmouth.auto.imaotai.service.IMaoTaiCommonService;
import com.beastmouth.auto.imaotai.service.IMaoTaiShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class InitRunner implements ApplicationRunner {
    @Resource
    private IMaoTaiCommonService iMaoTaiCommonService;
    @Resource
    private IMaoTaiShopService iMaoTaiShopService;

    @Resource

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("======== 执行缓存及数据库数据初始化开始 ========");
        iMaoTaiCommonService.initRefresh();
        iMaoTaiShopService.refreshShopList();
        log.info("======== 执行缓存及数据库数据初始化结束 ========");
    }
}
