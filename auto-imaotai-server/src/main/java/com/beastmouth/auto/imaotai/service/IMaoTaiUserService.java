package com.beastmouth.auto.imaotai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beastmouth.auto.imaotai.entity.req.user.IMaoTaiUserLoginDTO;
import com.beastmouth.auto.imaotai.entity.req.user.IMaoTaiUserPageDTO;
import com.beastmouth.auto.imaotai.entity.req.user.IMaoTaiUserUpdateDTO;
import com.beastmouth.auto.imaotai.entity.res.user.IMaoTaiUserDTO;
import com.beastmouth.auto.imaotai.mapper.entity.IMaoTaiUserDO;

import java.util.List;

public interface IMaoTaiUserService {
    Boolean login(IMaoTaiUserLoginDTO IMaoTaiUserLoginDTO);

    Boolean bindShop(Long recordId, String shopId);

    Boolean addUser(IMaoTaiUserLoginDTO IMaoTaiUserLoginDTO);

    Boolean updateUser(IMaoTaiUserUpdateDTO iMaoTaiUserUpdateDTO);

    Boolean deleteUser(Long recordId);

    IMaoTaiUserDTO detail(Long recordId);

    IPage<IMaoTaiUserDTO> page(IMaoTaiUserPageDTO iMaoTaiUserPageDTO);

    List<IMaoTaiUserDO> selectByOffset(Integer offset);

    void updateOffset(IMaoTaiUserDO iMaoTaiUserDO);
}
