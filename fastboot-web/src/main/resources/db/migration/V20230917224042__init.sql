create table if not exists sys_dict
(
    id          bigint                 not null comment 'id'
    primary key,
    create_time datetime               null comment '创建时间',
    update_time datetime               null comment '修改时间',
    create_by   bigint                 null comment '创建人',
    update_by   bigint                 null comment '修改人',
    del_flag    varchar(3) default '0' null comment '逻辑删除(0:未删除,1:删除)',
    type        varchar(100)           null comment '字典类型',
    description varchar(255)           null comment '字典描述'
    )
    comment '字段表';

create index unique_type
    on sys_dict (del_flag, type);

create table if not exists sys_dict_item
(
    id          bigint                 not null comment 'id'
    primary key,
    create_time datetime               null comment '创建时间',
    update_time datetime               null comment '修改时间',
    create_by   bigint                 null comment '创建人',
    update_by   bigint                 null comment '修改人',
    del_flag    varchar(3) default '0' null comment '逻辑删除(0:未删除,1:删除)',
    dict_id     bigint                 null comment '字典表id',
    value       varchar(255)           null comment '字典值',
    label       varchar(255)           null comment '字典标签',
    type        varchar(100)           null comment '字典类型',
    description varchar(255)           null comment '字典描述',
    sort        int        default 0   null comment '排序',
    style       varchar(255)           null comment '样式'
    )
    comment '字典项表';

create index unique_type_value
    on sys_dict_item (del_flag, value, type);


create table if not exists sys_menu
(
    id          bigint                 not null comment 'id'
    primary key,
    create_time datetime               null comment '创建时间',
    update_time datetime               null comment '修改时间',
    create_by   bigint                 null comment '创建人',
    update_by   bigint                 null comment '修改人',
    del_flag    varchar(3) default '0' null comment '逻辑删除(0:未删除,1:删除)',
    menu_name   varchar(255)           null comment '菜单名称',
    permission  varchar(255)           null comment '权限',
    path        varchar(255)           null comment '菜单路径',
    parent_id   bigint                 null comment '父菜单id',
    icon        varchar(255)           null comment 'icon',
    sort        int        default 1   null comment '排序',
    keep_alive  varchar(3) default '0' null comment '保持活跃(1:不,0:保持)',
    type        varchar(3)             null comment '菜单类型(1:菜单,2:按钮)',
    blank       varchar(3) default '1' null comment '是否外链(1:否,0:是)'
    )
    comment '系统菜单表';


create table if not exists sys_role
(
    id          bigint                 not null comment 'id'
    primary key,
    create_time datetime               null comment '创建时间',
    update_time datetime               null comment '修改时间',
    create_by   bigint                 null comment '创建人',
    update_by   bigint                 null comment '修改人',
    del_flag    varchar(3) default '0' null comment '逻辑删除(0:未删除,1:删除)',
    role_name   varchar(255)           not null comment '角色名称',
    role_code   varchar(255)           not null comment '角色编码',
    remark      varchar(255)           null comment '备注',
    constraint sys_role_del_flag_role_code_uindex
    unique (del_flag, role_code)
    )
    comment '系统角色表';


create table if not exists sys_role_menu
(
    id          bigint                 not null comment 'id'
    primary key,
    role_id     bigint                 null comment '角色id',
    menu_Id     bigint                 null comment '菜单id',
    create_time datetime               null comment '创建时间',
    create_by   bigint                 null comment '创建人',
    del_flag    varchar(3) default '0' null comment '逻辑删除(0:未删除,1:删除)'
    )
    comment '系统角色菜单关联表';

create index unique_role_id_menu_id
    on sys_role_menu (role_id, menu_Id, del_flag);

create table if not exists sys_user
(
    id          bigint                 not null comment 'id'
    primary key,
    create_time datetime               null comment '创建时间',
    update_time datetime               null comment '修改时间',
    create_by   bigint                 null comment '创建人',
    update_by   bigint                 null comment '修改人',
    del_flag    varchar(3) default '0' null comment '逻辑删除(0:未删除,1:删除)',
    username    varchar(50)            not null comment '用户名',
    password    varchar(255)           not null comment '密码',
    name        varchar(255)           not null comment '名称',
    phone       varchar(13)            null comment '手机号'
    )
    comment '系统用户表';

create index sys_user_create_time_index
    on sys_user (create_time);

create index unique_username
    on sys_user (del_flag, username);


create table if not exists sys_user_role
(
    id          bigint                 not null comment 'id'
    primary key,
    create_time datetime               null comment '创建时间',
    create_by   bigint                 null comment '创建人',
    del_flag    varchar(3) default '0' null comment '逻辑删除(0:未删除,1:删除)',
    user_id     bigint                 not null comment '用户id',
    role_id     bigint                 not null comment '角色id',
    constraint unique_user_id_role_id
    unique (del_flag, user_id, role_id)
    )
    comment '用户角色关联表';







