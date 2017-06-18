drop table if exists t_emp;

/*==============================================================*/
/* Table: t_emp                                                 */
/*==============================================================*/
create table t_emp
(
   emp_id               varchar(50) not null,
   dept_id              varchar(32) not null,
   name                 varchar(50),
   primary key (emp_id)
);

alter table t_emp add constraint FK_dept_emp foreign key (dept_id)
      references t_dept (dept_id) on delete restrict on update restrict;
drop table if exists r_emp_role;

/*==============================================================*/
/* Table: r_emp_role                                            */
/*==============================================================*/
create table r_emp_role
(
   emp_id               varchar(50) not null,
   role_id              varchar(32) not null,
   state                varchar(1),
   primary key (emp_id, role_id)
);

alter table r_emp_role add constraint FK_r_emp_role foreign key (emp_id)
      references t_emp (emp_id) on delete restrict on update restrict;

alter table r_emp_role add constraint FK_r_emp_role2 foreign key (role_id)
      references t_role (role_id) on delete restrict on update restrict;
      drop table if exists t_org;

/*==============================================================*/
/* Table: t_org                                                 */
/*==============================================================*/
create table t_org
(
   org_id               int not null,
   name                 varchar(50),
   primary key (org_id)
);
      drop table if exists t_privilege;

/*==============================================================*/
/* Table: t_privilege                                           */
/*==============================================================*/
create table t_privilege
(
   pri_id               varchar(32) not null,
   name                 varchar(50),
   primary key (pri_id)
);
      drop table if exists t_role;

/*==============================================================*/
/* Table: t_role                                                */
/*==============================================================*/
create table t_role
(
   role_id              varchar(32) not null,
   name                 varchar(50),
   primary key (role_id)
);
      drop table if exists role_pri;

/*==============================================================*/
/* Table: role_pri                                              */
/*==============================================================*/
create table role_pri
(
   pri_id               varchar(32) not null,
   role_id              varchar(32) not null,
   primary key (pri_id, role_id)
);

alter table role_pri add constraint FK_belong foreign key (role_id)
      references t_role (role_id) on delete restrict on update restrict;

alter table role_pri add constraint FK_own foreign key (pri_id)
      references t_privilege (pri_id) on delete restrict on update restrict;
      drop table if exists t_dept;

/*==============================================================*/
/* Table: t_dept                                                */
/*==============================================================*/
create table t_dept
(
   dept_id              varchar(32) not null,
   org_id               int,
   name                 varchar(50),
   primary key (dept_id)
);

alter table t_dept add constraint FK_org_dept foreign key (org_id)
      references t_org (org_id) on delete restrict on update restrict;
      drop table if exists t_leader;

/*==============================================================*/
/* Table: t_leader                                              */
/*==============================================================*/
create table t_leader
(
   emp_id               varchar(50) not null,
   position             varchar(50) not null,
   dept_id              varchar(32),
   name                 varchar(50),
   primary key (emp_id, position)
);

alter table t_leader add constraint FK_inherit foreign key (emp_id)
      references t_emp (emp_id) on delete restrict on update restrict;
  
      
/**
*Author:hcho
*Date:2015-10-09
*Desc:创建投诉模块和受理表
*/
 
 
DROP TABLE IF EXISTS comp_reply;

DROP TABLE IF EXISTS complain;

/*==============================================================*/
/* Table: comp_reply                                            */
/*==============================================================*/
CREATE TABLE comp_reply
(
   reply_id             VARCHAR(32) NOT NULL,
   comp_id              VARCHAR(32) NOT NULL,
   replyer              VARCHAR(20),
   reply_dept           VARCHAR(200),
   reply_time           DATETIME,
   content              VARCHAR(500),
   PRIMARY KEY (reply_id)
);

/*==============================================================*/
/* Table: complain                                              */
/*==============================================================*/
CREATE TABLE complain
(
   comp_id              VARCHAR(32) NOT NULL,
   comp_company         VARCHAR(200),
   comp_name            VARCHAR(20),
   comp_mobile          VARCHAR(20),
   comp_time            DATETIME,
   to_comp_dept         VARCHAR(200),
   to_comp_name         VARCHAR(20),
   comp_title           VARCHAR(200),
   comp_content         TEXT,
   is_NM                BOOL,
   state                VARCHAR(1),
   PRIMARY KEY (comp_id)
);

ALTER TABLE comp_reply ADD CONSTRAINT FK_complain FOREIGN KEY (comp_id)
      REFERENCES complain (comp_id) ON DELETE RESTRICT ON UPDATE RESTRICT;
    