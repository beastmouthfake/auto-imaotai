package com.beastmouth.auto.imaotai.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiItemEntity;
import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiUserEntity;
import com.beastmouth.auto.imaotai.service.IMaoTaiCommonService;
import com.beastmouth.auto.imaotai.service.IMaoTaiItemService;
import com.beastmouth.auto.imaotai.spi.IMaoTaiSpiAdapter;
import com.beastmouth.auto.imaotai.util.IMaoTaiUtil;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MaoTaiCommonServiceImpl implements IMaoTaiCommonService {
    @Resource(name = "iMaoTaiCommonCache")
    private Cache<String, String> iMaoTaiCommonCache;
    @Resource
    private IMaoTaiSpiAdapter iMaoTaiSpiAdapter;
    @Resource
    private IMaoTaiItemService iMaoTaiItemService;

    @Override
    public void initRefresh() {
        iMaoTaiCommonCache.invalidate("mt_version");
        iMaoTaiCommonCache.invalidate("mt_session_id");
        getMTVersion();
        getSessionId();
    }

    @Override
    public String getMTVersion() {
        try {
            return iMaoTaiCommonCache.get("mt_version", () -> {
                String mtVersion = iMaoTaiSpiAdapter.getMTVersion();
                iMaoTaiCommonCache.put("mt_version", mtVersion);
                return mtVersion;
            });
        } catch (Exception e) {
            log.error("[获取i茅台版本号] 失败, msg:{}", e.getMessage());
            return null;
        }
    }

    @Override
    public String getSessionId() {
        try {
            return iMaoTaiCommonCache.get("mt_session_id", () -> {
                JSONObject jsonObject = iMaoTaiSpiAdapter.getCurrentSessionId();
                String sessionId = jsonObject.getJSONObject("data").getString("sessionId");
                iMaoTaiCommonCache.put("mt_session_id", sessionId);

                // refresh items
                JSONArray itemJsonArray = jsonObject.getJSONObject("data").getJSONArray("itemList");
                List<IMaoTaiItemEntity> iMaoTaiItemEntities = itemJsonArray.stream().map(i -> {
                    IMaoTaiItemEntity temp = new IMaoTaiItemEntity();
                    temp.setItemCode(((JSONObject) i).getString("itemCode"));
                    temp.setPictureV2(((JSONObject) i).getString("pictureV2"));
                    temp.setCheckTag(((JSONObject) i).getInteger("checkTag"));
                    temp.setTitle(((JSONObject) i).getString("title"));
                    temp.setPicture(((JSONObject) i).getString("picture"));
                    temp.setContent(((JSONObject) i).getString("content"));
                    temp.setJumpUrl(((JSONObject) i).getString("jumpUrl"));
                    return temp;
                }).collect(Collectors.toList());
                iMaoTaiItemService.refreshItem(iMaoTaiItemEntities);
                return sessionId;
            });
        } catch (Exception e) {
            log.error("[获取i茅台今日SessionId] 失败, msg:{}", e.getMessage());
            return null;
        }
    }

    @Override
    public String sendCode(String mobile) {
        String mtVersion = getMTVersion();
        String deviceId = IMaoTaiUtil.deviceId();
        if (StrUtil.isBlank(mobile) || StrUtil.isBlank(mtVersion) || StrUtil.isBlank(deviceId)) {
            log.error("[发送i茅台验证码] 参数非法, mobile:{}, mtVersion:{}, deviceId:{}", mobile, mtVersion, deviceId);
            throw new RuntimeException("[发送i茅台验证码] 参数非法");
        }
        Boolean isSuccess = iMaoTaiSpiAdapter.sendCode(mobile, deviceId, mtVersion);
        if (!isSuccess) {
            throw new RuntimeException("[发送i茅台验证码] 发送失败");
        }
        return deviceId;
    }

    @Override
    public IMaoTaiUserEntity login(String mobile, String code, String deviceId) {
        String mtVersion = getMTVersion();
        if (StrUtil.isBlank(mobile) || StrUtil.isBlank(code) || StrUtil.isBlank(mtVersion) || StrUtil.isBlank(deviceId)) {
            log.error("[登陆i茅台] 参数非法, mobile:{}, code:{}, mtVersion:{}, deviceId:{}", mobile, code, mtVersion, deviceId);
            throw new RuntimeException("[登陆i茅台] 参数非法");
        }
        return iMaoTaiSpiAdapter.login(mobile, code, deviceId, mtVersion);
    }
}
