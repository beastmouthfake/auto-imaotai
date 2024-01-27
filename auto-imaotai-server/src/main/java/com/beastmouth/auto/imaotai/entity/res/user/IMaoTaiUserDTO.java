package com.beastmouth.auto.imaotai.entity.res.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class IMaoTaiUserDTO extends IMaoTaiUserBaseDTO {
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
     * 纬度
     */
    private String lat;

    /**
     * 经度
     */
    private String lng;
    /**
     * 发送验证码登陆时的设备id, 后续请求都要用此设备id
     */
    private String deviceId;
}
