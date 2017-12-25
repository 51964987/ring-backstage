DROP TABLE IF EXISTS `admin_office`;
CREATE TABLE `admin_office`(
	`id` varchar(64) NOT NULL COMMENT '���',
	`name` varchar(64) NOT NULL COMMENT '��������',
	`parent_id` varchar(64) DEFAULT NULL COMMENT '�ϼ����',
	`parent_ids` varchar(2000) DEFAULT NULL COMMENT '���и������',
	`sort` decimal(10,0) DEFAULT NULL COMMENT '����',
	`create_date` datetime NOT NULL COMMENT '����ʱ��',
	`create_by` varchar(64) NOT NULL COMMENT '������',
	`update_date` datetime DEFAULT NULL COMMENT '�޸�ʱ��',
	`update_by` varchar(64) DEFAULT NULL COMMENT '�޸���',
	`remarks` varchar(255) DEFAULT NULL COMMENT '��ע',
	`del_flag` char(1) NOT NULL COMMENT '��ע',
	PRIMARY KEY (`id`),
	KEY `admin_office_parent_id` (`parent_id`),
	KEY `admin_office_del_flag` (`del_flag`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='������';

DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user`(
	`id` varchar(64) NOT NULL COMMENT '���',
	`name` varchar(100) NOT NULL COMMENT '����',
	`office_id` varchar(64) DEFAULT NULL COMMENT '��������',
	`login_name` varchar(100) NOT NULL COMMENT '��¼��',
	`password` varchar(100) DEFAULT NULL COMMENT '��¼����',
	`salt` varchar(100) DEFAULT NULL COMMENT '������',
	`user_type` char(1) DEFAULT NULL COMMENT '�û����ͣ�1�������û�2��ҵ���û�',
	`create_date` datetime NOT NULL COMMENT '����ʱ��',
	`create_by` varchar(64) NOT NULL COMMENT '������',
	`update_date` datetime DEFAULT NULL COMMENT '�޸�ʱ��',
	`update_by` varchar(64) DEFAULT NULL COMMENT '�޸���',
	`remarks` varchar(255) DEFAULT NULL COMMENT '��ע',
	`del_flag` char(1) NOT NULL COMMENT '�Ƿ�ɾ��',
	PRIMARY KEY (`id`),
	UNIQUE KEY(`login_name`),
	KEY `admin_user_office_id` (`office_id`),
	KEY `admin_user_del_flag` (`del_flag`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�û���';