package com.beastmouth.auto.imaotai.entity.imaotai;

import lombok.Data;

@Data
public class IMaoTaiUserEntity {
    private Integer verifyStatus;
    private String cookie;
    private Integer userTag;
    private String mobile;
    private String userName;
    private Long userId;
    private String token;
}
