package com.beastmouth.auto.imaotai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beastmouth.auto.imaotai.mapper.entity.IMaoTaiShopDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IMaoTaiShopMapper extends BaseMapper<IMaoTaiShopDO> {
    @Update("truncate table i_maotai_shop")
    void truncate();

    int updateBatch(List<IMaoTaiShopDO> list);

    int updateBatchSelective(List<IMaoTaiShopDO> list);

    int batchInsert(@Param("list") List<IMaoTaiShopDO> list);
}
