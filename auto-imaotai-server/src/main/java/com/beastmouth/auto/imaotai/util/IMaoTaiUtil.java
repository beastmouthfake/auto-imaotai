package com.beastmouth.auto.imaotai.util;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
public class IMaoTaiUtil {
    private static final String SALT = "2af72f100c356273d46284f6fd1dfc08";

    private static final String AES_KEY = "qbhajinldepmucsonaaaccgypwuvcjaa";
    private static final String AES_IV = "2018534749963515";

    public static String deviceId() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取验证码的md5签名，密钥+手机号+时间
     * 登录的md5签名：密钥+mobile+vCode+ydLogId+ydToken
     *
     * @return 签名结果
     */
    public static String signature(Map<String, String> data) {
        Map<String, String> sortedTreeMap = new TreeMap<>(data);
        StringBuilder sb = new StringBuilder();
        sb.append(SALT);
        for (Map.Entry<String, String> entry : sortedTreeMap.entrySet()) {
            sb.append(entry.getValue());
        }
        return DigestUtil.md5Hex(sb.toString());
    }

    /**
     * 加密
     *
     * @param params
     * @return
     */
    public static String aesEncrypt(String params) {
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, AES_KEY.getBytes(), AES_IV.getBytes());
        return aes.encryptBase64(params);
    }

    public static Integer reservationTimeOffset() {
        Random random = new Random();
        return random.nextInt(30) + 1;
    }
}
