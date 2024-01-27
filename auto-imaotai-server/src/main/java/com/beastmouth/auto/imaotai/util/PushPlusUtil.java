package com.beastmouth.auto.imaotai.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PushPlusUtil {
    private interface Constant {
        String PUSH_PLUS_BASE_URL = "http://www.pushplus.plus/send";
    }

    /**
     * 发送消息
     *
     * @param token pushplus token 不能为空
     * @param title 标题 可为空
     * @param msg   消息内容 不能为空
     */
    public static boolean sendMsg(String token, String title, String msg) {
        HttpRequest request = HttpUtil.createRequest(Method.POST, Constant.PUSH_PLUS_BASE_URL);
        HttpResponse execute = request.body(JSONObject.toJSONString(PushPlusMessage.builder().token(token).title(title).content(msg).build())).execute();
        JSONObject body = JSONObject.parseObject(execute.body());
        if (body.getInteger("code") != 200) {
            log.error("发送pushplus消息失败, result:{}", JSON.toJSONString(body));
            return false;
        }
        return true;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class PushPlusMessage {
        private String token;
        private String title;
        private String content;
    }
}
