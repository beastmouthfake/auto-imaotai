package com.beastmouth.auto.imaotai.spi.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSONObject;
import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiShopEntity;
import com.beastmouth.auto.imaotai.spi.IMaoTaiShopSpiAdapter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class MaoTaiShopSpiAdapterImpl implements IMaoTaiShopSpiAdapter {

    @Override
    public List<IMaoTaiShopEntity> getShopList() {
        HttpRequest request = HttpUtil.createRequest(Method.GET,
                "https://static.moutai519.com.cn/mt-backend/xhr/front/mall/resource/get");

        JSONObject body = JSONObject.parseObject(request.execute().body());
        //获取shop的url
        String shopUrl = body.getJSONObject("data").getJSONObject("mtshops_pc").getString("url");
        String s = HttpUtil.get(shopUrl);
        JSONObject jsonObject = JSONObject.parseObject(s);
        Set<String> shopIdSet = jsonObject.keySet();
        List<IMaoTaiShopEntity> list = new ArrayList<>();
        for (String shopId : shopIdSet) {
            JSONObject shop = jsonObject.getJSONObject(shopId);
            IMaoTaiShopEntity shopEntity = convertIMaoTaiShopEntity(shopId, shop);
            list.add(shopEntity);
        }
        return list;
    }

    private IMaoTaiShopEntity convertIMaoTaiShopEntity(String shopId, JSONObject shop) {
        IMaoTaiShopEntity iMaoTaiShopEntity = new IMaoTaiShopEntity();
        iMaoTaiShopEntity.setShopId(shopId);
        iMaoTaiShopEntity.setProvince(shop.getString("province"));
        iMaoTaiShopEntity.setProvinceName(shop.getString("provinceName"));
        iMaoTaiShopEntity.setCity(shop.getString("city"));
        iMaoTaiShopEntity.setCityName(shop.getString("cityName"));
        iMaoTaiShopEntity.setDistrict(shop.getString("district"));
        iMaoTaiShopEntity.setDistrictName(shop.getString("districtName"));
        iMaoTaiShopEntity.setAddress(shop.getString("address"));
        iMaoTaiShopEntity.setFullAddress(shop.getString("fullAddress"));
        iMaoTaiShopEntity.setLat(shop.getString("lat"));
        iMaoTaiShopEntity.setLng(shop.getString("lng"));
        iMaoTaiShopEntity.setName(shop.getString("name"));
        iMaoTaiShopEntity.setTenantName(shop.getString("tenantName"));
        iMaoTaiShopEntity.setOpenEndTime(shop.getString("openEndTime"));
        iMaoTaiShopEntity.setOpenStartTime(shop.getString("openStartTime"));
        iMaoTaiShopEntity.setLayaway(shop.getBoolean("layaway"));
        iMaoTaiShopEntity.setTags(shop.getString("tags"));
        return iMaoTaiShopEntity;
    }
}
