package com.beastmouth.auto.imaotai.controller;

import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiItemEntity;
import com.beastmouth.auto.imaotai.entity.res.Result;
import com.beastmouth.auto.imaotai.service.IMaoTaiItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/imaotai/item")
public class IMaoTaiItemController {
    @Resource
    private IMaoTaiItemService iMaoTaiItemService;

    @GetMapping("/list")
    public Result<List<IMaoTaiItemEntity>> itemList() {
        return Result.success(iMaoTaiItemService.all());
    }
}
