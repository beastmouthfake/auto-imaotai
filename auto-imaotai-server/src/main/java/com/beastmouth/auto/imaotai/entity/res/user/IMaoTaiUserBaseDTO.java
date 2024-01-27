package com.beastmouth.auto.imaotai.entity.res.user;

import lombok.Data;

@Data
public class IMaoTaiUserBaseDTO {
    private Long recordId;
    /**
     * PushPlus Token 用于消息推送
     */
    private String pushPlusToken;
    /**
     * 预约的商品code
     */
    private String itemCode;
    /**
     * 门店id
     */
    private String bindShopId;
}
