package com.beastmouth.auto.imaotai.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beastmouth.auto.imaotai.entity.req.shop.IMaoTaiShopPageDTO;
import com.beastmouth.auto.imaotai.entity.res.Result;
import com.beastmouth.auto.imaotai.entity.res.shop.IMaoTaiShopDTO;
import com.beastmouth.auto.imaotai.service.IMaoTaiShopService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/imaotai/shop")
public class IMaoTaiShopController {
    @Resource
    private IMaoTaiShopService iMaoTaiShopService;

    @GetMapping("/page")
    public Result<IPage<IMaoTaiShopDTO>> page(IMaoTaiShopPageDTO param) {
        return Result.success(iMaoTaiShopService.page(param));
    }

    @GetMapping("/detail/{recordId}")
    public Result<IMaoTaiShopDTO> detail(@PathVariable("recordId") Long recordId) {
        return Result.success(iMaoTaiShopService.detail(recordId));
    }
}
