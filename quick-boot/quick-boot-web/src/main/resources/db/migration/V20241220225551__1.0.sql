
CREATE TABLE if not exists  `QRTZ_JOB_DETAILS` (
                                                   `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `job_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
    `job_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
    `description` varchar(250) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '相关介绍',
    `job_class_name` varchar(250) COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行任务类名称',
    `is_durable` varchar(1) COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否持久化',
    `is_nonconcurrent` varchar(1) COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否并发',
    `is_update_data` varchar(1) COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否更新数据',
    `requests_recovery` varchar(1) COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否接受恢复执行',
    `job_data` blob COMMENT '存放持久化job对象',
    PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='任务详细信息表';

CREATE TABLE if not exists `QRTZ_TRIGGERS` (
                                               `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `trigger_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的名字',
    `trigger_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组的名字',
    `job_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
    `job_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
    `description` varchar(250) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '相关介绍',
    `next_fire_time` bigint DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
    `prev_fire_time` bigint DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
    `priority` int DEFAULT NULL COMMENT '优先级',
    `trigger_state` varchar(16) COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器状态',
    `trigger_type` varchar(8) COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的类型',
    `start_time` bigint NOT NULL COMMENT '开始时间',
    `end_time` bigint DEFAULT NULL COMMENT '结束时间',
    `calendar_name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日程表名称',
    `misfire_instr` smallint DEFAULT NULL COMMENT '补偿执行的策略',
    `job_data` blob COMMENT '存放持久化job对象',
    PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
    KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
    CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `QRTZ_JOB_DETAILS` (`sched_name`, `job_name`, `job_group`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='触发器详细信息表';



CREATE TABLE if not exists  `QRTZ_BLOB_TRIGGERS` (
                                                     `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `trigger_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
    `trigger_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
    `blob_data` blob COMMENT '存放持久化Trigger对象',
    PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
    CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Blob类型的触发器表';

CREATE TABLE  if not exists  `QRTZ_CALENDARS` (
                                                  `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `calendar_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '日历名称',
    `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
    PRIMARY KEY (`sched_name`,`calendar_name`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='日历信息表';

CREATE TABLE  if not exists  `QRTZ_CRON_TRIGGERS` (
                                                      `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `trigger_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
    `trigger_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
    `cron_expression` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron表达式',
    `time_zone_id` varchar(80) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '时区',
    PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
    CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Cron类型的触发器表';

CREATE TABLE if not exists  `QRTZ_FIRED_TRIGGERS` (
                                                      `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `entry_id` varchar(95) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例id',
    `trigger_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
    `trigger_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
    `instance_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例名',
    `fired_time` bigint NOT NULL COMMENT '触发的时间',
    `sched_time` bigint NOT NULL COMMENT '定时器制定的时间',
    `priority` int NOT NULL COMMENT '优先级',
    `state` varchar(16) COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
    `job_name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务名称',
    `job_group` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务组名',
    `is_nonconcurrent` varchar(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否并发',
    `requests_recovery` varchar(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否接受恢复执行',
    PRIMARY KEY (`sched_name`,`entry_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='已触发的触发器表';



CREATE TABLE if not exists `QRTZ_LOCKS` (
                                            `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `lock_name` varchar(40) COLLATE utf8mb4_general_ci NOT NULL COMMENT '悲观锁名称',
    PRIMARY KEY (`sched_name`,`lock_name`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='存储的悲观锁信息表';

CREATE TABLE if not exists `QRTZ_PAUSED_TRIGGER_GRPS` (
                                                          `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `trigger_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
    PRIMARY KEY (`sched_name`,`trigger_group`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='暂停的触发器表';

CREATE TABLE if not exists `QRTZ_SCHEDULER_STATE` (
                                                      `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `instance_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '实例名称',
    `last_checkin_time` bigint NOT NULL COMMENT '上次检查时间',
    `checkin_interval` bigint NOT NULL COMMENT '检查间隔时间',
    PRIMARY KEY (`sched_name`,`instance_name`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='调度器状态表';


CREATE TABLE if not exists `QRTZ_SIMPLE_TRIGGERS` (
                                                      `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `trigger_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
    `trigger_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
    `repeat_count` bigint NOT NULL COMMENT '重复的次数统计',
    `repeat_interval` bigint NOT NULL COMMENT '重复的间隔时间',
    `times_triggered` bigint NOT NULL COMMENT '已经触发的次数',
    PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
    CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='简单触发器的信息表';


CREATE TABLE if not exists `QRTZ_SIMPROP_TRIGGERS` (
                                                       `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `trigger_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
    `trigger_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
    `str_prop_1` varchar(512) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
    `str_prop_2` varchar(512) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
    `str_prop_3` varchar(512) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
    `int_prop_1` int DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
    `int_prop_2` int DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
    `long_prop_1` bigint DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
    `long_prop_2` bigint DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
    `dec_prop_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
    `dec_prop_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
    `bool_prop_1` varchar(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
    `bool_prop_2` varchar(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
    PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
    CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='同步机制的行锁表';




CREATE TABLE if not exists `gen_table` (
                                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                           `table_name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '表名称',
    `table_comment` varchar(500) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '表描述',
    `class_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '实体类名称',
    `package_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成包路径',
    `module_name` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成模块名',
    `business_name` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成业务名',
    `function_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成功能名',
    `function_author` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成功能作者',
    `gen_type` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
    `gen_path` varchar(200) COLLATE utf8mb4_general_ci DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
    `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `verify_permission` varchar(3) COLLATE utf8mb4_general_ci DEFAULT 'Y' COMMENT '是否校验权限(sys_yes_no)',
    `parent_id` bigint DEFAULT NULL COMMENT '父菜单id',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=612395940311110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='代码生成业务表';

INSERT INTO `gen_table` VALUES (605803493171269,'sys_user','用户信息表','SysUser','com.su60.quickboot','system',NULL,NULL,'luyanan','1','E:\\workspace\\luyanan\\quick-boot/generaotorcode','1','2024-10-27 22:00:45','1','2024-12-20 13:51:22',NULL,'Y',100),(609357182300229,'sys_job','定时任务调度表','SysJob','cn.t200.quickboot','quartz',NULL,NULL,'luyanan','1','E:\\workspace\\luyanan\\quick-boot/generaotorcode','1','2024-11-06 23:00:45','1','2024-12-04 23:28:19',NULL,'Y',NULL),(609357182881861,'sys_job_log','定时任务调度日志表','SysJobLog','cn.t200.quickboot','quartz',NULL,NULL,'luyanan','1','E:\\workspace\\luyanan\\quick-boot/generaotorcode','1','2024-11-06 23:00:45','1','2024-11-07 22:45:27',NULL,'Y',NULL),(612395939868741,'sys_logininfor','系统访问记录','SysLogininfor','cn.t200.quickboot','system',NULL,NULL,'luyanan','1','E:\\workspace\\luyanan\\quick-boot/generaotorcode','1','2024-11-15 13:05:30','1','2024-11-15 14:09:34',NULL,'Y',NULL),(612395940311109,'sys_oper_log','操作日志记录','SysOperLog','cn.t200.quickboot','system',NULL,NULL,'luyanan','1','E:\\workspace\\luyanan\\quick-boot/generaotorcode','1','2024-11-15 13:05:30','1','2024-11-15 14:12:09',NULL,'Y',NULL);

CREATE TABLE if not exists `gen_table_column` (
                                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                                  `table_id` bigint DEFAULT NULL COMMENT '归属表编号',
                                                  `column_name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列名称',
    `column_comment` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列描述',
    `column_type` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列类型',
    `java_type` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'JAVA类型',
    `java_field` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'JAVA字段名',
    `is_pk` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否主键（1是）',
    `is_increment` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否自增（1是）',
    `is_required` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否必填（1是）',
    `is_insert` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否为插入字段（1是）',
    `is_edit` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否编辑字段（1是）',
    `is_list` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否列表字段（1是）',
    `is_query` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否查询字段（1是）',
    `query_type` varchar(200) COLLATE utf8mb4_general_ci DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
    `html_type` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
    `dict_type` varchar(200) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
    `sort` int DEFAULT NULL COMMENT '排序',
    `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=624564846772295 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='代码生成业务表字段';


CREATE TABLE if not exists `sys_dict_data` (
                                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
                                               `dict_sort` int DEFAULT '0' COMMENT '字典排序',
                                               `dict_label` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典标签',
    `dict_value` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典键值',
    `dict_type` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
    `css_class` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
    `list_class` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表格回显样式',
    `is_default` char(1) COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
    `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=611097172443206 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典数据表';


INSERT INTO `sys_dict_data` VALUES (1,1,'男','0','sys_user_sex','','','Y','0','1','2024-08-06 15:52:36','',NULL,'性别男'),(2,2,'女','1','sys_user_sex','','','N','0','1','2024-08-06 15:52:36','',NULL,'性别女'),(3,3,'未知','2','sys_user_sex','','','N','0','1','2024-08-06 15:52:36','',NULL,'性别未知'),(4,1,'显示','0','sys_show_hide','','primary','Y','0','1','2024-08-06 15:52:36','',NULL,'显示菜单'),(5,2,'隐藏','1','sys_show_hide','','danger','N','0','1','2024-08-06 15:52:36','',NULL,'隐藏菜单'),(6,1,'正常','0','sys_normal_disable','','primary','Y','0','1','2024-08-06 15:52:36','',NULL,'正常状态'),(7,2,'停用','1','sys_normal_disable','','danger','N','0','1','2024-08-06 15:52:36','',NULL,'停用状态'),(8,1,'正常','0','sys_job_status','','primary','Y','0','1','2024-08-06 15:52:36','',NULL,'正常状态'),(9,2,'暂停','1','sys_job_status','','danger','N','0','1','2024-08-06 15:52:36','',NULL,'停用状态'),(10,1,'默认','DEFAULT','sys_job_group','','','Y','0','1','2024-08-06 15:52:36','',NULL,'默认分组'),(11,2,'系统','SYSTEM','sys_job_group','','','N','0','1','2024-08-06 15:52:36','',NULL,'系统分组'),(12,1,'是','Y','sys_yes_no','','primary','Y','0','1','2024-08-06 15:52:36','',NULL,'系统默认是'),(13,2,'否','N','sys_yes_no','','danger','N','0','1','2024-08-06 15:52:36','',NULL,'系统默认否'),(14,1,'通知','1','sys_notice_type','','warning','Y','0','1','2024-08-06 15:52:36','',NULL,'通知'),(15,2,'公告','2','sys_notice_type','','success','N','0','1','2024-08-06 15:52:36','',NULL,'公告'),(16,1,'正常','0','sys_notice_status','','primary','Y','0','1','2024-08-06 15:52:36','',NULL,'正常状态'),(17,2,'关闭','1','sys_notice_status','','danger','N','0','1','2024-08-06 15:52:36','',NULL,'关闭状态'),(18,99,'其他','0','sys_oper_type','','info','N','0','1','2024-08-06 15:52:36','',NULL,'其他操作'),(19,1,'新增','1','sys_oper_type','','info','N','0','1','2024-08-06 15:52:36','',NULL,'新增操作'),(20,2,'修改','2','sys_oper_type','','info','N','0','1','2024-08-06 15:52:37','',NULL,'修改操作'),(21,3,'删除','3','sys_oper_type','','danger','N','0','1','2024-08-06 15:52:37','',NULL,'删除操作'),(22,4,'授权','4','sys_oper_type','','primary','N','0','1','2024-08-06 15:52:37','',NULL,'授权操作'),(23,5,'导出','5','sys_oper_type','','warning','N','0','1','2024-08-06 15:52:37','',NULL,'导出操作'),(24,6,'导入','6','sys_oper_type','','warning','N','0','1','2024-08-06 15:52:37','',NULL,'导入操作'),(25,7,'强退','7','sys_oper_type','','danger','N','0','1','2024-08-06 15:52:37','',NULL,'强退操作'),(26,8,'生成代码','8','sys_oper_type','','warning','N','0','1','2024-08-06 15:52:37','',NULL,'生成操作'),(27,9,'清空数据','9','sys_oper_type','','danger','N','0','1','2024-08-06 15:52:37','',NULL,'清空操作'),(28,1,'成功','0','sys_common_status','','primary','N','0','1','2024-08-06 15:52:37','',NULL,'正常状态'),(29,2,'失败','1','sys_common_status','','danger','N','0','1','2024-08-06 15:52:37','',NULL,'停用状态'),(600878106284101,0,'默认','default','sys_dict_list_class',NULL,'default','N','0','1','2024-10-13 23:59:18','1','2024-10-13 23:59:18',NULL),(600878167089221,0,'主要','primary','sys_dict_list_class',NULL,NULL,'N','0','1','2024-10-13 23:59:33','1','2024-10-13 23:59:49',NULL),(600878279942213,0,'成功','success','sys_dict_list_class',NULL,'default','N','0','1','2024-10-14 00:00:01','1','2024-10-14 00:00:01',NULL),(600878345912389,0,'信息','info','sys_dict_list_class',NULL,NULL,'N','0','1','2024-10-14 00:00:17','1','2024-10-14 00:00:17',NULL),(600878398996549,0,'警告','warning','sys_dict_list_class',NULL,'default','N','0','1','2024-10-14 00:00:30','1','2024-10-14 00:00:30',NULL),(600878435143749,0,'危险','danger','sys_dict_list_class',NULL,'default','N','0','1','2024-10-14 00:00:39','1','2024-10-14 00:00:52',NULL),(605449740329029,0,'文本框','input','GEN_HTML_TYPE',NULL,'default','N','0','1','2024-10-26 22:01:20','1','2024-10-26 22:01:53',NULL),(605449783861317,0,'文本域','textarea','GEN_HTML_TYPE',NULL,'default','N','0','1','2024-10-26 22:01:31','1','2024-10-26 22:01:47',NULL),(605449920094277,0,'下拉框','select','GEN_HTML_TYPE',NULL,'default','N','0','1','2024-10-26 22:02:04','1','2024-10-26 22:02:04',NULL),(605449954742341,0,'单选框','radio','GEN_HTML_TYPE',NULL,'default','N','0','1','2024-10-26 22:02:12','1','2024-10-26 22:02:12',NULL),(605449981599813,0,'复选框','checkbox','GEN_HTML_TYPE',NULL,'default','N','0','1','2024-10-26 22:02:19','1','2024-10-26 22:02:19',NULL),(605450009825349,0,'日期控件','datetime','GEN_HTML_TYPE',NULL,'default','N','0','1','2024-10-26 22:02:26','1','2024-10-26 22:02:26',NULL),(605450039922757,0,'图片上传','imageUpload','GEN_HTML_TYPE',NULL,'default','N','0','1','2024-10-26 22:02:33','1','2024-10-26 22:02:33',NULL),(605450069483589,0,'文件上传','fileUpload','GEN_HTML_TYPE',NULL,'default','N','0','1','2024-10-26 22:02:40','1','2024-10-26 22:02:40',NULL),(605450099314757,0,'富文本控件','editor','GEN_HTML_TYPE',NULL,'default','N','0','1','2024-10-26 22:02:48','1','2024-10-26 22:02:48',NULL),(607223630286917,0,'正常','0','COMMON_STATUS',NULL,'primary','N','0','1','2024-10-31 22:19:19','1','2024-10-31 22:19:19',NULL),(607223675379781,1,'停用','1','COMMON_STATUS',NULL,'danger','N','0','1','2024-10-31 22:19:30','1','2024-10-31 22:19:30',NULL),(611097063362629,0,'立即执行','1','JOB_MISFIRE_POLICY',NULL,'default','N','0','1','2024-11-11 21:00:21','1','2024-11-11 21:00:36',NULL),(611097149874245,0,'执行一次','2','JOB_MISFIRE_POLICY',NULL,'default','N','0','1','2024-11-11 21:00:42','1','2024-11-11 21:00:42',NULL),(611097172443205,0,'放弃执行','3','JOB_MISFIRE_POLICY',NULL,'default','N','0','1','2024-11-11 21:00:48','1','2024-11-11 21:00:48',NULL);

CREATE TABLE if not exists `sys_dict_type` (
                                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
                                               `dict_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典名称',
    `dict_type` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
    `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `dict_type` (`dict_type`)
    ) ENGINE=InnoDB AUTO_INCREMENT=611097011028038 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典类型表';

INSERT INTO `sys_dict_type` VALUES (1,'用户性别','sys_user_sex','0','1','2024-08-06 15:52:35','',NULL,'用户性别列表'),(2,'菜单状态','sys_show_hide','0','1','2024-08-06 15:52:35','',NULL,'菜单状态列表'),(3,'系统开关','sys_normal_disable','0','1','2024-08-06 15:52:35','',NULL,'系统开关列表'),(4,'任务状态','sys_job_status','0','1','2024-08-06 15:52:35','',NULL,'任务状态列表'),(5,'任务分组','sys_job_group','0','1','2024-08-06 15:52:35','',NULL,'任务分组列表'),(6,'系统是否','sys_yes_no','0','1','2024-08-06 15:52:35','',NULL,'系统是否列表'),(7,'通知类型','sys_notice_type','0','1','2024-08-06 15:52:35','',NULL,'通知类型列表'),(8,'通知状态','sys_notice_status','0','1','2024-08-06 15:52:35','',NULL,'通知状态列表'),(9,'操作类型','sys_oper_type','0','1','2024-08-06 15:52:35','',NULL,'操作类型列表'),(10,'系统状态','sys_common_status','0','1','2024-08-06 15:52:35','',NULL,'登录状态列表'),(600877970198597,'字典项回显样式','sys_dict_list_class','0','1','2024-10-13 23:58:45','1','2024-10-13 23:58:45','字典项回显样式'),(605449668653125,'代码生成-显示类型','GEN_HTML_TYPE','0','1','2024-10-26 22:01:03','1','2024-10-26 22:01:03',NULL),(607223519760453,'通用状态','COMMON_STATUS','0','1','2024-10-31 22:18:52','1','2024-10-31 22:18:56',NULL),(611097011028037,'任务计划执行错误策略','JOB_MISFIRE_POLICY','0','1','2024-11-11 21:00:08','1','2024-12-18 22:07:10',NULL);

CREATE TABLE if not exists `sys_job` (
                                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
                                         `job_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
    `job_group` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
    `invoke_target` varchar(500) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
    `cron_expression` varchar(255) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT 'cron执行表达式',
    `misfire_policy` varchar(20) COLLATE utf8mb4_general_ci DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
    `concurrent` char(1) COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
    `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1暂停）',
    `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注信息',
    `params` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '参数',
    PRIMARY KEY (`id`,`job_name`,`job_group`)
    ) ENGINE=InnoDB AUTO_INCREMENT=612397536370758 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='定时任务调度表';


INSERT INTO `sys_job` VALUES (612040729542725,'测试任务','SYSTEM','testTask','0 0/1 * * * ?','1','Y','1',NULL,'2024-11-14 13:00:08',NULL,'2024-12-19 23:23:17','1\n1\n1\n1\n1\n','11111'),(612397536370757,'测试任务2','SYSTEM','testTask','0 0/1 * * * ?','1','Y','1',NULL,'2024-11-15 13:11:59',NULL,'2024-11-17 15:26:45','','2222');

CREATE TABLE  if not exists `sys_job_log` (
                                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
                                              `job_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
    `job_group` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
    `invoke_target` varchar(500) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
    `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
    `exception_info` varchar(2000) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '异常信息',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `params` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '参数',
    `job_id` bigint DEFAULT NULL COMMENT '任务id',
    `time_consuming` bigint DEFAULT NULL COMMENT '耗时',
    `start_time` datetime DEFAULT NULL COMMENT '开始时间',
    `end_time` datetime DEFAULT NULL COMMENT '结束时间',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='定时任务调度日志表';


CREATE TABLE if not exists `sys_logininfor` (
                                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
                                                `user_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户账号',
    `ipaddr` varchar(128) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '登录IP地址',
    `login_location` varchar(255) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '登录地点',
    `browser` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '浏览器类型',
    `os` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作系统',
    `login_time` datetime DEFAULT NULL COMMENT '访问时间',
    `user_id` bigint DEFAULT NULL COMMENT '用户id',
    `client_id` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '客户端id',
    PRIMARY KEY (`id`),
    KEY `idx_sys_logininfor_lt` (`login_time`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统访问记录';


CREATE TABLE if not exists `sys_menu` (
                                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
                                          `menu_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
    `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
    `order_num` int DEFAULT '0' COMMENT '显示顺序',
    `path` varchar(200) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由地址',
    `component` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组件路径',
    `query` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由参数',
    `route_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由名称',
    `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
    `is_cache` int DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
    `menu_type` char(1) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `visible` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
    `perms` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
    `icon` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '#' COMMENT '菜单图标',
    `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=624750118146118 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='菜单权限表';


INSERT INTO `sys_menu` VALUES (1,'系统管理',0,1,'system',NULL,'','',1,0,'M','0','0','','system','1','2024-08-06 15:52:24','',NULL,'系统管理目录'),(3,'系统工具',0,3,'tool',NULL,'','',1,0,'M','0','0','','tool','1','2024-08-06 15:52:24','',NULL,'系统工具目录'),(4,'若依官网',0,4,'http://ruoyi.vip',NULL,'','',0,0,'M','0','0','','guide','1','2024-08-06 15:52:24','',NULL,'若依官网地址'),(100,'用户管理',1,1,'user','system/user/index','','',1,0,'C','0','0','system:user:list','user','1','2024-08-06 15:52:24','',NULL,'用户管理菜单'),(101,'角色管理',1,2,'role','system/role/index','','',1,0,'C','0','0','system:role:list','peoples','1','2024-08-06 15:52:25','',NULL,'角色管理菜单'),(102,'菜单管理',1,3,'menu','system/menu/index','','',1,0,'C','0','0','system:menu:list','tree-table','1','2024-08-06 15:52:25','',NULL,'菜单管理菜单'),(105,'字典管理',1,6,'dict','system/dict/index','','',1,0,'C','0','0','system:dict:list','dict','1','2024-08-06 15:52:25','',NULL,'字典管理菜单'),(107,'通知公告',1,8,'notice','system/notice/index','','',1,0,'C','0','0','system:notice:list','message','1','2024-08-06 15:52:25','',NULL,'通知公告菜单'),(108,'日志管理',1,9,'log','','','',1,0,'M','0','0','','log','1','2024-08-06 15:52:25','',NULL,'日志管理菜单'),(116,'代码生成',3,2,'gen','tool/gen/index','','',1,0,'C','0','0','generator:gen:list','code','1','2024-08-06 15:52:25','1','2024-12-19 22:20:06','代码生成菜单'),(500,'操作日志',108,1,'sysoperlog','system/sysoperlog/index','','',1,0,'C','0','0','system:operlog:list','form','1','2024-08-06 15:52:26','1','2024-12-18 22:17:20','操作日志菜单'),(501,'登录日志',108,2,'syslogininfor','system/syslogininfor/index','','',1,0,'C','0','0','system:logininfor:list','logininfor','1','2024-08-06 15:52:26','1','2024-12-18 22:18:00','登录日志菜单'),(1000,'用户查询',100,1,'','','','',1,0,'F','0','0','system:user:query','#','1','2024-08-06 15:52:26','',NULL,''),(1001,'用户新增',100,2,'','','','',1,0,'F','0','0','system:user:add','#','1','2024-08-06 15:52:26','',NULL,''),(1002,'用户修改',100,3,'','','','',1,0,'F','0','0','system:user:edit','#','1','2024-08-06 15:52:26','',NULL,''),(1003,'用户删除',100,4,'','','','',1,0,'F','0','0','system:user:remove','#','1','2024-08-06 15:52:26','',NULL,''),(1004,'用户导出',100,5,'','','','',1,0,'F','0','0','system:user:export','#','1','2024-08-06 15:52:26','',NULL,''),(1005,'用户导入',100,6,'','','','',1,0,'F','0','0','system:user:import','#','1','2024-08-06 15:52:26','',NULL,''),(1006,'重置密码',100,7,'','','','',1,0,'F','0','0','system:user:resetPwd','#','1','2024-08-06 15:52:26','',NULL,''),(1007,'角色查询',101,1,'','','','',1,0,'F','0','0','system:role:query','#','1','2024-08-06 15:52:26','',NULL,''),(1008,'角色新增',101,2,'','','','',1,0,'F','0','0','system:role:add','#','1','2024-08-06 15:52:26','',NULL,''),(1009,'角色修改',101,3,'','','','',1,0,'F','0','0','system:role:edit','#','1','2024-08-06 15:52:26','',NULL,''),(1010,'角色删除',101,4,'','','','',1,0,'F','0','0','system:role:remove','#','1','2024-08-06 15:52:26','',NULL,''),(1011,'角色导出',101,5,'','','','',1,0,'F','0','0','system:role:export','#','1','2024-08-06 15:52:26','',NULL,''),(1012,'菜单查询',102,1,'','','','',1,0,'F','0','0','system:menu:query','#','1','2024-08-06 15:52:27','',NULL,''),(1013,'菜单新增',102,2,'','','','',1,0,'F','0','0','system:menu:add','#','1','2024-08-06 15:52:27','',NULL,''),(1014,'菜单修改',102,3,'','','','',1,0,'F','0','0','system:menu:edit','#','1','2024-08-06 15:52:27','',NULL,''),(1015,'菜单删除',102,4,'','','','',1,0,'F','0','0','system:menu:remove','#','1','2024-08-06 15:52:27','',NULL,''),(1025,'字典查询',105,1,'#','','','',1,0,'F','0','0','system:dict:query','#','1','2024-08-06 15:52:28','',NULL,''),(1026,'字典新增',105,2,'#','','','',1,0,'F','0','0','system:dict:add','#','1','2024-08-06 15:52:28','',NULL,''),(1027,'字典修改',105,3,'#','','','',1,0,'F','0','0','system:dict:edit','#','1','2024-08-06 15:52:28','',NULL,''),(1028,'字典删除',105,4,'#','','','',1,0,'F','0','0','system:dict:remove','#','1','2024-08-06 15:52:28','',NULL,''),(1029,'字典导出',105,5,'#','','','',1,0,'F','0','0','system:dict:export','#','1','2024-08-06 15:52:28','',NULL,''),(1035,'公告查询',107,1,'#','','','',1,0,'F','0','0','system:notice:query','#','1','2024-08-06 15:52:28','',NULL,''),(1036,'公告新增',107,2,'#','','','',1,0,'F','0','0','system:notice:add','#','1','2024-08-06 15:52:28','',NULL,''),(1037,'公告修改',107,3,'#','','','',1,0,'F','0','0','system:notice:edit','#','1','2024-08-06 15:52:28','',NULL,''),(1038,'公告删除',107,4,'#','','','',1,0,'F','0','0','system:notice:remove','#','1','2024-08-06 15:52:28','',NULL,''),(1039,'操作查询',500,1,'#','','','',1,0,'F','0','0','system:operlog:query','#','1','2024-08-06 15:52:28','1','2024-12-18 22:17:53',''),(1040,'操作删除',500,2,'#','','','',1,0,'F','0','0','system:operlog:remove','#','1','2024-08-06 15:52:28','1','2024-12-18 22:18:32',''),(1041,'日志导出',500,3,'#','','','',1,0,'F','0','0','monitor:operlog:export','#','1','2024-08-06 15:52:28','',NULL,''),(1042,'登录查询',501,1,'#','','','',1,0,'F','0','0','system:logininfor:query','#','1','2024-08-06 15:52:28','1','2024-12-18 22:21:28',''),(1043,'登录删除',501,2,'#','','','',1,0,'F','0','0','monitor:logininfor:remove','#','1','2024-08-06 15:52:28','',NULL,''),(1044,'日志导出',501,3,'#','','','',1,0,'F','0','0','monitor:logininfor:export','#','1','2024-08-06 15:52:29','',NULL,''),(1045,'账户解锁',501,4,'#','','','',1,0,'F','0','0','monitor:logininfor:unlock','#','1','2024-08-06 15:52:29','',NULL,''),(1055,'生成查询',116,1,'#','','','',1,0,'F','0','0','generator:gen:query','#','1','2024-08-06 15:52:29','1','2024-12-19 22:04:35',''),(1056,'生成修改',116,2,'#','','','',1,0,'F','0','0','generator:gen:edit','#','1','2024-08-06 15:52:29','1','2024-12-19 22:04:39',''),(1057,'生成删除',116,3,'#','','','',1,0,'F','0','0','generator:gen:remove','#','1','2024-08-06 15:52:29','1','2024-12-19 22:04:43',''),(1058,'导入代码',116,4,'#','','','',1,0,'F','0','0','generator:gen:import','#','1','2024-08-06 15:52:29','1','2024-12-19 22:04:47',''),(1059,'预览代码',116,5,'#','','','',1,0,'F','0','0','generator:gen:preview','#','1','2024-08-06 15:52:29','1','2024-12-19 22:04:52',''),(1060,'生成代码',116,6,'#','','','',1,0,'F','0','0','generator:gen:code','#','1','2024-08-06 15:52:29','1','2024-12-19 22:04:56',''),(1061,'定时任务',3,4,'sysjob','tool/job/sysjob/index',NULL,'',1,0,'C','0','0','quartz:sysjob:list','job','1','2024-11-08 00:38:11','1','2024-12-19 23:20:00',''),(1062,'Demo',0,99,'demo',NULL,NULL,'',1,0,'M','0','0',NULL,'code','1','2024-11-17 14:39:43','1','2024-11-17 14:39:43',''),(1063,'crud',1062,1,'crud','demo/crud/index',NULL,'',1,0,'C','0','0','demo:crud:list','edit','1','2024-11-17 14:40:29','1','2024-12-20 10:53:49',''),(624579313455173,'定时任务查询',1061,1,'',NULL,NULL,'',1,0,'F','0','0','quartz:sysjob:query','#','1','2024-12-19 23:19:46','1','2024-12-19 23:19:46',''),(624579450138693,'定时任务修改',1061,2,'',NULL,NULL,'',1,0,'F','0','0','quartz:sysjob:edit','#','1','2024-12-19 23:20:19','1','2024-12-19 23:20:19',''),(624579547689029,'定时任务删除',1061,3,'',NULL,NULL,'',1,0,'F','0','0','quartz:sysjob:remove','#','1','2024-12-19 23:20:43','1','2024-12-19 23:20:43',''),(624579640430661,'定时任务新增',1061,4,'',NULL,NULL,'',1,0,'F','0','0','quartz:sysjob:add','#','1','2024-12-19 23:21:06','1','2024-12-19 23:21:06',''),(624749852430405,'crud新增',1063,1,'',NULL,NULL,'',1,0,'F','0','0','demo:crud:add','#','1','2024-12-20 10:53:41','1','2024-12-20 10:53:41',''),(624749949640773,'crud修改',1063,2,'',NULL,NULL,'',1,0,'F','0','0','demo:crud:edit','#','1','2024-12-20 10:54:05','1','2024-12-20 10:54:05',''),(624750118146117,'crud删除',1063,3,'',NULL,NULL,'',1,0,'F','0','0','demo:crud:remove','#','1','2024-12-20 10:54:46','1','2024-12-20 10:54:46','');


CREATE TABLE if not exists `sys_notice` (
                                            `id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
                                            `notice_title` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
    `notice_type` char(1) COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
    `notice_content` longblob COMMENT '公告内容',
    `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
    `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='通知公告表';


INSERT INTO `sys_notice` VALUES (1,'温馨提醒：2018-07-01 若依新版本发布啦','2',_binary '新版本内容','0','admin','2024-08-06 15:52:39','',NULL,'管理员'),(2,'维护通知：2018-07-01 若依系统凌晨维护','1',_binary '维护内容','0','admin','2024-08-06 15:52:39','',NULL,'管理员');


CREATE TABLE if not exists `sys_oper_log` (
                                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
                                              `method` varchar(200) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '方法名称',
    `request_method` varchar(10) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求方式',
    `oper_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作人员',
    `oper_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求URL',
    `oper_ip` varchar(128) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '主机地址',
    `oper_location` varchar(255) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作地点',
    `oper_param` varchar(2000) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求参数',
    `json_result` varchar(2000) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '返回参数',
    `status` int DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
    `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
    `cost_time` bigint DEFAULT '0' COMMENT '消耗时间',
    `user_id` bigint DEFAULT NULL COMMENT '用户id',
    `client_id` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '客户端id',
    PRIMARY KEY (`id`),
    KEY `idx_sys_oper_log_s` (`status`),
    KEY `idx_sys_oper_log_ot` (`oper_time`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='操作日志记录';


CREATE TABLE  if not exists `sys_role` (
                                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
                                           `role_name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
    `role_key` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
    `role_sort` int NOT NULL COMMENT '显示顺序',
    `data_scope` char(1) COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
    `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
    `status` char(1) COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
    `del_flag` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1826996326446018562 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色信息表';


INSERT INTO `sys_role` VALUES (1,'超级管理员','admin',1,'1',0,1,'0','0','1','2024-08-06 15:52:00','1','2024-12-20 10:54:57','超级管理员'),(2,'普通角色','common',2,'2',0,1,'0','0','1','2024-08-06 15:52:00','1','2024-11-14 22:45:48','普通角色'),(599779743338565,'测试角色1','TEST_1',3,'1',0,1,'0','1',NULL,NULL,NULL,NULL,'1111'),(599782251475013,'测试角色11','TEST_1111',2,'1',0,1,'1','1',NULL,NULL,'1','2024-10-11 09:06:55','222222222'),(599790794924101,'2','2',2,'1',0,1,'0','1',NULL,NULL,NULL,NULL,'2222'),(599811491852357,'2','2',0,'1',0,1,'0','1',NULL,NULL,NULL,NULL,NULL),(599811536961605,'22','21',20,'1',1,1,'0','1',NULL,NULL,NULL,NULL,NULL),(599945460629573,'2','2',0,'1',0,1,'0','1',NULL,NULL,'1','2024-10-11 09:06:55',NULL),(599951811473477,'2','2',0,'1',0,1,'0','0','1','2024-10-11 09:10:12','1','2024-10-11 09:10:12',NULL);


CREATE TABLE  if not exists  `sys_role_menu` (
                                                 `role_id` bigint NOT NULL COMMENT '角色ID',
                                                 `menu_id` bigint NOT NULL COMMENT '菜单ID',
                                                 `id` bigint NOT NULL COMMENT 'id',
                                                 PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色和菜单关联表';


INSERT INTO `sys_role_menu` VALUES (2,1,612184659644485),(2,100,612184659648581),(2,1000,612184659648582),(2,1001,612184659648583),(2,1002,612184659648584),(2,1003,612184659648585),(2,1004,612184659648586),(2,1005,612184659648587),(2,1006,612184659648588),(2,101,612184659648589),(2,1007,612184659648590),(2,1008,612184659648591),(2,1009,612184659648592),(2,1010,612184659648593),(2,1011,612184659652677),(2,102,612184659652678),(2,1012,612184659652679),(2,1013,612184659652680),(2,1014,612184659652681),(2,1015,612184659652682),(2,103,612184659652683),(2,1016,612184659652684),(2,1017,612184659652685),(2,1018,612184659652686),(2,1019,612184659652687),(2,104,612184659652688),(2,1020,612184659652689),(2,1021,612184659652690),(2,1022,612184659652691),(2,1023,612184659652692),(2,1024,612184659652693),(2,105,612184659656773),(2,1025,612184659656774),(2,1026,612184659656775),(2,1027,612184659656776),(2,1028,612184659656777),(2,1029,612184659656778),(2,106,612184659656779),(2,1030,612184659656780),(2,1031,612184659656781),(2,1032,612184659656782),(2,1033,612184659656783),(2,1034,612184659656784),(2,107,612184659656785),(2,1035,612184659656786),(2,1036,612184659656787),(2,1037,612184659660869),(2,1038,612184659660870),(2,108,612184659660871),(2,500,612184659660872),(2,1039,612184659660873),(2,1040,612184659660874),(2,1041,612184659660875),(2,501,612184659660876),(2,1042,612184659660877),(2,1043,612184659660878),(2,1044,612184659660879),(2,1045,612184659660880),(1,1,624750159904837),(1,100,624750159904838),(1,1000,624750159904839),(1,1001,624750159904840),(1,1002,624750159904841),(1,1003,624750159908933),(1,1004,624750159908934),(1,1005,624750159908935),(1,1006,624750159908936),(1,101,624750159908937),(1,1007,624750159908938),(1,1008,624750159908939),(1,1009,624750159908940),(1,1010,624750159908941),(1,1011,624750159908942),(1,102,624750159908943),(1,1012,624750159908944),(1,1013,624750159908945),(1,1014,624750159913029),(1,1015,624750159913030),(1,105,624750159913031),(1,1025,624750159913032),(1,1026,624750159913033),(1,1027,624750159913034),(1,1028,624750159913035),(1,1029,624750159913036),(1,108,624750159913037),(1,500,624750159913038),(1,1039,624750159913039),(1,1040,624750159913040),(1,1041,624750159913041),(1,501,624750159913042),(1,1042,624750159913043),(1,1043,624750159913044),(1,1044,624750159913045),(1,1045,624750159913046),(1,3,624750159913047),(1,116,624750159913048),(1,1055,624750159913049),(1,1056,624750159917125),(1,1057,624750159917126),(1,1058,624750159917127),(1,1059,624750159917128),(1,1060,624750159917129),(1,1061,624750159917130),(1,624579313455173,624750159917131),(1,624579450138693,624750159917132),(1,624579547689029,624750159917133),(1,624579640430661,624750159917134),(1,1062,624750159917135),(1,1063,624750159917136),(1,624749852430405,624750159917137),(1,624749949640773,624750159917138),(1,624750118146117,624750159917139);


CREATE TABLE  if not exists `sys_user` (
                                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                                           `user_name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
    `nick_name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
    `user_type` varchar(2) COLLATE utf8mb4_general_ci DEFAULT '00' COMMENT '用户类型（00系统用户）',
    `email` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户邮箱',
    `phonenumber` varchar(11) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '手机号码',
    `sex` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
    `avatar` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '头像地址',
    `password` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '密码',
    `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    `del_flag` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `login_ip` varchar(128) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '最后登录IP',
    `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
    `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=607234575745094 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户信息表';


INSERT INTO `sys_user` VALUES (1,'admin','若依','00','ry@163.com1','15888888888','1','','{bcrypt}$2a$10$Q3VJOOCp32EQZhiLJpA7Nu4Axma8W.pzx6DrbfJr8cNtDq8nH6kBu','0','0','127.0.0.1','2024-08-06 15:52:23','1','2024-08-06 15:52:23','1','2024-10-31 22:35:37','管理员11111'),(2,'ry','若依','00','ry@qq.com','15666666666','1','','{bcrypt}$2a$10$ppHbcNcXR7Dd7oQOvHVxduLFNtQULLGlqvApptItSz9q8Xkx71D2.','1','0','127.0.0.1','2024-08-06 15:52:23','1','2024-08-06 15:52:23','1','2024-11-06 21:48:36','测试员'),(607234575745093,'test','test','00','','admin','0','','{bcrypt}$2a$10$k2cG6PCcOqPVXJJPq35SaeyZhrD6WlOO6BNCW1Cqo0R4DYmjqQGWi','0','1','',NULL,'1','2024-10-31 23:03:51','1','2024-10-31 23:05:03',NULL);


CREATE TABLE if not exists `sys_user_role` (
                                               `user_id` bigint NOT NULL COMMENT '用户ID',
                                               `role_id` bigint NOT NULL COMMENT '角色ID',
                                               `id` bigint NOT NULL COMMENT 'id',
                                               PRIMARY KEY (`id`),
    UNIQUE KEY `user_id` (`user_id`,`role_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户和角色关联表';

INSERT INTO `sys_user_role` VALUES (1,1,1),(2,2,609339449774149),(2,599951811473477,609339449790533);

