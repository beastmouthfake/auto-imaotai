package com.beastmouth.auto.imaotai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beastmouth.auto.imaotai.mapper.entity.IMaoTaiItemDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IMaoTaiItemMapper extends BaseMapper<IMaoTaiItemDO> {
    @Update("truncate table i_maotai_item")
    void truncate();

    int batchInsert(@Param("list") List<IMaoTaiItemDO> list);
}
