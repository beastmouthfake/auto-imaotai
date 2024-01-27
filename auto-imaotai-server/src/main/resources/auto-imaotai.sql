CREATE TABLE `i_maotai_item`
(
    `id`          bigint NOT NULL,
    `date_create` datetime                                                      DEFAULT NULL,
    `date_update` datetime                                                      DEFAULT NULL,
    `date_delete` bigint                                                        DEFAULT '0',
    `item_code`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT 'I茅台接口返回, ',
    `picture_v2`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT 'I茅台接口返回, ',
    `check_tag`   int                                                           DEFAULT NULL COMMENT 'I茅台接口返回, checkTag',
    `title`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'I茅台接口返回, ',
    `picture`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT 'I茅台接口返回, ',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT 'I茅台接口返回, ',
    `jumpUrl`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT 'I茅台接口返回, ',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `i_maotai_shop`
(
    `id`              bigint NOT NULL,
    `date_create`     datetime                                                      DEFAULT NULL,
    `date_update`     datetime                                                      DEFAULT NULL,
    `date_delete`     bigint                                                        DEFAULT '0',
    `shop_id`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'I茅台接口返回, 门店id',
    `province`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT 'I茅台接口返回, 省份code',
    `province_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT 'I茅台接口返回, 省份名称',
    `city`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT 'I茅台接口返回, 城市code',
    `city_name`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT 'I茅台接口返回, 城市名称',
    `district`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT 'I茅台接口返回, 地区code',
    `district_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT 'I茅台接口返回, 地区名称',
    `address`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'I茅台接口返回, 详细地址',
    `full_address`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'I茅台接口返回, 完整地址',
    `lat`             varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT 'I茅台接口返回, 纬度',
    `lng`             varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT 'I茅台接口返回, 经度',
    `name`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'I茅台接口返回, 门店名称',
    `tenant_name`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'I茅台接口返回, 公司名称',
    `open_end_time`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'I茅台接口返回, 营业时间',
    `open_start_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'I茅台接口返回, 营业时间',
    `layaway`         tinyint                                                       DEFAULT NULL COMMENT 'I茅台接口返回, 是否支持到店自提',
    `tags`            json                                                          DEFAULT NULL COMMENT 'I茅台接口返回, 标签',
    PRIMARY KEY (`id`),
    KEY `idx_shop_id` (`shop_id`) USING BTREE COMMENT '门店id索引',
    KEY `idx_province` (`province_name`) COMMENT '省份索引',
    KEY `idx_city` (`city_name`) COMMENT '城市索引',
    KEY `idx_district` (`district_name`) COMMENT '区县索引'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `i_maotai_user`
(
    `id`                      bigint NOT NULL,
    `date_create`             datetime                                                     DEFAULT NULL,
    `date_update`             datetime                                                     DEFAULT NULL,
    `date_delete`             bigint                                                       DEFAULT '0',
    `verify_status`           int                                                          DEFAULT NULL COMMENT 'I茅台接口返回, 校验状态',
    `cookie`                  varchar(1024) COLLATE utf8mb4_general_ci                     DEFAULT NULL COMMENT 'I茅台接口返回, 用户cookie',
    `user_tag`                int                                                          DEFAULT NULL COMMENT 'I茅台接口返回, 用户标签',
    `mobile`                  varchar(16) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT 'I茅台接口返回, 用户手机号 加敏',
    `user_name`               varchar(64) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT 'I茅台接口返回, 用户名称',
    `user_id`                 bigint                                                       DEFAULT NULL COMMENT 'I茅台接口返回, 用户id',
    `token`                   varchar(1024) COLLATE utf8mb4_general_ci                     DEFAULT NULL COMMENT 'I茅台接口返回, 用户token',
    `mobile_show`             varchar(16) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT '用户手机号 脱敏',
    `reservation_time_offset` int                                                          DEFAULT NULL COMMENT '预约时间偏移（较9点偏移分钟数）（0-30）',
    `login_time`              datetime                                                     DEFAULT NULL COMMENT '登陆时间 30天后需要重新登陆',
    `push_plus_token`         varchar(512) COLLATE utf8mb4_general_ci                      DEFAULT NULL COMMENT '消息推送token',
    `device_id`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备id',
    `lat`                     varchar(64) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT '所在经纬度，预约时用',
    `lng`                     varchar(64) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT '所在经纬度，预约时用',
    `item_code`               json                                                         DEFAULT NULL COMMENT '预约商品code',
    `shop_id`                 varchar(255) COLLATE utf8mb4_general_ci                      DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_mobile` (`mobile`) USING BTREE COMMENT '手机号索引',
    KEY `idx_user_id` (`user_id`) USING BTREE COMMENT '用户id索引'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;