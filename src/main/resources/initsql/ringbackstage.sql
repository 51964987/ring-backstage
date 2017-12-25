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
	`del_flag` char(1) NOT NULL COMMENT '备注',
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
	`salt` varchar(100) DEFAULT NULL COMMENT '密码盐',
	`user_type` char(1) DEFAULT NULL COMMENT '用户类型，1：管理用户2：业务用户',
	`create_date` datetime NOT NULL COMMENT '创建时间',
	`create_by` varchar(64) NOT NULL COMMENT '创建人',
	`update_date` datetime DEFAULT NULL COMMENT '修改时间',
	`update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
	`remarks` varchar(255) DEFAULT NULL COMMENT '备注',
	`del_flag` char(1) NOT NULL COMMENT '是否删除',
	PRIMARY KEY (`id`),
	UNIQUE KEY(`login_name`),
	KEY `admin_user_office_id` (`office_id`),
	KEY `admin_user_del_flag` (`del_flag`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';