package com.beastmouth.auto.imaotai.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beastmouth.auto.imaotai.entity.req.user.IMaoTaiUserLoginDTO;
import com.beastmouth.auto.imaotai.entity.req.user.IMaoTaiUserPageDTO;
import com.beastmouth.auto.imaotai.entity.req.user.IMaoTaiUserUpdateDTO;
import com.beastmouth.auto.imaotai.entity.res.Result;
import com.beastmouth.auto.imaotai.entity.res.user.IMaoTaiUserDTO;
import com.beastmouth.auto.imaotai.service.IMaoTaiCommonService;
import com.beastmouth.auto.imaotai.service.IMaoTaiUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/imaotai/user")
public class IMaoTaiUserController {
    @Resource
    private IMaoTaiUserService iMaoTaiUserService;
    @Resource
    private IMaoTaiCommonService iMaoTaiCommonService;

    @GetMapping("/page")
    public Result<IPage<IMaoTaiUserDTO>> page(IMaoTaiUserPageDTO param) {
        return Result.success(iMaoTaiUserService.page(param));
    }

    @GetMapping("/detail/{recordId}")
    public Result<IMaoTaiUserDTO> detail(@PathVariable("recordId") Long recordId) {
        return Result.success(iMaoTaiUserService.detail(recordId));
    }

    @PostMapping("/addUser")
    public Result<Boolean> addUser(@RequestBody IMaoTaiUserLoginDTO param) {
        return Result.success(iMaoTaiUserService.addUser(param));
    }

    @PostMapping("/updateUser")
    public Result<Boolean> updateUser(@RequestBody IMaoTaiUserUpdateDTO param) {
        return Result.success(iMaoTaiUserService.updateUser(param));
    }

    @PostMapping("/deleteUser/{recordId}")
    public Result<Boolean> deleteUser(@PathVariable("recordId") Long recordId) {
        return Result.success(iMaoTaiUserService.deleteUser(recordId));
    }

    @GetMapping("/code/{phone}")
    public Result<String> code(@PathVariable("phone") String phone) {
        return Result.success(iMaoTaiCommonService.sendCode(phone));
    }
}
