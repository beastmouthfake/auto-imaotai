package com.beastmouth.auto.imaotai.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_maotai_user")
public class IMaoTaiUserDO extends BaseDO {
    // ==============================茅台接口返回参数==============================
    private Integer verifyStatus;
    private String cookie;
    private Integer userTag;
    private String mobile;
    private String userName;
    private Long userId;
    private String token;
    // ==============================茅台接口返回参数==============================
    /**
     * 展示的手机号 完整不脱敏
     */
    private String mobileShow;
    /**
     * 预约时间偏移量
     */
    private Integer reservationTimeOffset;
    /**
     * 登陆时间 30天后过期需要重登
     */
    private Date loginTime;
    /**
     * PushPlus Token 用于消息推送
     */
    private String pushPlusToken;
    /**
     * 发送验证码登陆时的设备id, 后续请求都要用此设备id
     */
    private String deviceId;
    /**
     * 纬度
     */
    private String lat;

    /**
     * 经度
     */
    private String lng;

    /**
     * 预约门店id
     */
    private String shopId;

    /**
     * 预约的商品code
     */
    private String itemCode;
}
