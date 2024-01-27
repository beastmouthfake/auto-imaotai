package com.beastmouth.auto.imaotai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiShopEntity;
import com.beastmouth.auto.imaotai.entity.req.shop.IMaoTaiShopPageDTO;
import com.beastmouth.auto.imaotai.entity.res.shop.IMaoTaiShopDTO;
import com.beastmouth.auto.imaotai.mapper.IMaoTaiShopMapper;
import com.beastmouth.auto.imaotai.mapper.entity.IMaoTaiShopDO;
import com.beastmouth.auto.imaotai.service.IMaoTaiShopService;
import com.beastmouth.auto.imaotai.spi.IMaoTaiShopSpiAdapter;
import com.beastmouth.auto.imaotai.util.SnowflakeUtil;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MaoTaiShopServiceImpl implements IMaoTaiShopService {
    @Resource(name = "iMaoTaiShopCache")
    private Cache<String, List<IMaoTaiShopEntity>> iMaoTaiShopCache;
    @Resource
    private IMaoTaiShopSpiAdapter iMaoTaiShopSpiAdapter;
    @Resource
    private IMaoTaiShopMapper iMaoTaiShopMapper;

    @Override
    public List<IMaoTaiShopEntity> getShopList() {
        try {
            return iMaoTaiShopCache.get("mt_shop_list", () -> {
                List<IMaoTaiShopEntity> shopList = iMaoTaiShopSpiAdapter.getShopList();
                iMaoTaiShopCache.put("mt_shop_list", shopList);
                return shopList;
            });
        } catch (Exception e) {
            log.error("[获取i茅台门店列表] 失败, msg:{}", e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<IMaoTaiShopEntity> refreshShopList() {
        List<IMaoTaiShopEntity> shopList = iMaoTaiShopSpiAdapter.getShopList();
        iMaoTaiShopMapper.truncate();
        iMaoTaiShopCache.put("mt_shop_list", shopList);
        List<IMaoTaiShopDO> shops = shopList.stream().map(temp -> {
            IMaoTaiShopDO shop = new IMaoTaiShopDO();
            BeanUtil.copyProperties(temp, shop);
            shop.setId(SnowflakeUtil.nextId());
            shop.setDateCreate(new Date());
            return shop;
        }).collect(Collectors.toList());
        iMaoTaiShopMapper.batchInsert(shops);
        return shopList;
    }

    @Override
    public IMaoTaiShopEntity getByShopId(String shopId) {
        List<IMaoTaiShopEntity> shopList = getShopList();
        if (CollUtil.isNotEmpty(shopList)) {
            Optional<IMaoTaiShopEntity> optional = shopList.stream().filter(shop -> Objects.equals(shopId, shop.getShopId())).findFirst();
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    @Override
    public IPage<IMaoTaiShopDTO> page(IMaoTaiShopPageDTO pageDTO) {
        if (pageDTO.getPage() == null) pageDTO.setPage(1);
        if (pageDTO.getPageSize() == null) pageDTO.setPageSize(20);
        IPage<IMaoTaiShopDO> page = new Page<>(pageDTO.getPage(), pageDTO.getPageSize());
        LambdaQueryWrapper<IMaoTaiShopDO> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(pageDTO.getProvinceName())) {
            wrapper.likeRight(IMaoTaiShopDO::getProvinceName, pageDTO.getProvinceName());
        }
        if (StrUtil.isNotBlank(pageDTO.getCityName())) {
            wrapper.likeRight(IMaoTaiShopDO::getCityName, pageDTO.getCityName());
        }
        if (StrUtil.isNotBlank(pageDTO.getDistrictName())) {
            wrapper.likeRight(IMaoTaiShopDO::getDistrictName, pageDTO.getDistrictName());
        }
        IPage<IMaoTaiShopDO> dbPage = iMaoTaiShopMapper.selectPage(page, wrapper);
        IPage<IMaoTaiShopDTO> result = new Page<>();
        BeanUtil.copyProperties(dbPage, result);
        if (dbPage.getRecords() == null || dbPage.getRecords().isEmpty()) {
            return result;
        }
        result.setRecords(BeanUtil.copyToList(dbPage.getRecords(), IMaoTaiShopDTO.class));
        return result;
    }

    @Override
    public IMaoTaiShopDTO detail(Long recordId) {
        IMaoTaiShopDO iMaoTaiShopDO = iMaoTaiShopMapper.selectById(recordId);
        if (iMaoTaiShopDO == null) {
            return null;
        }
        IMaoTaiShopDTO result = new IMaoTaiShopDTO();
        BeanUtil.copyProperties(iMaoTaiShopDO, result);
        return result;
    }
}
