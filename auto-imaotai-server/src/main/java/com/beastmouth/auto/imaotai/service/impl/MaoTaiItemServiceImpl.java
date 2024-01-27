package com.beastmouth.auto.imaotai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiItemEntity;
import com.beastmouth.auto.imaotai.mapper.IMaoTaiItemMapper;
import com.beastmouth.auto.imaotai.mapper.entity.IMaoTaiItemDO;
import com.beastmouth.auto.imaotai.service.IMaoTaiItemService;
import com.beastmouth.auto.imaotai.util.SnowflakeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaoTaiItemServiceImpl implements IMaoTaiItemService {
    @Resource
    private IMaoTaiItemMapper iMaoTaiItemMapper;

    @Override
    public void refreshItem(List<IMaoTaiItemEntity> itemEntities) {
        iMaoTaiItemMapper.truncate();
        List<IMaoTaiItemDO> itemDos = itemEntities.stream().map(temp -> {
            IMaoTaiItemDO itemDo = new IMaoTaiItemDO();
            BeanUtils.copyProperties(temp, itemDo);
            itemDo.setId(SnowflakeUtil.nextId());
            itemDo.setDateCreate(new Date());
            return itemDo;
        }).collect(Collectors.toList());
        iMaoTaiItemMapper.batchInsert(itemDos);
    }

    @Override
    public List<IMaoTaiItemEntity> all() {
        LambdaQueryWrapper<IMaoTaiItemDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IMaoTaiItemDO::getDateDelete, 0L);
        List<IMaoTaiItemDO> tempList = iMaoTaiItemMapper.selectList(wrapper);
        return tempList.stream().map(temp -> {
            IMaoTaiItemEntity itemEntity = new IMaoTaiItemEntity();
            BeanUtils.copyProperties(temp, itemEntity);
            return itemEntity;
        }).collect(Collectors.toList());
    }
}
