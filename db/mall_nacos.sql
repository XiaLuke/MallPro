/*
 Navicat Premium Data Transfer

 Source Server         : docker_mysql
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : 192.168.56.10:3306
 Source Schema         : mall_nacos

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 13/02/2022 17:45:34
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
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (3, 'application.yml', 'dev', '# 端口\r\nserver:\r\n  port: 6000\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    url: jdbc:mysql://192.168.56.10:3306/mall_coupon\r\n    username: root\r\n    password: 987654\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n\r\n# mapper.xml扫描\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/*.xml\r\n  # 主键自增\r\n  global-config:\r\n    db-config:\r\n      id-type: auto\r\n', '599cd07da7bc4fd86003e18aa9a927bd', '2022-02-10 09:53:45', '2022-02-10 09:53:45', 'nacos', '127.0.0.1', '', '66aa6954-27a3-460c-9b2d-e613187af93f', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (4, 'application.yml', 'dev', '# 端口\r\nserver:\r\n  port: 7000\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    url: jdbc:mysql://192.168.56.10:3306/mall_member\r\n    username: root\r\n    password: 987654\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: 127.0.0.1:8848\r\n\r\n# mapper.xml扫描\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/*.xml\r\n  # 主键自增\r\n  global-config:\r\n    db-config:\r\n      id-type: auto', 'e86adcf59e58249f4e4ce40052eba23f', '2022-02-10 09:54:11', '2022-02-10 09:54:11', 'nacos', '127.0.0.1', '', '6b8e46bc-6bbf-41de-a890-92df84a6f0a7', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (5, 'application.yml', 'dev', '# 端口\r\nserver:\r\n  port: 8000\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    url: jdbc:mysql://192.168.56.10:3306/mall_order\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    username: root\r\n    password: 987654\r\n# mapper.xml扫描\r\nmybatis-plus:\r\n  mapper-locations: classpath:mapper/**/.*xml\r\n  global-config:\r\n    db-config:\r\n      id-type: auto', '1aebfd439442e68b774358d4aba9e58a', '2022-02-10 09:54:39', '2022-02-10 09:54:39', 'nacos', '127.0.0.1', '', '889f5306-318f-4a18-aeda-522b57e408fb', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (6, 'application.yml', 'dev', '# 端口\nserver:\n  port: 9000\n\n# 数据源\nspring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.56.10:3306/mall_product\n    username: root\n    password: 987654\n  application:\n    name: product\n  # 注册中心\n  cloud:\n    nacos:\n      discovery:\n        server-addr: 127.0.0.1:8848\n# mapper映射地址\nmybatis-plus:\n  mapper-locations: classpath:/mapper/**/*.xml\n#  主键自增\n  global-config:\n    db-config:\n      id-type: auto\n\n# 日志打印级别\nlogging:\n  level:\n    cn.xf.product: debug', '1dd10c62eb0b83d0e380f7b214d0a39d', '2022-02-10 09:55:00', '2022-02-11 06:07:18', 'nacos', '0:0:0:0:0:0:0:1', '', 'eb41d029-6c65-47df-9cdb-a17752e6d6ab', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (7, 'application.yml', 'dev', '# 端口\r\nserver:\r\n  port: 10000\r\n\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://192.168.56.10:3306/mall_warehousing\r\n    username: root\r\n    password: 987654\r\n\r\n# mapper映射地址\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/**/*.xml\r\n#  主键自增\r\n  global-config:\r\n    db-config:\r\n      id-type: auto', '0135827bae4f8d46ef85947956474344', '2022-02-10 09:55:21', '2022-02-10 09:55:21', 'nacos', '127.0.0.1', '', '5e06c141-8ef1-447f-a1c6-e416471f5a90', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (10, 'application.yml', 'dev', 'server:\n    port: 88\nspring:\n    application:\n        name: gateway\n    cloud:\n        nacos:\n            discovery:\n                server-addr: 127.0.0.1:8848\n        gateway:\n            routes:\n                - id: product_route\n                  uri: lb://product\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/product/**\n                  filters:\n                    # 将api下的所有请求，将api去掉剩下的为请求路径\n                    - RewritePath=/api/(?<segment>.*),/$\\{segment}\n\n                - id: admin_route\n                  uri: lb://renren-fast\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/**\n                  filters:\n                    - RewritePath=/api/(?<segment>.*),/renren-fast/$\\{segment}\n', 'dbe1e3d8707bafbecddaaaa6f88117f0', '2022-02-11 02:19:27', '2022-02-11 03:41:58', 'nacos', '0:0:0:0:0:0:0:1', '', '3eefe1fe-bd89-41a8-898c-e972dd703bb1', '', '', '', 'yaml', '');

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
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (0, 1, 'application', 'dev', '', '# 端口\r\nserver:\r\n  port: 7000\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    url: jdbc:mysql://192.168.56.10:3306/mall_member\r\n    username: root\r\n    password: 987654\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: 127.0.0.1:8848\r\n\r\n# mapper.xml扫描\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/*.xml\r\n  # 主键自增\r\n  global-config:\r\n    db-config:\r\n      id-type: auto', 'e86adcf59e58249f4e4ce40052eba23f', '2022-02-10 09:52:01', '2022-02-10 09:52:02', 'nacos', '127.0.0.1', 'I', '6b8e46bc-6bbf-41de-a890-92df84a6f0a7');
INSERT INTO `his_config_info` VALUES (1, 2, 'application', 'dev', '', '# 端口\r\nserver:\r\n  port: 7000\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    url: jdbc:mysql://192.168.56.10:3306/mall_member\r\n    username: root\r\n    password: 987654\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: 127.0.0.1:8848\r\n\r\n# mapper.xml扫描\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/*.xml\r\n  # 主键自增\r\n  global-config:\r\n    db-config:\r\n      id-type: auto', 'e86adcf59e58249f4e4ce40052eba23f', '2022-02-10 09:52:10', '2022-02-10 09:52:11', 'nacos', '127.0.0.1', 'D', '6b8e46bc-6bbf-41de-a890-92df84a6f0a7');
INSERT INTO `his_config_info` VALUES (0, 3, 'application.yml', 'DEFAULT_GROUP', '', '# 端口\r\nserver:\r\n  port: 6000\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    url: jdbc:mysql://192.168.56.10:3306/mall_coupon\r\n    username: root\r\n    password: 987654\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n\r\n# mapper.xml扫描\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/*.xml\r\n  # 主键自增\r\n  global-config:\r\n    db-config:\r\n      id-type: auto\r\n', '599cd07da7bc4fd86003e18aa9a927bd', '2022-02-10 09:52:53', '2022-02-10 09:52:55', 'nacos', '127.0.0.1', 'I', '6b8e46bc-6bbf-41de-a890-92df84a6f0a7');
INSERT INTO `his_config_info` VALUES (2, 4, 'application.yml', 'DEFAULT_GROUP', '', '# 端口\r\nserver:\r\n  port: 6000\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    url: jdbc:mysql://192.168.56.10:3306/mall_coupon\r\n    username: root\r\n    password: 987654\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n\r\n# mapper.xml扫描\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/*.xml\r\n  # 主键自增\r\n  global-config:\r\n    db-config:\r\n      id-type: auto\r\n', '599cd07da7bc4fd86003e18aa9a927bd', '2022-02-10 09:53:29', '2022-02-10 09:53:30', 'nacos', '127.0.0.1', 'D', '6b8e46bc-6bbf-41de-a890-92df84a6f0a7');
INSERT INTO `his_config_info` VALUES (0, 5, 'application', 'dev', '', '# 端口\r\nserver:\r\n  port: 6000\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    url: jdbc:mysql://192.168.56.10:3306/mall_coupon\r\n    username: root\r\n    password: 987654\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n\r\n# mapper.xml扫描\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/*.xml\r\n  # 主键自增\r\n  global-config:\r\n    db-config:\r\n      id-type: auto\r\n', '599cd07da7bc4fd86003e18aa9a927bd', '2022-02-10 09:53:44', '2022-02-10 09:53:45', 'nacos', '127.0.0.1', 'I', '66aa6954-27a3-460c-9b2d-e613187af93f');
INSERT INTO `his_config_info` VALUES (0, 6, 'application.yml', 'dev', '', '# 端口\r\nserver:\r\n  port: 7000\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    url: jdbc:mysql://192.168.56.10:3306/mall_member\r\n    username: root\r\n    password: 987654\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: 127.0.0.1:8848\r\n\r\n# mapper.xml扫描\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/*.xml\r\n  # 主键自增\r\n  global-config:\r\n    db-config:\r\n      id-type: auto', 'e86adcf59e58249f4e4ce40052eba23f', '2022-02-10 09:54:10', '2022-02-10 09:54:11', 'nacos', '127.0.0.1', 'I', '6b8e46bc-6bbf-41de-a890-92df84a6f0a7');
INSERT INTO `his_config_info` VALUES (0, 7, 'application.yml', 'dev', '', '# 端口\r\nserver:\r\n  port: 8000\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    url: jdbc:mysql://192.168.56.10:3306/mall_order\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    username: root\r\n    password: 987654\r\n# mapper.xml扫描\r\nmybatis-plus:\r\n  mapper-locations: classpath:mapper/**/.*xml\r\n  global-config:\r\n    db-config:\r\n      id-type: auto', '1aebfd439442e68b774358d4aba9e58a', '2022-02-10 09:54:38', '2022-02-10 09:54:39', 'nacos', '127.0.0.1', 'I', '889f5306-318f-4a18-aeda-522b57e408fb');
INSERT INTO `his_config_info` VALUES (0, 8, 'application.yml', 'dev', '', '# 端口\r\nserver:\r\n  port: 9000\r\n\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://192.168.56.10:3306/mall_product\r\n    username: root\r\n    password: 987654\r\n\r\n# mapper映射地址\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/**/*.xml\r\n#  主键自增\r\n  global-config:\r\n    db-config:\r\n      id-type: auto', '71abb89da4a4ac484ad3a3a345ca21dd', '2022-02-10 09:54:59', '2022-02-10 09:55:00', 'nacos', '127.0.0.1', 'I', 'eb41d029-6c65-47df-9cdb-a17752e6d6ab');
INSERT INTO `his_config_info` VALUES (0, 9, 'application.yml', 'dev', '', '# 端口\r\nserver:\r\n  port: 10000\r\n\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://192.168.56.10:3306/mall_warehousing\r\n    username: root\r\n    password: 987654\r\n\r\n# mapper映射地址\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/**/*.xml\r\n#  主键自增\r\n  global-config:\r\n    db-config:\r\n      id-type: auto', '0135827bae4f8d46ef85947956474344', '2022-02-10 09:55:19', '2022-02-10 09:55:21', 'nacos', '127.0.0.1', 'I', '5e06c141-8ef1-447f-a1c6-e416471f5a90');
INSERT INTO `his_config_info` VALUES (0, 10, 'application', 'dev', '', 'spring:\r\n    application:\r\n        name: gateway\r\n# 路由\r\n    cloud:\r\n        nacos:\r\n            discovery:\r\n                server-addr: 127.0.0.1:8848\r\n        gateway:\r\n            routes:\r\n                - id: admin_route\r\n                  uri: lb://renren-fast\r\n                  # 设置断言，什么路径触发\r\n                  predicates:\r\n                   - Path=/api/**', '58b11aa1e7d65b3a17a26e46921fa962', '2022-02-11 02:15:46', '2022-02-11 02:15:46', 'nacos', '127.0.0.1', 'I', '3eefe1fe-bd89-41a8-898c-e972dd703bb1');
INSERT INTO `his_config_info` VALUES (0, 11, 'application.yml', 'dev', '', 'spring:\r\n    application:\r\n        name: gateway\r\n# 路由\r\n    cloud:\r\n        nacos:\r\n            discovery:\r\n                server-addr: 127.0.0.1:8848\r\n        gateway:\r\n            routes:\r\n                - id: admin_route\r\n                  uri: lb://renren-fast\r\n                  # 设置断言，什么路径触发\r\n                  predicates:\r\n                   - Path=/api/**', '58b11aa1e7d65b3a17a26e46921fa962', '2022-02-11 02:15:53', '2022-02-11 02:15:54', 'nacos', '127.0.0.1', 'I', '3eefe1fe-bd89-41a8-898c-e972dd703bb1');
INSERT INTO `his_config_info` VALUES (8, 12, 'application', 'dev', '', 'spring:\r\n    application:\r\n        name: gateway\r\n# 路由\r\n    cloud:\r\n        nacos:\r\n            discovery:\r\n                server-addr: 127.0.0.1:8848\r\n        gateway:\r\n            routes:\r\n                - id: admin_route\r\n                  uri: lb://renren-fast\r\n                  # 设置断言，什么路径触发\r\n                  predicates:\r\n                   - Path=/api/**', '58b11aa1e7d65b3a17a26e46921fa962', '2022-02-11 02:15:58', '2022-02-11 02:15:59', 'nacos', '127.0.0.1', 'D', '3eefe1fe-bd89-41a8-898c-e972dd703bb1');
INSERT INTO `his_config_info` VALUES (9, 13, 'application.yml', 'dev', '', 'spring:\r\n    application:\r\n        name: gateway\r\n# 路由\r\n    cloud:\r\n        nacos:\r\n            discovery:\r\n                server-addr: 127.0.0.1:8848\r\n        gateway:\r\n            routes:\r\n                - id: admin_route\r\n                  uri: lb://renren-fast\r\n                  # 设置断言，什么路径触发\r\n                  predicates:\r\n                   - Path=/api/**', '58b11aa1e7d65b3a17a26e46921fa962', '2022-02-11 02:16:33', '2022-02-11 02:16:34', 'nacos', '127.0.0.1', 'D', '3eefe1fe-bd89-41a8-898c-e972dd703bb1');
INSERT INTO `his_config_info` VALUES (0, 14, 'application.yml', 'dev', '', 'server:\r\n    port: 88\r\nspring:\r\n    application:\r\n        name: gateway\r\n    cloud:\r\n        nacos:\r\n            discovery:\r\n                server-addr: 127.0.0.1:8848\r\n        gateway:\r\n            routes:\r\n                - id: admin_route\r\n                  uri: lb://renren-fast\r\n                  #配置断言，什么路径下触发\r\n                  predicates:\r\n                    - Path=/api/**\r\n', 'b7191a7a6284b71a742e74de564206ad', '2022-02-11 02:19:26', '2022-02-11 02:19:27', 'nacos', '127.0.0.1', 'I', '3eefe1fe-bd89-41a8-898c-e972dd703bb1');
INSERT INTO `his_config_info` VALUES (10, 15, 'application.yml', 'dev', '', 'server:\r\n    port: 88\r\nspring:\r\n    application:\r\n        name: gateway\r\n    cloud:\r\n        nacos:\r\n            discovery:\r\n                server-addr: 127.0.0.1:8848\r\n        gateway:\r\n            routes:\r\n                - id: admin_route\r\n                  uri: lb://renren-fast\r\n                  #配置断言，什么路径下触发\r\n                  predicates:\r\n                    - Path=/api/**\r\n', 'b7191a7a6284b71a742e74de564206ad', '2022-02-11 02:25:15', '2022-02-11 02:25:15', 'nacos', '127.0.0.1', 'U', '3eefe1fe-bd89-41a8-898c-e972dd703bb1');
INSERT INTO `his_config_info` VALUES (10, 16, 'application.yml', 'dev', '', 'server:\n    port: 88\nspring:\n    application:\n        name: gateway\n    cloud:\n        nacos:\n            discovery:\n                server-addr: 127.0.0.1:8848\n        gateway:\n            routes:\n                - id: admin_route\n                  uri: lb://renren-fast\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/**\n                  filters:\n                    - RewritePath=/api/(?<segment>.*),/renren-fase/$\\{segment}\n', 'd32403962275849a9c379b5111956aae', '2022-02-11 02:27:14', '2022-02-11 02:27:15', 'nacos', '127.0.0.1', 'U', '3eefe1fe-bd89-41a8-898c-e972dd703bb1');
INSERT INTO `his_config_info` VALUES (10, 17, 'application.yml', 'dev', '', 'server:\n    port: 88\nspring:\n    application:\n        name: gateway\n    cloud:\n        nacos:\n            discovery:\n                server-addr: 127.0.0.1:8848\n        gateway:\n            routes:\n                - id: admin_route\n                  uri: lb://renren-fast\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/**\n                  filters:\n                    - RewritePath=/api/(?<segment>.*),/renren-fast/$\\{segment}\n', '968b9f624c04c1d9816bf655dfbad8ad', '2022-02-11 03:28:58', '2022-02-11 03:28:59', 'nacos', '0:0:0:0:0:0:0:1', 'U', '3eefe1fe-bd89-41a8-898c-e972dd703bb1');
INSERT INTO `his_config_info` VALUES (6, 18, 'application.yml', 'dev', '', '# 端口\r\nserver:\r\n  port: 9000\r\n\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://192.168.56.10:3306/mall_product\r\n    username: root\r\n    password: 987654\r\n\r\n# mapper映射地址\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/**/*.xml\r\n#  主键自增\r\n  global-config:\r\n    db-config:\r\n      id-type: auto', '71abb89da4a4ac484ad3a3a345ca21dd', '2022-02-11 03:34:46', '2022-02-11 03:34:46', 'nacos', '0:0:0:0:0:0:0:1', 'U', 'eb41d029-6c65-47df-9cdb-a17752e6d6ab');
INSERT INTO `his_config_info` VALUES (10, 19, 'application.yml', 'dev', '', 'server:\n    port: 88\nspring:\n    application:\n        name: gateway\n    cloud:\n        nacos:\n            discovery:\n                server-addr: 127.0.0.1:8848\n        gateway:\n            routes:\n                - id: admin_route\n                  uri: lb://renren-fast\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/**\n                  filters:\n                    - RewritePath=/api/(?<segment>.*),/renren-fast/$\\{segment}\n\n                - id: product_route\n                  uri: lb://product\n                  #配置断言，什么路径下触发\n                  predicates:\n                    - Path=/api/product/**\n                  filters:\n                    # 将api下的所有请求，将api去掉剩下的为请求路径\n                    - RewritePath=/api/(?<segment>.*),/$\\{segment}', 'f52923eff3740a131b731188593edee5', '2022-02-11 03:41:57', '2022-02-11 03:41:58', 'nacos', '0:0:0:0:0:0:0:1', 'U', '3eefe1fe-bd89-41a8-898c-e972dd703bb1');
INSERT INTO `his_config_info` VALUES (6, 20, 'application.yml', 'dev', '', '# 端口\nserver:\n  port: 9000\n\n# 数据源\nspring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.56.10:3306/mall_product\n    username: root\n    password: 987654\n  application:\n    name: product\n  # 注册中心\n  cloud:\n    nacos:\n      discovery:\n        server-addr: 127.0.0.1:8848\n# mapper映射地址\nmybatis-plus:\n  mapper-locations: classpath:/mapper/**/*.xml\n#  主键自增\n  global-config:\n    db-config:\n      id-type: auto', '6ad54465617eec7ac0c28ac537c98b63', '2022-02-11 04:59:51', '2022-02-11 04:59:52', 'nacos', '0:0:0:0:0:0:0:1', 'U', 'eb41d029-6c65-47df-9cdb-a17752e6d6ab');
INSERT INTO `his_config_info` VALUES (6, 21, 'application.yml', 'dev', '', '# 端口\nserver:\n  port: 9000\n\n# 数据源\nspring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.56.10:3306/mall_product\n    username: root\n    password: 987654\n  application:\n    name: product\n  # 注册中心\n  cloud:\n    nacos:\n      discovery:\n        server-addr: 127.0.0.1:8848\n# mapper映射地址\nmybatis-plus:\n  mapper-locations: classpath:/mapper/**/*.xml\n#  主键自增\n  global-config:\n    db-config:\n      id-type: auto\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\n\n# 日志打印级别\nlogging:\n  levlel:\n    cn.xf.product', '9771372fa2ddfdcff596aff15fd3d8b2', '2022-02-11 05:23:42', '2022-02-11 05:23:43', 'nacos', '0:0:0:0:0:0:0:1', 'U', 'eb41d029-6c65-47df-9cdb-a17752e6d6ab');
INSERT INTO `his_config_info` VALUES (6, 22, 'application.yml', 'dev', '', '# 端口\nserver:\n  port: 9000\n\n# 数据源\nspring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.56.10:3306/mall_product\n    username: root\n    password: 987654\n  application:\n    name: product\n  # 注册中心\n  cloud:\n    nacos:\n      discovery:\n        server-addr: 127.0.0.1:8848\n# mapper映射地址\nmybatis-plus:\n  mapper-locations: classpath:/mapper/**/*.xml\n#  主键自增\n  global-config:\n    db-config:\n      id-type: auto\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\n\n# 日志打印级别\nlogging:\n  levlel:\n    cn.xf.product: debug', '52be1b14bb87259c4649f6f3061b9a32', '2022-02-11 05:32:21', '2022-02-11 05:32:22', 'nacos', '0:0:0:0:0:0:0:1', 'U', 'eb41d029-6c65-47df-9cdb-a17752e6d6ab');
INSERT INTO `his_config_info` VALUES (6, 23, 'application.yml', 'dev', '', '# 端口\nserver:\n  port: 9000\n\n# 数据源\nspring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.56.10:3306/mall_product\n    username: root\n    password: 987654\n  application:\n    name: product\n  # 注册中心\n  cloud:\n    nacos:\n      discovery:\n        server-addr: 127.0.0.1:8848\n# mapper映射地址\nmybatis-plus:\n  mapper-locations: classpath:/mapper/**/*.xml\n#  主键自增\n  global-config:\n    db-config:\n      id-type: auto\n\n# 日志打印级别\nlogging:\n  levlel:\n    cn.xf.product: debug', '7cac7dbadaca3a1d90facb06fe8cf00e', '2022-02-11 06:07:18', '2022-02-11 06:07:18', 'nacos', '0:0:0:0:0:0:0:1', 'U', 'eb41d029-6c65-47df-9cdb-a17752e6d6ab');

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (1, '1', 'eb41d029-6c65-47df-9cdb-a17752e6d6ab', 'product', '商品服务', 'nacos', 1644486592796, 1644486592796);
INSERT INTO `tenant_info` VALUES (2, '1', '889f5306-318f-4a18-aeda-522b57e408fb', 'order', '订单服务', 'nacos', 1644486608562, 1644486608562);
INSERT INTO `tenant_info` VALUES (3, '1', '6b8e46bc-6bbf-41de-a890-92df84a6f0a7', 'member', '用户服务', 'nacos', 1644486621177, 1644486621177);
INSERT INTO `tenant_info` VALUES (4, '1', '5e06c141-8ef1-447f-a1c6-e416471f5a90', 'warehousing', '库存服务', 'nacos', 1644486631039, 1644486631039);
INSERT INTO `tenant_info` VALUES (5, '1', '66aa6954-27a3-460c-9b2d-e613187af93f', 'coupon', '优惠券服务', 'nacos', 1644486640611, 1644486640611);
INSERT INTO `tenant_info` VALUES (6, '1', '3eefe1fe-bd89-41a8-898c-e972dd703bb1', 'gateway', '网关', 'nacos', 1644542861079, 1644542861079);

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
