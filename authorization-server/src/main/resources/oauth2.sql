/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : oauth2

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 21/02/2020 10:30:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
# DROP TABLE IF EXISTS `authority`;
CREATE TABLE IF NOT EXISTS `authority`  (
  `id` bigint(11) NOT NULL COMMENT '权限id',
  `authority` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT IGNORE INTO `authority` VALUES (1, 'ROLE_OAUTH_ADMIN');
INSERT IGNORE INTO `authority` VALUES (2, 'ROLE_RESOURCE_ADMIN');
INSERT IGNORE INTO `authority` VALUES (3, 'ROLE_PROJECT_ADMIN');

-- ----------------------------
-- Table structure for credentials
-- ----------------------------
# DROP TABLE IF EXISTS `credentials`;
CREATE TABLE IF NOT EXISTS `credentials`  (
  `id` bigint(11) NOT NULL COMMENT '凭证id',
  `enabled` tinyint(1) NOT NULL COMMENT '是否可用',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `version` int(11) NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of credentials
-- ----------------------------
INSERT IGNORE INTO `credentials` VALUES (1, 1, 'oauth_admin', '$2a$10$aNEhVHfILyIw2hXV.3pdSejsNrJu7k4OgevdlJykK2KYVR9.G4uru', 0);
INSERT IGNORE INTO `credentials` VALUES (2, 1, 'resource_admin', '$2a$10$aNEhVHfILyIw2hXV.3pdSejsNrJu7k4OgevdlJykK2KYVR9.G4uru', 0);
INSERT IGNORE INTO `credentials` VALUES (3, 1, 'project_admin', '$2a$10$aNEhVHfILyIw2hXV.3pdSejsNrJu7k4OgevdlJykK2KYVR9.G4uru', 0);

-- ----------------------------
-- Table structure for credentials_authorities
-- ----------------------------
# DROP TABLE IF EXISTS `credentials_authorities`;
CREATE TABLE IF NOT EXISTS `credentials_authorities`  (
  `credentials_id` bigint(20) NOT NULL COMMENT '凭证id',
  `authorities_id` bigint(20) NOT NULL COMMENT '权限id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of credentials_authorities
-- ----------------------------
INSERT IGNORE INTO `credentials_authorities` VALUES (1, 1);
INSERT IGNORE INTO `credentials_authorities` VALUES (2, 2);
INSERT IGNORE INTO `credentials_authorities` VALUES (3, 3);

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
# DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE IF NOT EXISTS `oauth_access_token`  (
  `token_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密的access_token的值',
  `token` longblob NULL COMMENT 'OAuth2AccessToken.java对象序列化后的二进制数据',
  `authentication_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密过的username,client_id,scope',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录的用户名',
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端ID',
  `authentication` longblob NULL COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据',
  `refresh_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密的refresh_token的值'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
# DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE IF NOT EXISTS `oauth_approvals`  (
  `userId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录的用户名',
  `clientId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端ID',
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请的权限范围',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态（Approve或Deny）',
  `expiresAt` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `lastModifiedAt` datetime(0) NULL DEFAULT NULL COMMENT '最终修改时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
# DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE IF NOT EXISTS `oauth_client_details`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端ID',
  `resource_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源ID集合,多个资源时用逗号(,)分隔',
  `client_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端密匙',
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端申请的权限范围',
  `authorized_grant_types` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端支持的grant_type',
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '重定向URI',
  `authorities` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔',
  `access_token_validity` int(11) NULL DEFAULT NULL COMMENT '访问令牌有效时间值(单位:秒)',
  `refresh_token_validity` int(11) NULL DEFAULT NULL COMMENT '更新令牌有效时间值(单位:秒)',
  `additional_information` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段',
  `autoapprove` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户是否自动Approval操作',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT IGNORE INTO `oauth_client_details` VALUES (1, 'user_client', 'project_api', '$2a$10$aNEhVHfILyIw2hXV.3pdSejsNrJu7k4OgevdlJykK2KYVR9.G4uru', 'read,write', 'password,refresh_token', 'http://127.0.0.1', 'ROLE_PROJECT_ADMIN', 7200, 1800, NULL , NULL);

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
# DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE IF NOT EXISTS `oauth_client_token`  (
  `token_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密的access_token值',
  `token` longblob NULL COMMENT 'OAuth2AccessToken.java对象序列化后的二进制数据',
  `authentication_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密过的username,client_id,scope',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录的用户名',
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
# DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE IF NOT EXISTS  `oauth_code`  (
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权码(未加密)',
  `authentication` varbinary(255) NULL DEFAULT NULL COMMENT 'AuthorizationRequestHolder.java对象序列化后的二进制数据'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
# DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE IF NOT EXISTS `oauth_refresh_token`  (
  `token_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密过的refresh_token的值',
  `token` longblob NULL COMMENT 'OAuth2RefreshToken.java对象序列化后的二进制数据 ',
  `authentication` longblob NULL COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
