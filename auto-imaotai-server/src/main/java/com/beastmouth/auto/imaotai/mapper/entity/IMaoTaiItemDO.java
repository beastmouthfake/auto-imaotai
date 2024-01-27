package com.beastmouth.auto.imaotai.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_maotai_item")
public class IMaoTaiItemDO extends BaseDO {
    /**
     * 商品code
     */
    private String itemCode;
    /**
     * 商品图片v2
     */
    private String pictureV2;
    /**
     * checkTag
     */
    private Integer checkTag;
    /**
     * 商品标题
     */
    private String title;
    /**
     * 商品图片
     */
    private String picture;
    /**
     * 商品内容
     */
    private String content;
    /**
     * 跳转链接
     */
    private String jumpUrl;
}
