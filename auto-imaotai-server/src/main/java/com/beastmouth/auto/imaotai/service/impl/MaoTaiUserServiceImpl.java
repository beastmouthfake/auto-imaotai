package com.beastmouth.auto.imaotai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiShopEntity;
import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiUserEntity;
import com.beastmouth.auto.imaotai.entity.req.user.IMaoTaiUserLoginDTO;
import com.beastmouth.auto.imaotai.entity.req.user.IMaoTaiUserPageDTO;
import com.beastmouth.auto.imaotai.entity.req.user.IMaoTaiUserUpdateDTO;
import com.beastmouth.auto.imaotai.entity.res.user.IMaoTaiUserDTO;
import com.beastmouth.auto.imaotai.mapper.IMaoTaiUserMapper;
import com.beastmouth.auto.imaotai.mapper.entity.IMaoTaiUserDO;
import com.beastmouth.auto.imaotai.service.IMaoTaiCommonService;
import com.beastmouth.auto.imaotai.service.IMaoTaiShopService;
import com.beastmouth.auto.imaotai.service.IMaoTaiUserService;
import com.beastmouth.auto.imaotai.util.IMaoTaiUtil;
import com.beastmouth.auto.imaotai.util.SnowflakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MaoTaiUserServiceImpl implements IMaoTaiUserService {
    @Resource
    private IMaoTaiCommonService iMaoTaiCommonService;
    @Resource
    private IMaoTaiUserMapper iMaoTaiUserMapper;
    @Resource
    private IMaoTaiShopService iMaoTaiShopService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean login(IMaoTaiUserLoginDTO IMaoTaiUserLoginDTO) {
        IMaoTaiUserEntity iMaoTaiUserEntity = iMaoTaiCommonService.login(IMaoTaiUserLoginDTO.getMobile(), IMaoTaiUserLoginDTO.getCode(), IMaoTaiUserLoginDTO.getDeviceId());
        if (iMaoTaiUserEntity == null) {
            log.error("[登陆i茅台] 登陆失败, loginDTO:{}", JSON.toJSONString(IMaoTaiUserLoginDTO));
            return false;
        }
        IMaoTaiUserDO oldData = selectByUserId(iMaoTaiUserEntity.getUserId());
        if (oldData != null) {
            // 用户已存在, 重新登陆更新信息
            BeanUtil.copyProperties(iMaoTaiUserEntity, oldData);
            if (StrUtil.isNotBlank(IMaoTaiUserLoginDTO.getBindShopId())) {
                IMaoTaiShopEntity shopInfo = iMaoTaiShopService.getByShopId(IMaoTaiUserLoginDTO.getBindShopId());
                if (shopInfo != null) userBindShop(oldData, shopInfo);
            }
            iMaoTaiUserMapper.updateById(oldData);
            return Boolean.TRUE;
        }

        // 用户不存在, 新增用户
        IMaoTaiUserDO iMaoTaiUserDO = buildDO(iMaoTaiUserEntity, IMaoTaiUserLoginDTO);
        if (StrUtil.isNotBlank(IMaoTaiUserLoginDTO.getBindShopId())) {
            IMaoTaiShopEntity shopInfo = iMaoTaiShopService.getByShopId(IMaoTaiUserLoginDTO.getBindShopId());
            if (shopInfo != null) userBindShop(iMaoTaiUserDO, shopInfo);
        }
        int result = iMaoTaiUserMapper.insert(iMaoTaiUserDO);
        return result > 0;
    }

    @Override
    public Boolean bindShop(Long recordId, String shopId) {
        // 绑定门店【即修改预定的经纬度】
        IMaoTaiUserDO iMaoTaiUser = iMaoTaiUserMapper.selectById(recordId);
        if (iMaoTaiUser == null) {
            log.error("[绑定门店] 用户不存在, recordId:{}", recordId);
            throw new RuntimeException("[绑定门店] 用户不存在");
        }
        IMaoTaiShopEntity shopInfo = iMaoTaiShopService.getByShopId(shopId);
        if (shopInfo == null) {
            log.error("[绑定门店] 门店不存在, shopId:{}", shopId);
            throw new RuntimeException("[绑定门店] 门店不存在");
        }
        userBindShop(iMaoTaiUser, shopInfo);
        iMaoTaiUserMapper.updateById(iMaoTaiUser);
        return Boolean.TRUE;
    }

    @Override
    public Boolean addUser(IMaoTaiUserLoginDTO iMaoTaiUserLoginDTO) {
        String mobile = iMaoTaiUserLoginDTO.getMobile();
        IMaoTaiUserDO checkMobile = selectByMobile(mobile);
        if (checkMobile != null) {
            log.error("[添加用户] 手机号已存在, mobile:{}", mobile);
            throw new RuntimeException("[添加用户] 手机号已存在");
        }
        // 用户先搜索适合门店，所以这个场景门店不会为空
        IMaoTaiShopEntity shopInfo = iMaoTaiShopService.getByShopId(iMaoTaiUserLoginDTO.getBindShopId());
        if (shopInfo == null) {
            log.error("[添加用户] 门店不存在, shopId:{}", iMaoTaiUserLoginDTO.getBindShopId());
            throw new RuntimeException("[添加用户] 请先选择存在门店");
        }
        IMaoTaiUserEntity iMaoTaiUserEntity = iMaoTaiCommonService.login(iMaoTaiUserLoginDTO.getMobile(), iMaoTaiUserLoginDTO.getCode(), iMaoTaiUserLoginDTO.getDeviceId());
        if (iMaoTaiUserEntity == null) {
            log.error("[添加用户] 登陆失败, loginDTO:{}", JSON.toJSONString(iMaoTaiUserLoginDTO));
            return false;
        }
        IMaoTaiUserDO iMaoTaiUserDO = buildDO(iMaoTaiUserEntity, iMaoTaiUserLoginDTO);
        userBindShop(iMaoTaiUserDO, shopInfo);
        iMaoTaiUserDO.setItemCode(JSON.toJSONString(new ArrayList<>()));
        int result = iMaoTaiUserMapper.insert(iMaoTaiUserDO);
        return result > 0;
    }

    @Override
    public Boolean updateUser(IMaoTaiUserUpdateDTO iMaoTaiUserUpdateDTO) {
        IMaoTaiUserDO iMaoTaiUserDO = iMaoTaiUserMapper.selectById(iMaoTaiUserUpdateDTO.getRecordId());
        if (iMaoTaiUserDO == null) {
            log.error("[用户修改] 用户不存在, recordId:{}", iMaoTaiUserUpdateDTO.getRecordId());
            throw new RuntimeException("[用户修改] 用户不存在");
        }
        if (StrUtil.isNotBlank(iMaoTaiUserUpdateDTO.getPushPlusToken())) {
            iMaoTaiUserDO.setPushPlusToken(iMaoTaiUserUpdateDTO.getPushPlusToken());
        }
        if (StrUtil.isNotBlank(iMaoTaiUserUpdateDTO.getBindShopId())) {
            IMaoTaiShopEntity shopInfo = iMaoTaiShopService.getByShopId(iMaoTaiUserUpdateDTO.getBindShopId());
            if (shopInfo == null) {
                log.error("[用户修改] 门店不存在, shopId:{}", iMaoTaiUserUpdateDTO.getBindShopId());
                throw new RuntimeException("[用户修改] 请先选择存在门店");
            }
            userBindShop(iMaoTaiUserDO, shopInfo);
        }
        if (StrUtil.isNotBlank(iMaoTaiUserUpdateDTO.getItemCode())) {
            List<String> itemCodes = JSON.parseArray(iMaoTaiUserUpdateDTO.getItemCode(), String.class);
            // TODO 校验itemCode合法性
            iMaoTaiUserDO.setItemCode(iMaoTaiUserUpdateDTO.getItemCode());
        }
        iMaoTaiUserDO.setDateUpdate(new Date());
        int result = iMaoTaiUserMapper.updateById(iMaoTaiUserDO);
        return result > 0;
    }

    @Override
    public Boolean deleteUser(Long recordId) {
        return iMaoTaiUserMapper.deleteById(recordId) >= 0;
    }

    @Override
    public IMaoTaiUserDTO detail(Long recordId) {
        IMaoTaiUserDO iMaoTaiUserDO = iMaoTaiUserMapper.selectById(recordId);
        if (iMaoTaiUserDO == null) {
            log.error("[用户详情] 用户不存在, recordId:{}", recordId);
            throw new RuntimeException("[用户详情] 用户不存在");
        }
        IMaoTaiUserDTO iMaoTaiUserDTO = new IMaoTaiUserDTO();
        BeanUtil.copyProperties(iMaoTaiUserDO, iMaoTaiUserDTO);
        iMaoTaiUserDTO.setRecordId(iMaoTaiUserDO.getId());
        return iMaoTaiUserDTO;
    }

    @Override
    public IPage<IMaoTaiUserDTO> page(IMaoTaiUserPageDTO iMaoTaiUserPageDTO) {
        if (iMaoTaiUserPageDTO.getPage() == null) iMaoTaiUserPageDTO.setPage(1);
        if (iMaoTaiUserPageDTO.getPageSize() == null) iMaoTaiUserPageDTO.setPageSize(20);
        IPage<IMaoTaiUserDO> page = new Page<>(iMaoTaiUserPageDTO.getPage(), iMaoTaiUserPageDTO.getPageSize());
        LambdaQueryWrapper<IMaoTaiUserDO> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(iMaoTaiUserPageDTO.getMobileShow())) {
            wrapper.eq(IMaoTaiUserDO::getMobileShow, iMaoTaiUserPageDTO.getMobileShow());
        }
        IPage<IMaoTaiUserDO> dbPage = iMaoTaiUserMapper.selectPage(page, wrapper);
        IPage<IMaoTaiUserDTO> result = new Page<>();
        BeanUtil.copyProperties(dbPage, result);
        if (dbPage.getRecords() == null || dbPage.getRecords().isEmpty()) {
            return result;
        }
        result.setRecords(dbPage.getRecords().stream().map(temp -> {
            IMaoTaiUserDTO iMaoTaiUserDTO = new IMaoTaiUserDTO();
            BeanUtil.copyProperties(temp, iMaoTaiUserDTO);
            iMaoTaiUserDTO.setRecordId(temp.getId());
            return iMaoTaiUserDTO;
        }).collect(java.util.stream.Collectors.toList()));
        return result;
    }

    @Override
    public List<IMaoTaiUserDO> selectByOffset(Integer offset) {
        LambdaQueryWrapper<IMaoTaiUserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IMaoTaiUserDO::getReservationTimeOffset, offset);
        wrapper.eq(IMaoTaiUserDO::getDateDelete, 0);
        return iMaoTaiUserMapper.selectList(wrapper);
    }

    @Override
    public void updateOffset(IMaoTaiUserDO iMaoTaiUserDO) {
        iMaoTaiUserDO.setReservationTimeOffset(IMaoTaiUtil.reservationTimeOffset());
        iMaoTaiUserMapper.updateById(iMaoTaiUserDO);
    }

    private IMaoTaiUserDO selectByUserId(Long userId) {
        LambdaQueryWrapper<IMaoTaiUserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IMaoTaiUserDO::getUserId, userId);
        wrapper.eq(IMaoTaiUserDO::getDateDelete, 0);
        return iMaoTaiUserMapper.selectOne(wrapper);
    }

    private IMaoTaiUserDO selectByMobile(String mobile) {
        LambdaQueryWrapper<IMaoTaiUserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IMaoTaiUserDO::getMobileShow, mobile);
        wrapper.eq(IMaoTaiUserDO::getDateDelete, 0);
        return iMaoTaiUserMapper.selectOne(wrapper);
    }

    private void userBindShop(IMaoTaiUserDO iMaoTaiUser, IMaoTaiShopEntity shopInfo) {
        iMaoTaiUser.setLat(shopInfo.getLat());
        iMaoTaiUser.setLng(shopInfo.getLng());
        iMaoTaiUser.setShopId(shopInfo.getShopId());
    }

    private IMaoTaiUserDO buildDO(IMaoTaiUserEntity iMaoTaiUser, IMaoTaiUserLoginDTO IMaoTaiUserLoginDTO) {
        IMaoTaiUserDO iMaoTaiUserDO = new IMaoTaiUserDO();
        BeanUtil.copyProperties(iMaoTaiUser, iMaoTaiUserDO);
        iMaoTaiUserDO.setId(SnowflakeUtil.nextId());
        iMaoTaiUserDO.setDateCreate(new Date());
        iMaoTaiUserDO.setMobileShow(IMaoTaiUserLoginDTO.getMobile());
        iMaoTaiUserDO.setReservationTimeOffset(IMaoTaiUtil.reservationTimeOffset());
        iMaoTaiUserDO.setLoginTime(new Date());
        iMaoTaiUserDO.setPushPlusToken(IMaoTaiUserLoginDTO.getPushPlusToken());
        iMaoTaiUserDO.setDeviceId(IMaoTaiUserLoginDTO.getDeviceId());
        return iMaoTaiUserDO;
    }
}
