package com.beastmouth.auto.imaotai.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_maotai_shop")
public class IMaoTaiShopDO extends BaseDO {
    /**
     * 门店id
     */
    private String shopId;
    /**
     * 省份code
     */
    private String province;
    /**
     * 省份名称
     */
    private String provinceName;
    /**
     * 城市code
     */
    private String city;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 地区code
     */
    private String district;
    /**
     * 地区名称
     */
    private String districtName;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 完整地址
     */
    private String fullAddress;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 经度
     */
    private String lng;
    /**
     * 门店名称
     */
    private String name;
    /**
     * 公司名称
     */
    private String tenantName;
    /**
     * 营业时间
     */
    private String openEndTime;
    /**
     * 营业时间
     */
    private String openStartTime;
    /**
     * 是否支持到店自提
     */
    private Boolean layaway;
    /**
     * 标签
     */
    private String tags;
}
