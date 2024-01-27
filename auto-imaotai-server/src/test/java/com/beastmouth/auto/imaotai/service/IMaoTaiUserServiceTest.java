package com.beastmouth.auto.imaotai.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beastmouth.auto.imaotai.BootstrapApplicationTest;
import com.beastmouth.auto.imaotai.entity.req.user.IMaoTaiUserLoginDTO;
import com.beastmouth.auto.imaotai.entity.req.user.IMaoTaiUserPageDTO;
import com.beastmouth.auto.imaotai.entity.req.user.IMaoTaiUserUpdateDTO;
import com.beastmouth.auto.imaotai.entity.res.user.IMaoTaiUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

@Slf4j
public class IMaoTaiUserServiceTest extends BootstrapApplicationTest {
    @Resource
    private IMaoTaiUserService iMaoTaiUserService;

    // ==================== 测试过程中需要根据接口返回修改调整 ====================
    private final String MOBILE = "";
    private final Long USER_ID = 0L;
    private final String TOKEN = "";
    private final String DEVICE_ID = "";
    private final String PUSH_PLUS_TOKEN = "";
    private final String SHOP_ID = "";

    @Test
    public void login() {
        IMaoTaiUserLoginDTO IMaoTaiUserLoginDTO = new IMaoTaiUserLoginDTO();
        IMaoTaiUserLoginDTO.setMobile(MOBILE);
        IMaoTaiUserLoginDTO.setCode("221621");
        IMaoTaiUserLoginDTO.setDeviceId(DEVICE_ID);
        IMaoTaiUserLoginDTO.setPushPlusToken(PUSH_PLUS_TOKEN);
        iMaoTaiUserService.login(IMaoTaiUserLoginDTO);
    }

    @Test
    public void bindShop() {
        iMaoTaiUserService.bindShop(USER_ID, SHOP_ID);
    }

    @Test
    public void addUser() {
        IMaoTaiUserLoginDTO iMaoTaiUserLoginDTO = new IMaoTaiUserLoginDTO();
        iMaoTaiUserLoginDTO.setMobile(MOBILE);
        iMaoTaiUserLoginDTO.setCode("564555");
        iMaoTaiUserLoginDTO.setDeviceId(DEVICE_ID);
        iMaoTaiUserLoginDTO.setPushPlusToken(PUSH_PLUS_TOKEN);
        iMaoTaiUserLoginDTO.setBindShopId(SHOP_ID);
        iMaoTaiUserService.addUser(iMaoTaiUserLoginDTO);
    }

    @Test
    public void updateUser() {
        IMaoTaiUserUpdateDTO iMaoTaiUserUpdateDTO = new IMaoTaiUserUpdateDTO();
        iMaoTaiUserUpdateDTO.setRecordId(1750711623036833792L);
        iMaoTaiUserUpdateDTO.setPushPlusToken(PUSH_PLUS_TOKEN);
        iMaoTaiUserUpdateDTO.setBindShopId(SHOP_ID);
        iMaoTaiUserService.updateUser(iMaoTaiUserUpdateDTO);
    }

    @Test
    public void deleteUser() {
        iMaoTaiUserService.deleteUser(1750372334163857408L);
    }

    @Test
    public void detail() {
        IMaoTaiUserDTO detail = iMaoTaiUserService.detail(1750711623036833792L);
        log.info("detail:{}", JSON.toJSONString(detail));
    }

    @Test
    public void page() {
        IPage<IMaoTaiUserDTO> page = iMaoTaiUserService.page(new IMaoTaiUserPageDTO());
        log.info("page:{}", JSON.toJSONString(page));
    }
}
