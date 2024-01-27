package com.beastmouth.auto.imaotai.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beastmouth.auto.imaotai.BootstrapApplicationTest;
import com.beastmouth.auto.imaotai.entity.req.shop.IMaoTaiShopPageDTO;
import com.beastmouth.auto.imaotai.entity.res.shop.IMaoTaiShopDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

@Slf4j
public class IMaoTaiShopServiceTest extends BootstrapApplicationTest {
    @Resource
    private IMaoTaiShopService iMaoTaiShopService;

    @Test
    public void getShopList() {
        log.info("shopList:{}", JSON.toJSONString(iMaoTaiShopService.getShopList()));
    }

    @Test
    public void refreshShopList() {
        log.info("shopList:{}", JSON.toJSONString(iMaoTaiShopService.refreshShopList()));
    }

    @Test
    public void page() {
        IPage<IMaoTaiShopDTO> page = iMaoTaiShopService.page(new IMaoTaiShopPageDTO());
        log.info("page:{}", JSON.toJSONString(page));
    }
}
