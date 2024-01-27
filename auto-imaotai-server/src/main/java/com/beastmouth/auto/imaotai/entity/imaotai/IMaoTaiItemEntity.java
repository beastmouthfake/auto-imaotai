package com.beastmouth.auto.imaotai.entity.imaotai;

import lombok.Data;

@Data
public class IMaoTaiItemEntity {
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
