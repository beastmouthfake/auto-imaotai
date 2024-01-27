package com.beastmouth.auto.imaotai.mapper.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDO {
    /**
     * id
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date dateCreate;
    /**
     * 更新时间
     */
    private Date dateUpdate;
    /**
     * 删除时间
     */
    private Long dateDelete;
}
