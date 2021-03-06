DROP TABLE IF EXISTS `admin_office`;
CREATE TABLE `admin_office`(
	`id` varchar(64) NOT NULL COMMENT '编号',
	`name` varchar(64) NOT NULL COMMENT '机构名称',
	`parent_id` varchar(64) DEFAULT NULL COMMENT '上级编号',
	`parent_ids` varchar(2000) DEFAULT NULL COMMENT '所有父级编号',
	`sort` decimal(10,0) DEFAULT NULL COMMENT '排序',
	`create_date` datetime NOT NULL COMMENT '创建时间',
	`create_by` varchar(64) NOT NULL COMMENT '创建人',
	`update_date` datetime DEFAULT NULL COMMENT '修改时间',
	`update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
	`remarks` varchar(255) DEFAULT NULL COMMENT '备注',
	`del_flag` char(1) NOT NULL COMMENT '删除标记',
	PRIMARY KEY (`id`),
	KEY `admin_office_parent_id` (`parent_id`),
	KEY `admin_office_del_flag` (`del_flag`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构表';

DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user`(
	`id` varchar(64) NOT NULL COMMENT '编号',
	`name` varchar(100) NOT NULL COMMENT '姓名',
	`office_id` varchar(64) DEFAULT NULL COMMENT '所属机构',
	`login_name` varchar(100) NOT NULL COMMENT '登录名',
	`password` varchar(100) DEFAULT NULL COMMENT '登录密码',
	`salt` varchar(100) NOT NULL COMMENT '密码盐',
	`user_type` char(1) DEFAULT NULL COMMENT '用户类型，1：管理用户2：业务用户',
	`create_date` datetime NOT NULL COMMENT '创建时间',
	`create_by` varchar(64) NOT NULL COMMENT '创建人',
	`update_date` datetime DEFAULT NULL COMMENT '修改时间',
	`update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
	`remarks` varchar(255) DEFAULT NULL COMMENT '备注',
	`del_flag` char(1) NOT NULL COMMENT '是否删除',
	`enabled` char(1) NOT NULL COMMENT '是否可用',
	PRIMARY KEY (`id`),
	UNIQUE KEY(`login_name`),
	KEY `admin_user_office_id` (`office_id`),
	KEY `admin_user_del_flag` (`del_flag`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

DROP TABLE IF EXISTS `admin_resource`;
CREATE TABLE `admin_resource`(
	`id` varchar(64) NOT NULL COMMENT '编号',
	`name` varchar(64) NOT NULL COMMENT '资源名称',
	`parent_id` varchar(64) DEFAULT NULL COMMENT '上级编号',
	`parent_ids` varchar(2000) DEFAULT NULL COMMENT '所有父级编号',
	`sort` decimal(10,0) DEFAULT NULL COMMENT '排序',
	`create_date` datetime NOT NULL COMMENT '创建时间',
	`create_by` varchar(64) NOT NULL COMMENT '创建人',
	`update_date` datetime DEFAULT NULL COMMENT '修改时间',
	`update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
	`remarks` varchar(255) DEFAULT NULL COMMENT '备注',
	`del_flag` char(1) NOT NULL COMMENT '删除标记',
	`back_flag` char(1) NOT NULL COMMENT '是否后台应用,1:后台管理，0：业务应用',
	`url` varchar(255) NOT DEFAULT COMMENT '连接地址',
	`icon` varchar(100) DEFAULT NULL COMMENT '图标',
	PRIMARY KEY (`id`),
	KEY `admin_resource_parent_id` (`parent_id`),
	KEY `admin_resource_del_flag` (`del_flag`),
	KEY `admin_resource_back_flag` (`back_flag`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`(
	`id` varchar(64) NOT NULL COMMENT '编号',
	`name` varchar(64) NOT NULL COMMENT '角色名称',
	`sort` decimal(10,0) DEFAULT NULL COMMENT '排序',
	`create_date` datetime NOT NULL COMMENT '创建时间',
	`create_by` varchar(64) NOT NULL COMMENT '创建人',
	`update_date` datetime DEFAULT NULL COMMENT '修改时间',
	`update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
	`remarks` varchar(255) DEFAULT NULL COMMENT '备注',
	`del_flag` char(1) NOT NULL COMMENT '删除标记',
	`enabled` char(1) NOT NULL COMMENT '是否可用',
	PRIMARY KEY (`id`),
	KEY `admin_role_del_flag` (`del_flag`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

DROP TABLE IF EXISTS `admin_role_resource`;
CREATE TABLE `admin_role_resource`(
	`role_id` varchar(64) NOT NULL COMMENT '角色ID',
	`resource_id` varchar(64) NOT NULL COMMENT '资源ID',
	PRIMARY KEY (`role_id`,`resource_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-资源';

DROP TABLE IF EXISTS `admin_user_role`;
CREATE TABLE `admin_user_role`(
	`user_id` varchar(64) NOT NULL COMMENT '用户ID',
	`role_id` varchar(64) NOT NULL COMMENT '角色ID',
	PRIMARY KEY (`user_id`,`role_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色';


DROP TABLE IF EXISTS `admin_log`;
CREATE TABLE `admin_log`(
	`id` varchar(64) NOT NULL COMMENT '编号',
	`log_type` varchar(64) DEFAULT NULL COMMENT '日志类型',
	`req_method` varchar(255) DEFAULT NULL COMMENT '请求方式',
	`uri` varchar(255) DEFAULT NULL COMMENT '请求地址',
	`req_params` text DEFAULT NULL COMMENT '请求参数',
	`code` varchar(255) DEFAULT NULL COMMENT '日志状态',
	`message` varchar(255) DEFAULT NULL COMMENT '日志信息',
	`error_msg` text DEFAULT NULL COMMENT '异常信息',
	`create_ip` varchar(255) DEFAULT NULL COMMENT '操作IP',
	`create_date` datetime DEFAULT NULL COMMENT '操作时间',
	`create_by` varchar(64) DEFAULT NULL COMMENT '操作人',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志表';