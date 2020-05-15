-- ----------------------------
-- Table structure for `sys_permission`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_permission` (
  `id` char(32) NOT NULL COMMENT '唯一标识',
  `permission_code` varchar(64) NOT NULL COMMENT '权限编码',
  `permission_type` char(2) NOT NULL DEFAULT '1' COMMENT '权限类别（01:自定义, 02:路径, 03:菜单）',
  `permission_memo` varchar(128) DEFAULT NULL COMMENT '权限描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '记录创建用户',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改时间',
  `update_user` char(32) DEFAULT NULL COMMENT '记录修改用户',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标记已删除 0:否 1:是',
  primary key (`id`),
  unique key `sys_permission_uni_permission_code` (`permission_code`),
  UNIQUE KEY `sys_permission_uni_permission_type_permission_code` (`permission_type`,`permission_code`),
  )COMMENT='权限信息表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('cde9ed55e99e11e99cb80242ac12010a', 'common_add', '01', null, '2019-11-15 13:27:35', null, '2019-11-15 13:27:35', null, '0');

INSERT INTO `sys_permission` VALUES ('d3d3ebb3e99e11e99cb80242ac12010a', 'common_edit', '01', null, '2019-11-15 13:27:35', null, '2019-11-15 13:27:35', null, '0');

INSERT INTO `sys_permission` VALUES ('d9775fb9e99e11e99cb80242ac12010a', 'common_delete', '01', null, '2019-11-15 13:27:35', null, '2019-11-15 13:27:35', null, '0');

INSERT INTO `sys_permission` VALUES ('dd0d07dee99e11e99cb80242ac12010a', 'common_read', '01', null, '2019-11-15 13:27:35', null, '2019-11-15 13:27:35', null, '0');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` char(32) NOT NULL COMMENT '唯一标识',
  `role_name` varchar(64) NOT NULL COMMENT '角色名称',
  `role_memo` varchar(128) DEFAULT NULL COMMENT '角色描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '记录创建用户',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改时间',
  `update_user` char(32) DEFAULT NULL COMMENT '记录修改用户',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标记已删除 0:否 1:是'
)COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('87feedeae99911e99cb80242ac12010a', 'admin', '管理员', '2019-11-15 13:27:35', null, '2019-11-15 13:27:35', null, '0');

INSERT INTO `sys_role` VALUES ('add26716e99911e99cb80242ac12010a', 'guest', '来宾', '2019-11-15 13:27:35', null, '2019-11-15 13:27:35', null, '0');

-- ----------------------------
-- Table structure for `sys_role_permission_rel`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission_rel`;

CREATE TABLE `sys_role_permission_rel` (
  `id` char(32) NOT NULL COMMENT '唯一标识',
  `role_id` char(32) NOT NULL COMMENT '角色信息表唯一标识',
  `permission_id` char(32) NOT NULL COMMENT '权限信息表唯一标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '记录创建用户',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改时间',
  `update_user` char(32) DEFAULT NULL COMMENT '记录修改用户',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标记已删除 0:否 1:是',
  )COMMENT='角色权限关系表';

-- ----------------------------
-- Records of sys_role_permission_rel
-- ----------------------------
INSERT INTO `sys_role_permission_rel` VALUES ('860fbfcfe9a011e99cb80242ac12010a', '87feedeae99911e99cb80242ac12010a', 'd9775fb9e99e11e99cb80242ac12010a', '2019-11-15 13:27:35', null, '2019-11-15 13:27:35', null, '0');

INSERT INTO `sys_role_permission_rel` VALUES ('b9a469b1e9a011e99cb80242ac12010a', '87feedeae99911e99cb80242ac12010a', 'dd0d07dee99e11e99cb80242ac12010a', '2019-11-15 13:27:35', null, '2019-11-15 13:27:35', null, '0');

INSERT INTO `sys_role_permission_rel` VALUES ('e330e8eae99f11e99cb80242ac12010a', '87feedeae99911e99cb80242ac12010a', 'cde9ed55e99e11e99cb80242ac12010a', '2019-11-15 13:27:35', null, '2019-11-15 13:27:35', null, '0');

INSERT INTO `sys_role_permission_rel` VALUES ('eb2d9253e99f11e99cb80242ac12010a', '87feedeae99911e99cb80242ac12010a', 'd3d3ebb3e99e11e99cb80242ac12010a', '2019-11-15 13:27:35', null, '2019-11-15 13:27:35', null, '0');

INSERT INTO `sys_role_permission_rel` VALUES ('f697321be9a011e99cb80242ac12010a', 'add26716e99911e99cb80242ac12010a', 'dd0d07dee99e11e99cb80242ac12010a', '2019-11-15 13:27:35', null, '2019-11-15 13:27:35', null, '0');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` char(32) NOT NULL COMMENT '唯一标识',
  `user_name` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名称',
  `user_pwd` varchar(64) NOT NULL DEFAULT '' COMMENT '用户密码',
  `nick_name` varchar(64) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `sex` int(1) NOT NULL DEFAULT '0' COMMENT '性别 0:女 1:男',
  `email` varchar(64) NOT NULL DEFAULT '' COMMENT '邮箱',
  `mobile` varchar(24) NOT NULL DEFAULT '' COMMENT '移动电话',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '记录创建用户',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改时间',
  `update_user` char(32) DEFAULT NULL COMMENT '记录修改用户',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标记已删除 0:否 1:是'
)COMMENT='用户基础表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('0a19ba58e8ab11e9b3700242ac12010a', 'guest', '123456', '', '0', 'a@a.com', '13112345678', '2019-11-15 13:27:35', null, '2019-11-15 13:27:35', null, '0');

INSERT INTO `sys_user` VALUES ('89232296e8a911e9b3700242ac12010a', 'admin', 'admin', '', '0', 'b@b.com', '13212345678', '2019-11-15 13:27:35', null, '2019-11-18 13:51:29', null, '0');

-- ----------------------------
-- Table structure for `sys_user_role_rel`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_rel`;

CREATE TABLE `sys_user_role_rel` (
  `id` char(32) NOT NULL COMMENT '唯一标识',
  `user_id` char(32) NOT NULL COMMENT '用户信息表唯一标识',
  `role_id` char(32) NOT NULL COMMENT '角色信息表唯一标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '记录创建用户',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改时间',
  `update_user` char(32) DEFAULT NULL COMMENT '记录修改用户',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标记已删除 0:否 1:是',
  )COMMENT='用户角色关系表';

-- ----------------------------
-- Records of sys_user_role_rel
-- ----------------------------
INSERT INTO `sys_user_role_rel` VALUES ('e062d8aee99e11e99cb80242ac12010a', '89232296e8a911e9b3700242ac12010a', '87feedeae99911e99cb80242ac12010a', '2019-11-15 13:27:35', null, '2019-11-15 13:27:35', null, '0');

INSERT INTO `sys_user_role_rel` VALUES ('e70b8a96e99e11e99cb80242ac12010a', '0a19ba58e8ab11e9b3700242ac12010a', 'add26716e99911e99cb80242ac12010a', '2019-11-15 13:27:35', null, '2019-11-15 13:27:35', null, '0');



-- ALTER TABLE sys_permission ADD PRIMARY KEY (`id`);
-- ALTER TABLE sys_permission ADD UNIQUE KEY `sys_permission_uni_permission_code` (`permission_code`);
-- ALTER TABLE sys_permission ADD UNIQUE KEY `sys_permission_uni_permission_type_permission_code` (`permission_type`,`permission_code`);
ALTER TABLE sys_role ADD PRIMARY KEY (`id`);
ALTER TABLE sys_role ADD UNIQUE KEY `sys_role_uni_role_name` (`role_name`);
ALTER TABLE sys_role_permission_rel ADD PRIMARY KEY (`id`);
ALTER TABLE sys_role_permission_rel ADD UNIQUE KEY `sys_role_permission_rel_uni_role_id_permission_id` (`role_id`,`permission_id`);
ALTER TABLE sys_user ADD PRIMARY KEY (`id`);
ALTER TABLE sys_user ADD UNIQUE KEY `sys_user_uni_user_name` (`user_name`);
ALTER TABLE sys_user_role_rel ADD PRIMARY KEY (`id`);
ALTER TABLE sys_user_role_rel ADD UNIQUE KEY `sys_user_role_rel_uni_user_id_role_id` (`user_id`,`role_id`);