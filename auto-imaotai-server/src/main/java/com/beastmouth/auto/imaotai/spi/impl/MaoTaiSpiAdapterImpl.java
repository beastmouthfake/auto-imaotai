package com.beastmouth.auto.imaotai.spi.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiUserEntity;
import com.beastmouth.auto.imaotai.spi.IMaoTaiSpiAdapter;
import com.beastmouth.auto.imaotai.util.IMaoTaiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class MaoTaiSpiAdapterImpl implements IMaoTaiSpiAdapter {


    @Override
    public String getMTVersion() {
        String mtVersion = null;
        String url = "https://apps.apple.com/cn/app/i%E8%8C%85%E5%8F%B0/id1600482450";
        String htmlContent = HttpUtil.get(url);
        Pattern pattern = Pattern.compile("new__latest__version\">(.*?)</p>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(htmlContent);
        if (matcher.find()) {
            mtVersion = matcher.group(1);
            mtVersion = mtVersion.replace("版本 ", "");
        }
        return mtVersion;
    }

    @Override
    public Boolean sendCode(String mobile, String deviceId, String mtVersion) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        Map<String, String> data = new HashMap<>();
        data.put("mobile", mobile);
        data.put("timestamp", timestamp);
        data.put("md5", IMaoTaiUtil.signature(data));

        HttpRequest request = HttpUtil.createRequest(Method.POST, "https://app.moutai519.com.cn/xhr/front/user/register/vcode");

        request.header("MT-Device-ID", deviceId);
        request.header("MT-APP-Version", mtVersion);
        request.header("User-Agent", "iOS;16.3;Apple;?unrecognized?");
        request.header("Content-Type", "application/json");

        HttpResponse execute = request.body(JSONObject.toJSONString(data)).execute();
        JSONObject jsonObject = JSONObject.parseObject(execute.body());
        //成功返回 {"code":2000}
        log.info("[发送i茅台验证码] resutl:{}", jsonObject.toJSONString());
        if (jsonObject.getString("code").equals("2000")) {
            return Boolean.TRUE;
        } else {
            log.error("[发送i茅台验证码] 失败, reuslt:{}", jsonObject.toJSONString());
            throw new RuntimeException("[发送i茅台验证码] 失败");
        }
    }

    @Override
    public IMaoTaiUserEntity login(String mobile, String code, String deviceId, String mtVersion) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("vCode", code);
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("MT-APP-Version", mtVersion);

        map.put("md5", IMaoTaiUtil.signature(map));

        HttpRequest request = HttpUtil.createRequest(Method.POST, "https://app.moutai519.com.cn/xhr/front/user/register/login");
        request.header("MT-Device-ID", deviceId);
        request.header("MT-APP-Version", mtVersion);
        request.header("User-Agent", "iOS;16.3;Apple;?unrecognized?");
        request.header("Content-Type", "application/json");

        HttpResponse execute = request.body(JSONObject.toJSONString(map)).execute();

        JSONObject body = JSONObject.parseObject(execute.body());
        log.info("[登陆i茅台] 登陆结果, result:{}", body.toJSONString());
        log.info("[登陆i茅台] {}", body.getString("code").equals("2000") ? "[登陆] 登陆成功" : "[登陆] 登陆失败");
        if (body.getString("code").equals("2000")) {
            return body.getObject("data", IMaoTaiUserEntity.class);
        } else {
            log.error("[登陆i茅台] 失败");
            throw new RuntimeException("[登陆i茅台] 失败");
        }
    }

    @Override
    public JSONObject getCurrentSessionId() {
        long dayTime = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String res = HttpUtil.get("https://static.moutai519.com.cn/mt-backend/xhr/front/mall/index/session/get/" + dayTime);
        //替换 current_session_id 673 ['data']['sessionId']
        return JSONObject.parseObject(res);
    }

    @Override
    public JSONObject reservation(String itemId, String shopId, String currentSessionId,
                                  String userId, String lat, String lng, String token, String deviceId) {
        Map<String, Object> map = new HashMap<>();
        JSONArray itemArray = new JSONArray();
        Map<String, Object> info = new HashMap<>();
        info.put("count", 1);
        info.put("itemId", itemId);

        itemArray.add(info);

        map.put("itemInfoList", itemArray);

        map.put("sessionId", currentSessionId);
        map.put("userId", userId);
        map.put("shopId", shopId);

        map.put("actParam", IMaoTaiUtil.aesEncrypt(JSON.toJSONString(map)));

        HttpRequest request = HttpUtil.createRequest(Method.POST, "https://app.moutai519.com.cn/xhr/front/mall/reservation/add");

        request.header("MT-Lat", lat);
        request.header("MT-Lng", lng);
        request.header("MT-Token", token);
        request.header("MT-Info", "028e7f96f6369cafe1d105579c5b9377");
        request.header("MT-Device-ID", deviceId);
        request.header("MT-APP-Version", getMTVersion());
        request.header("User-Agent", "iOS;16.3;Apple;?unrecognized?");
        request.header("Content-Type", "application/json");
        request.header("userId", userId.toString());

        HttpResponse execute = request.body(JSONObject.toJSONString(map)).execute();

        JSONObject body = JSONObject.parseObject(execute.body());
        log.info("[申购i茅台] 申购结果, result:{}", JSON.toJSONString(body));
        //{"code":2000,"data":{"successDesc":"申购完成，请于7月6日18:00查看预约申购结果","reservationList":[{"reservationId":17053404357,"sessionId":678,"shopId":"233331084001","reservationTime":1688608601720,"itemId":"10214","count":1}],"reservationDetail":{"desc":"申购成功后将以短信形式通知您，请您在申购成功次日18:00前确认支付方式，并在7天内完成提货。","lotteryTime":1688637600000,"cacheValidTime":1688637600000}}}
        if (body.getInteger("code") != 2000) {
            String message = body.getString("message");
            log.error("[申购i茅台] 失败, userId:{}, message:{}", userId, message);
        }
        return body;
    }
}
