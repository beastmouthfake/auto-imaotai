package com.beastmouth.auto.imaotai.entity.req.user;

import com.beastmouth.auto.imaotai.entity.req.BasePageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IMaoTaiUserPageDTO extends BasePageDTO {
    /**
     * 手机号 精确查找
     */
    private String mobileShow;
}
