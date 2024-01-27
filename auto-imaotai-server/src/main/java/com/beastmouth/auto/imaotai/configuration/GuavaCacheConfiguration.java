package com.beastmouth.auto.imaotai.configuration;

import com.beastmouth.auto.imaotai.entity.imaotai.IMaoTaiShopEntity;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class GuavaCacheConfiguration {
    @Bean(name = "iMaoTaiCommonCache")
    public Cache<String, String> iMaoTaiCommonCache() {
        return CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(); // 设置缓存过期时间
    }

    @Bean(name = "iMaoTaiShopCache")
    public Cache<String, List<IMaoTaiShopEntity>> iMaoTaiShopCache() {
        return CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.DAYS).build(); // 设置缓存过期时间
    }
}
