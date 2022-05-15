/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : mall_nacos

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 15/05/2022 15:21:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (3, 'application.yml', 'dev', '# 端口\nserver:\n  port: 6000\n\n# 数据源\nspring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/mall_coupon?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false\n    username: root\n    password: 987654\n  application:\n    name: coupon\n  # 注册中心\n  cloud:\n    nacos:\n      discovery:\n        server-addr: 127.0.0.1:8847\n# mapper映射地址\nmybatis-plus:\n  mapper-locations: classpath:/mapper/**/*.xml\n#  主键自增\n  global-config:\n    db-config:\n      id-type: auto\n\n# 日志打印级别\nlogging:\n  level:\n    cn.xf.product: debug', '4965226ee912c45492bb5034aa7f372f', '2022-02-10 09:53:45', '2022-03-24 10:33:37', 'nacos', '0:0:0:0:0:0:0:1', '', '66aa6954-27a3-460c-9b2d-e613187af93f', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (4, 'application.yml', 'dev', '# 端口\nserver:\n  port: 7000\n\n# 数据源\nspring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/mall_member?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false\n    username: root\n    password: 987654\n  application:\n    name: member\n  # 注册中心\n  cloud:\n    nacos:\n      discovery:\n        server-addr: 127.0.0.1:8847\n# mapper映射地址\nmybatis-plus:\n  mapper-locations: classpath:/mapper/**/*.xml\n#  主键自增\n  global-config:\n    db-config:\n      id-type: auto\n\n# 日志打印级别\nlogging:\n  level:\n    cn.xf.product: debug', '1478c5777106f074f4e7420eeea67865', '2022-02-10 09:54:11', '2022-03-24 10:33:49', 'nacos', '0:0:0:0:0:0:0:1', '', '6b8e46bc-6bbf-41de-a890-92df84a6f0a7', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (5, 'application.yml', 'dev', '# 端口\r\nserver:\r\n  port: 8000\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    url: jdbc:mysql://127.0.0.1:3306/mall_order\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    username: root\r\n    password: 987654\r\n# mapper.xml扫描\r\nmybatis-plus:\r\n  mapper-locations: classpath:mapper/**/.*xml\r\n  global-config:\r\n    db-config:\r\n      id-type: auto', '1aebfd439442e68b774358d4aba9e58a', '2022-02-10 09:54:39', '2022-02-10 09:54:39', 'nacos', '127.0.0.1', '', '889f5306-318f-4a18-aeda-522b57e408fb', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (6, 'application.yml', 'dev', '# 端口\nserver:\n  port: 9000\n\n# 数据源\nspring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/mall_product?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false\n    username: root\n    password: 987654\n  application:\n    name: product\n  # 注册中心\n  cloud:\n    nacos:\n      discovery:\n        server-addr: 127.0.0.1:8847\n# mapper映射地址\nmybatis-plus:\n  mapper-locations: classpath:/mapper/**/*.xml\n#  主键自增\n  global-config:\n    db-config:\n      id-type: auto\n\n# 日志打印级别\nlogging:\n  level:\n    cn.xf.product: debug', 'e8dabb72ae134e9d0a3541e15c14c547', '2022-02-10 09:55:00', '2022-02-15 05:12:22', 'nacos', '127.0.0.1', '', 'eb41d029-6c65-47df-9cdb-a17752e6d6ab', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (7, 'application.yml', 'dev', '# 端口\r\nserver:\r\n  port: 10000\r\n\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://127.0.0.1:3306/mall_warehousing\r\n    username: root\r\n    password: 987654\r\n\r\n# mapper映射地址\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/**/*.xml\r\n#  主键自增\r\n  global-config:\r\n    db-config:\r\n      id-type: auto', '0135827bae4f8d46ef85947956474344', '2022-02-10 09:55:21', '2022-02-10 09:55:21', 'nacos', '127.0.0.1', '', '5e06c141-8ef1-447f-a1c6-e416471f5a90', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (10, 'application.yml', 'dev', 'server:\n    port: 88\nspring:\n    application:\n        name: gateway\n    cloud:\n        nacos:\n            discovery:\n                server-addr: 127.0.0.1:8847\n        gateway:\n            routes:\n                - id: product_route\n                  uri: lb://product\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/product/**\n                  filters:\n                    # 将api下的所有请求，将api去掉剩下的为请求路径\n                    - RewritePath=/api/(?<segment>.*),/$\\{segment}\n\n                - id: member_route\n                  uri: lb://member\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/member/**\n                  filters:\n                    - RewritePath=/api/(?<segment>.*),/$\\{segment}\n                \n                - id: coupon_route\n                  uri: lb://coupon\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/coupon/**\n                  filters:\n                    - RewritePath=/api/(?<segment>.*),/$\\{segment}\n\n                - id: third_route\n                  uri: lb://third-party\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/thirdparty/**\n                  filters:\n                    - RewritePath=/api/thirdparty/(?<segment>.*),/$\\{segment}\n\n                - id: admin_route\n                  uri: lb://renren-fast\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/**\n                  filters:\n                    - RewritePath=/api/(?<segment>.*),/renren-fast/$\\{segment}\n              ', 'f5543777481240849bf70f7643019a92', '2022-02-11 02:19:27', '2022-03-24 10:31:59', 'nacos', '0:0:0:0:0:0:0:1', '', '3eefe1fe-bd89-41a8-898c-e972dd703bb1', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (20, 'application.yml', 'dev', 'server:\n    port: 30000\nspring:\n    application:\n        name: third-party\n    cloud:\n        nacos:\n            discovery:\n                server-addr: 127.0.0.1:8847\n        alicloud:\n            access-key: LTAI5tB5UAcGJtvCVwSGdNbD\n            secret-key: wEjGOiGRWRGsi8uzydRBTjESDYXkqm\n            oss:\n                endpoint: https://oss-cn-chengdu.aliyuncs.com\n                bucket: mallprotest', '698642569e21b1c04d7d25ee36faf667', '2022-02-16 03:30:56', '2022-02-16 04:42:37', 'nacos', '0:0:0:0:0:0:0:1', '', '933c7cdd-aae2-4d58-b9c8-ba7649b5031d', '', '', '', 'yaml', '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(0) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint(0) UNSIGNED NOT NULL,
  `nid` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (10, 31, 'application.yml', 'dev', '', 'server:\n    port: 88\nspring:\n    application:\n        name: gateway\n    cloud:\n        nacos:\n            discovery:\n                server-addr: 127.0.0.1:8847\n        gateway:\n            routes:\n                - id: product_route\n                  uri: lb://product\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/product/**\n                  filters:\n                    # 将api下的所有请求，将api去掉剩下的为请求路径\n                    - RewritePath=/api/(?<segment>.*),/$\\{segment}\n\n                - id: third_route\n                  uri: lb://third-party\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/thirdparty/**\n                  filters:\n                    - RewritePath=/api/thirdparty/(?<segment>.*),/$\\{segment}\n\n                - id: admin_route\n                  uri: lb://renren-fast\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/**\n                  filters:\n                    - RewritePath=/api/(?<segment>.*),/renren-fast/$\\{segment}\n', 'c655c656874f7f74936c14d9dec53ce9', '2022-03-24 17:57:36', '2022-03-24 09:57:36', 'nacos', '0:0:0:0:0:0:0:1', 'U', '3eefe1fe-bd89-41a8-898c-e972dd703bb1');
INSERT INTO `his_config_info` VALUES (4, 32, 'application.yml', 'dev', '', '# 端口\r\nserver:\r\n  port: 7000\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    url: jdbc:mysql://127.0.0.1:3306/mall_member\r\n    username: root\r\n    password: 987654\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: 127.0.0.1:8847\r\n\r\n# mapper.xml扫描\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/*.xml\r\n  # 主键自增\r\n  global-config:\r\n    db-config:\r\n      id-type: auto', '730a41f7b35eb1a17be2caf35002fcca', '2022-03-24 18:09:00', '2022-03-24 10:09:00', 'nacos', '0:0:0:0:0:0:0:1', 'U', '6b8e46bc-6bbf-41de-a890-92df84a6f0a7');
INSERT INTO `his_config_info` VALUES (4, 33, 'application.yml', 'dev', '', '# 端口\nserver:\n  port: 7000\n# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://127.0.0.1:3306/mall_member?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false\n    username: root\n    password: 987654\n    driver-class-name: com.mysql.cj.jdbc.Driver\n  cloud:\n    nacos:\n      discovery:\n        server-addr: 127.0.0.1:8847\n\n# mapper.xml扫描\nmybatis-plus:\n  mapper-locations: classpath:/mapper/*.xml\n  # 主键自增\n  global-config:\n    db-config:\n      id-type: auto', '14ffa2218cbc915f04698191677934ac', '2022-03-24 18:10:16', '2022-03-24 10:10:16', 'nacos', '0:0:0:0:0:0:0:1', 'U', '6b8e46bc-6bbf-41de-a890-92df84a6f0a7');
INSERT INTO `his_config_info` VALUES (10, 34, 'application.yml', 'dev', '', 'server:\n    port: 88\nspring:\n    application:\n        name: gateway\n    cloud:\n        nacos:\n            discovery:\n                server-addr: 127.0.0.1:8847\n        gateway:\n            routes:\n                - id: product_route\n                  uri: lb://product\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/product/**\n                  filters:\n                    # 将api下的所有请求，将api去掉剩下的为请求路径\n                    - RewritePath=/api/(?<segment>.*),/$\\{segment}\n\n                - id: third_route\n                  uri: lb://third-party\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/thirdparty/**\n                  filters:\n                    - RewritePath=/api/thirdparty/(?<segment>.*),/$\\{segment}\n\n                - id: admin_route\n                  uri: lb://renren-fast\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/**\n                  filters:\n                    - RewritePath=/api/(?<segment>.*),/renren-fast/$\\{segment}\n                \n                - id: member_route\n                  uri: lb://member\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/member/**\n                  filters:\n                    - RewritePath=/api/(?<segment>.*),/$\\{segment}\n', '660d1b140a1387f9ff4b7b648efc7f1b', '2022-03-24 18:14:47', '2022-03-24 10:14:48', 'nacos', '0:0:0:0:0:0:0:1', 'U', '3eefe1fe-bd89-41a8-898c-e972dd703bb1');
INSERT INTO `his_config_info` VALUES (10, 35, 'application.yml', 'dev', '', 'server:\n    port: 88\nspring:\n    application:\n        name: gateway\n    cloud:\n        nacos:\n            discovery:\n                server-addr: 127.0.0.1:8847\n        gateway:\n            routes:\n                - id: product_route\n                  uri: lb://product\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/product/**\n                  filters:\n                    # 将api下的所有请求，将api去掉剩下的为请求路径\n                    - RewritePath=/api/(?<segment>.*),/$\\{segment}\n\n                - id: third_route\n                  uri: lb://third-party\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/thirdparty/**\n                  filters:\n                    - RewritePath=/api/thirdparty/(?<segment>.*),/$\\{segment}\n\n                - id: member_route\n                  uri: lb://member\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/member/**\n                  filters:\n                    - RewritePath=/api/(?<segment>.*),/$\\{segment}\n\n                - id: admin_route\n                  uri: lb://renren-fast\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/**\n                  filters:\n                    - RewritePath=/api/(?<segment>.*),/renren-fast/$\\{segment}\n              ', '6187da767f23ff8d8a230b0d0b2db870', '2022-03-24 18:20:50', '2022-03-24 10:20:51', 'nacos', '0:0:0:0:0:0:0:1', 'U', '3eefe1fe-bd89-41a8-898c-e972dd703bb1');
INSERT INTO `his_config_info` VALUES (10, 36, 'application.yml', 'dev', '', 'server:\n    port: 88\nspring:\n    application:\n        name: gateway\n    cloud:\n        nacos:\n            discovery:\n                server-addr: 127.0.0.1:8847\n        gateway:\n            routes:\n                - id: product_route\n                  uri: lb://product\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/product/**\n                  filters:\n                    # 将api下的所有请求，将api去掉剩下的为请求路径\n                    - RewritePath=/api/(?<segment>.*),/$\\{segment}\n\n                - id: member_route\n                  uri: lb://member\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/member/**\n                  filters:\n                    - RewritePath=/api/(?<segment>.*),/$\\{segment}\n\n                - id: third_route\n                  uri: lb://third-party\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/thirdparty/**\n                  filters:\n                    - RewritePath=/api/thirdparty/(?<segment>.*),/$\\{segment}\n\n                - id: admin_route\n                  uri: lb://renren-fast\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/**\n                  filters:\n                    - RewritePath=/api/(?<segment>.*),/renren-fast/$\\{segment}\n              ', 'bb680068f4b13c986ea7404679aa924e', '2022-03-24 18:31:58', '2022-03-24 10:31:59', 'nacos', '0:0:0:0:0:0:0:1', 'U', '3eefe1fe-bd89-41a8-898c-e972dd703bb1');
INSERT INTO `his_config_info` VALUES (3, 37, 'application.yml', 'dev', '', '# 端口\r\nserver:\r\n  port: 6000\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    url: jdbc:mysql://127.0.0.1:3306/mall_coupon\r\n    username: root\r\n    password: 987654\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n\r\n# mapper.xml扫描\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/*.xml\r\n  # 主键自增\r\n  global-config:\r\n    db-config:\r\n      id-type: auto\r\n', 'ef2950356159576db97c5caf289e9a3d', '2022-03-24 18:33:37', '2022-03-24 10:33:37', 'nacos', '0:0:0:0:0:0:0:1', 'U', '66aa6954-27a3-460c-9b2d-e613187af93f');
INSERT INTO `his_config_info` VALUES (4, 38, 'application.yml', 'dev', '', '# 端口\nserver:\n  port: 7000\n\n# 数据源\nspring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/mall_member?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false\n    username: root\n    password: 987654\n  application:\n    name: product\n  # 注册中心\n  cloud:\n    nacos:\n      discovery:\n        server-addr: 127.0.0.1:8847\n# mapper映射地址\nmybatis-plus:\n  mapper-locations: classpath:/mapper/**/*.xml\n#  主键自增\n  global-config:\n    db-config:\n      id-type: auto\n\n# 日志打印级别\nlogging:\n  level:\n    cn.xf.product: debug', 'addf9c29b7f557365ab0ccb55957d350', '2022-03-24 18:33:49', '2022-03-24 10:33:49', 'nacos', '0:0:0:0:0:0:0:1', 'U', '6b8e46bc-6bbf-41de-a890-92df84a6f0a7');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (1, '1', 'eb41d029-6c65-47df-9cdb-a17752e6d6ab', 'product', '商品服务', 'nacos', 1644486592796, 1644486592796);
INSERT INTO `tenant_info` VALUES (2, '1', '889f5306-318f-4a18-aeda-522b57e408fb', 'order', '订单服务', 'nacos', 1644486608562, 1644486608562);
INSERT INTO `tenant_info` VALUES (3, '1', '6b8e46bc-6bbf-41de-a890-92df84a6f0a7', 'member', '用户服务', 'nacos', 1644486621177, 1644486621177);
INSERT INTO `tenant_info` VALUES (4, '1', '5e06c141-8ef1-447f-a1c6-e416471f5a90', 'warehousing', '库存服务', 'nacos', 1644486631039, 1644486631039);
INSERT INTO `tenant_info` VALUES (5, '1', '66aa6954-27a3-460c-9b2d-e613187af93f', 'coupon', '优惠券服务', 'nacos', 1644486640611, 1644486640611);
INSERT INTO `tenant_info` VALUES (6, '1', '3eefe1fe-bd89-41a8-898c-e972dd703bb1', 'gateway', '网关', 'nacos', 1644542861079, 1644542861079);
INSERT INTO `tenant_info` VALUES (7, '1', '933c7cdd-aae2-4d58-b9c8-ba7649b5031d', 'third-party', '三方服务', 'nacos', 1644981851243, 1644986546356);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
