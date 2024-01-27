package com.beastmouth.auto.imaotai.entity.req.user;

import lombok.Data;

@Data
public class IMaoTaiUserLoginDTO {
    // 必填
    private String mobile;
    private String code;
    private String deviceId;
    // 以下为非必填
    private String pushPlusToken;
    private String bindShopId;
}
