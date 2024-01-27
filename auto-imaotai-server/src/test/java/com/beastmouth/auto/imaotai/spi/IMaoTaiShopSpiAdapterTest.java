package com.beastmouth.auto.imaotai.spi;

import com.alibaba.fastjson.JSON;
import com.beastmouth.auto.imaotai.BootstrapApplicationTest;
import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiShopEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
public class IMaoTaiShopSpiAdapterTest extends BootstrapApplicationTest {
    @Resource
    private IMaoTaiShopSpiAdapter iMaoTaiShopSpiAdapter;

    @Test
    public void getShopList() {
        List<IMaoTaiShopEntity> shopList = iMaoTaiShopSpiAdapter.getShopList();
        log.info("shopList:{}", JSON.toJSONString(shopList));
    }
}
