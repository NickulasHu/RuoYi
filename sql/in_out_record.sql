-- ----------------------------
-- 1、存储每一个已配置的 jobDetail 的详细信息
-- ----------------------------
drop table if exists sys_in_out_record;
create table sys_in_out_record (
    recordId           varchar(50)   not null,
    studentCode        varchar(100)  null,
    entryType          varchar(50)   null,
    studentName        varchar(50)   null,
    sex                varchar(20)   null,
    orgId              varchar(50)   null,
    dormId             varchar(50)   null,
    alarmTime          varchar(50)   null,
    primary key (recordId)
) engine=innodb;

ALTER TABLE sys_in_out_record ADD recordType varchar(50) NULL COMMENT '考勤类型';
ALTER TABLE sys_user ADD personId varchar(50) NULL COMMENT '海康人员ID';
