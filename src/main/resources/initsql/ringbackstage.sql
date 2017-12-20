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