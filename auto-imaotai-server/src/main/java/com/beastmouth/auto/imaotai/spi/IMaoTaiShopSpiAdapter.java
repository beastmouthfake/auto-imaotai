package com.beastmouth.auto.imaotai.spi;

import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiShopEntity;

import java.util.List;

public interface IMaoTaiShopSpiAdapter {
    List<IMaoTaiShopEntity> getShopList();
}
