package com.beastmouth.auto.imaotai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiShopEntity;
import com.beastmouth.auto.imaotai.entity.req.shop.IMaoTaiShopPageDTO;
import com.beastmouth.auto.imaotai.entity.res.shop.IMaoTaiShopDTO;

import java.util.List;

public interface IMaoTaiShopService {
    List<IMaoTaiShopEntity> getShopList();

    List<IMaoTaiShopEntity> refreshShopList();

    IMaoTaiShopEntity getByShopId(String shopId);

    IPage<IMaoTaiShopDTO> page(IMaoTaiShopPageDTO pageDTO);

    IMaoTaiShopDTO detail(Long recordId);
}
