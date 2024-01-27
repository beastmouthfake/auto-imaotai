package com.beastmouth.auto.imaotai.entity.req.shop;

import com.beastmouth.auto.imaotai.entity.req.BasePageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IMaoTaiShopPageDTO extends BasePageDTO {
    private String provinceName;
    private String cityName;
    private String districtName;
}
