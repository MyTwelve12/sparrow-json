


####后端json配置表###
CREATE TABLE `t_sparrow_backend_config` (
    `tid` int(11) NOT NULL AUTO_INCREMENT,
    `template_alias` varchar(64) DEFAULT NULL COMMENT '模板别名',
    `code` varchar(255) DEFAULT NULL COMMENT '方法code',
    `type` int(11) DEFAULT NULL COMMENT '方法类型',
    `desc` varchar(1024) DEFAULT NULL COMMENT '方法描述',
    `config` text COMMENT '方法配置',
    `create_id` int(11) DEFAULT NULL COMMENT '创建人ID',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_id` int(11) DEFAULT NULL COMMENT '更新人ID',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `del_flag` int(11) NOT NULL DEFAULT '1' COMMENT '删除标志 1：未删除；2：已删除',
    PRIMARY KEY (`tid`),
    KEY `idx_backend_type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='云帆后端json配置规则表';

INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(1, 'add', 'insertTargetRule', 1, '插入目标规则信息，支持直接插入表or调用rpc, 1DB 2RPC', '{"insertType":1,"tableRule":${tableRule}}', NULL, '2024-11-07 10:22:07', NULL, '2024-11-07 10:22:07', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(2, 'add', 'insertParam', 1, '插入or调用rpc的条件参数，支持多参数对象。当插入表时、默认使用key为tableInsertDTO的对象。默认从body中根据insertField为key取值。如有转换的参数则使用转换规则', '{"tableInsertDTO":${insertParam}}', NULL, '2024-11-07 16:03:27', NULL, '2024-11-07 16:03:27', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(3, 'add', 'onlyOneCheck', 1, '检验参数，依据给定条件进行数据库唯一性查询（给定的多参数条件在数据库中无数据）。需满足此校验参数中的所有场景，一旦有某查询场景不唯一、则此次插入操作将不会被执行,返回对应的错误code及msg', '{"datasourceName":"${datasourceName}","tableName":"${tableName}","fields":"${fields}","errorCode":"${errorCode}","errorMsg":"${errorMsg}"}', NULL, '2024-11-07 16:03:27', NULL, '2024-11-07 16:03:27', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(4, 'update', 'updateTargetRule', 1, '插入目标规则信息，支持直接插入表or调用rpc, 1DB 2RPC', '{"updateType":1,"tableRule":{"datasourceName":"${datasourceName}","tableName":"${tableName}"}}', NULL, '2024-11-07 16:03:27', NULL, '2024-11-07 16:03:27', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(5, 'update', 'updateParam', 1, '插入or调用rpc的条件参数，支持多参数对象。当插入表时、默认使用key为tableInsertDTO的对象。默认从body中根据insertField为key取值。如有转换的参数则使用转换规则', '{"tableUpdateDTO":${updateParam}}', NULL, '2024-11-07 16:03:27', NULL, '2024-11-07 16:03:27', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(6, 'update', 'updateCondition', 1, '更新or调用rpc的条件参数，支持多参数对象。当插入表时、默认使用key为tableInsertDTO的对象。默认从body中根据insertField为key取值。如有转换的参数则使用转换规则', '{"tableConditionDTO":${updateCondition}}', NULL, '2024-11-07 16:03:27', NULL, '2024-11-07 16:03:27', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(7, 'update', 'onlyOneCheck', 1, '检验参数，依据给定条件进行数据库唯一性查询（给定的多参数条件在数据库中无数据）。需满足此校验参数中的所有场景，一旦有某查询场景不唯一、则此次插入操作将不会被执行,返回对应的错误code及msg', '{"datasourceName":"${datasourceName}","tableName":"${tableName}","fields":"${fields}","errorCode":"${errorCode}","errorMsg":"${errorMsg}"}', NULL, '2024-11-07 16:03:27', NULL, '2024-11-07 16:03:27', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(8, 'delete', 'updateTargetRule', 1, '更新目标规则信息，支持直接更新表or调用rpc, 1DB 2RPC', '{"updateType":1,"tableRule":{"datasourceName":"${datasourceName}","tableName":"${tableName}"}}', NULL, '2024-11-07 16:03:27', NULL, '2024-11-07 16:03:27', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(9, 'delete', 'updateParam', 1, '更新or调用rpc的条件参数，支持多参数对象。当插入表时、默认使用key为tableInsertDTO的对象。默认从body中根据insertField为key取值。如有转换的参数则使用转换规则', '{"tableUpdateDTO":${updateParam}}', NULL, '2024-11-07 16:03:27', NULL, '2024-11-07 16:03:27', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(10, 'delete', 'updateCondition', 1, '更新or调用rpc的条件参数，支持多参数对象。当更新表时、默认使用key为tableInsertDTO的对象。默认从body中根据insertField为key取值。如有转换的参数则使用转换规则', '{"tableConditionDTO":${updateCondition}}', NULL, '2024-11-07 16:03:27', NULL, '2024-11-07 16:03:27', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(11, 'delete', 'onlyOneCheck', 1, '检验参数，依据给定条件进行数据库唯一性查询（给定的多参数条件在数据库中无数据）。需满足此校验参数中的所有场景，一旦有某查询场景不唯一、则此次插入操作将不会被执行,返回对应的错误code及msg', '{"datasourceName":"${datasourceName}","tableName":"${tableName}","fields":"${fields}","errorCode":"${errorCode}","errorMsg":"${errorMsg}"}', NULL, '2024-11-07 16:03:27', NULL, '2024-11-07 16:03:27', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(12, 'list', 'queryTargetRule', 1, '查询目标规则信息，支持直接查询表or调用rpc，queryType：1DB 2RPC，查询表时支持多表查询', '{"queryType":1,"tableRule":${tableRule},"outputRule":{"outputType":${outputType}}}', NULL, '2024-11-07 16:10:13', NULL, '2024-11-07 16:10:13', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(13, 'list', 'queryParam', 1, '查询or调用rpc的条件参数，支持多参数对象。当查询表时、默认使用key为tableQueryDTO的对象、作为查询的限制条件。默认从body中根据queryField为key取值。如有转换的参数则使用转换规则', '{"tableQueryDTO":${tableQueryDTO},"pageDTO":[{"tableField":"pageNo","queryField":"pageNo"},{"tableField":"pageSize","queryField":"pageSize"}]}', NULL, '2024-11-07 16:10:13', NULL, '2024-11-07 16:10:13', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(14, 'add', 'rulesDB', 1, 'DB转换规则-动态添加更新字段到dto中的查询（需替换流程中的数据源），根据输入字段的key作为条件查询表，得到结果中的key映射添加到dto。 field中多条件间以connect字段做连接符，compare比较符支持=、<、>和in，拼接表达式为：connect + tableField + compare + sourceField的value。 默认取key为tableInsertDTO的对象数据，定制化需求自行更改流程。tableItem：对应的子key name，如无需使用子key则填null。valueAlias：指定添加字段的name，如无需使用对象结构（值的键为boObjKey）则填null。boObjKey：添加到多键值对象时，指定添加的key。', NULL, NULL, '2024-11-08 16:55:51', NULL, '2024-11-08 16:55:51', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(15, 'update', 'rulesDB', 1, 'DB转换规则-动态添加更新字段到dto中的查询（需替换流程中的数据源），根据输入字段的key作为条件查询表，得到结果中的key映射添加到dto。 field中多条件间以connect字段做连接符，compare比较符支持=、<、>和in，拼接表达式为：connect + tableField + compare + sourceField的value。 默认取key为tableInsertDTO的对象数据，定制化需求自行更改流程。tableItem：对应的子key name，如无需使用子key则填null。valueAlias：指定添加字段的name，如无需使用对象结构（值的键为boObjKey）则填null。boObjKey：添加到多键值对象时，指定添加的key。', NULL, NULL, '2024-11-08 16:55:51', NULL, '2024-11-08 16:55:51', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(16, 'delete', 'rulesDB', 1, 'DB转换规则-动态添加更新字段到dto中的查询（需替换流程中的数据源），根据输入字段的key作为条件查询表，得到结果中的key映射添加到dto。 field中多条件间以connect字段做连接符，compare比较符支持=、<、>和in，拼接表达式为：connect + tableField + compare + sourceField的value。 默认取key为tableInsertDTO的对象数据，定制化需求自行更改流程。tableItem：对应的子key name，如无需使用子key则填null。valueAlias：指定添加字段的name，如无需使用对象结构（值的键为boObjKey）则填null。boObjKey：添加到多键值对象时，指定添加的key。', NULL, NULL, '2024-11-08 16:55:51', NULL, '2024-11-08 16:55:51', 1);

INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(17, 'list', 'resultRule', 1, '对查询的返回结果，批量调用rpc获取结果、填充数据，此规则会与fillFieldByTransObj的规则或其他规则配合使用。每个子对象中的boObjList为调用rpc的参数，entityItem表达式为jsonpath表达式，数据为查询es的结果，ListTransType表示是否将获取到的结果，按集合方法进行转换处理，1为将集合元素转为string 2为将集合元素转为int，distinctType表示是否进行去重，1为去重。最后将rpc获取得到的数据存入以transKey为key的对象中、供后续使用。', '[]', NULL, '2024-11-08 16:55:51', NULL, '2024-11-08 16:55:51', 1);
INSERT INTO t_sparrow_backend_config
(tid, template_alias, code, `type`, `desc`, config, create_id, create_time, update_id, update_time, del_flag)
VALUES(18, 'list', 'fillFieldByTransObj', 1, '批量根据转换对象填充查询的返回结果。每个子对象中，根据transKey、获取transKey对应的对象数据。listFilterRule为批量调用结果的过滤规则，targetFieldKey为表示查询结果中的具体key，targetFieldTransType表示根据targetFieldKey、在循环从查询中获取值后做的转换，1转为string 2转为int；sourceFieldKey为transKey对应数据对象中的key，根据sourceFieldKey过滤得到值，表达式为sourceFieldValue = transKeyValue.filter((item) => item.get(sourceFieldKey) == targetFieldValue)。entityItem为从获取的值sourceFieldValue中获取具体赋值对象，valueAlias为赋值的key，targetDefaultValue为默认值。', '[]', NULL, '2024-11-08 16:55:51', NULL, '2024-11-08 16:55:51', 1);


####菜单表###
CREATE TABLE `t_cms_menu` (
  `tid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `code` varchar(64) DEFAULT NULL COMMENT '菜单代码',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(255) DEFAULT NULL COMMENT '页面路由',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `level` int(11) NOT NULL DEFAULT '0' COMMENT '权限级别',
  `layer` int(1) DEFAULT '0' COMMENT '层级',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级目录',
  `path` varchar(255) DEFAULT NULL COMMENT '层级关系',
  `child_num` int(11) DEFAULT '0' COMMENT '按钮数量',
  `channel_flag` int(11) NOT NULL DEFAULT '1' COMMENT '渠道标识',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` int(11) DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` int(11) NOT NULL DEFAULT '1' COMMENT '删除标志',
  `system` int(11) DEFAULT '1' COMMENT '系统类型',
  `status` int(11) DEFAULT '1' COMMENT '菜单状态',
  PRIMARY KEY (`tid`),
  KEY `idx_cms_menu_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理后台菜单表';

####角色表###
CREATE TABLE `t_cms_role` (
  `tid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(64) NOT NULL COMMENT '角色名',
  `role_type` int(11) DEFAULT NULL COMMENT '角色类型',
  `source` int(1) DEFAULT '1' COMMENT '数据来源',
  `user_num` int(11) DEFAULT '0' COMMENT '角色下账号数',
  `channel_flag` int(11) DEFAULT '1' COMMENT '渠道标识',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` int(11) DEFAULT NULL COMMENT '更新人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` int(11) NOT NULL DEFAULT '1' COMMENT '删除标志',
  PRIMARY KEY (`tid`),
  KEY `idx_cms_role_name_type` (`role_name`,`role_type`,`channel_flag`,`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='管理后台角色表';


####角色菜单关联表###
CREATE TABLE `t_cms_role_menu_rlt` (
   `tid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `fk_role_tid` int(11) NOT NULL COMMENT '角色主键',
   `fk_menu_tid` int(11) NOT NULL COMMENT '菜单主键',
   `create_id` int(11) DEFAULT NULL COMMENT '创建人ID',
   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_id` int(11) DEFAULT NULL COMMENT '更新人ID',
   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   `del_flag` int(11) NOT NULL DEFAULT '1' COMMENT '删除标志',
   PRIMARY KEY (`tid`),
   KEY `idx_cms_role_tid` (`fk_role_tid`),
   KEY `idx_cms_menu_tid` (`fk_menu_tid`),
   KEY `idx_cms_role_del_flag` (`fk_role_tid`,`del_flag`),
   KEY `idx_cms_role_menu_del_flag` (`fk_role_tid`,`fk_menu_tid`,`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=197202 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理后台角色菜单关联表';

####渠道组表###
CREATE TABLE t_channel_group (
                                 tid int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 name varchar(255) DEFAULT NULL COMMENT '渠道组名称',
                                 effect_status int(11) DEFAULT NULL COMMENT '生效状态',
                                 start_time datetime DEFAULT NULL COMMENT '生效开始时间',
                                 end_time datetime DEFAULT NULL COMMENT '生效结束时间',
                                 status int(11) DEFAULT '0' COMMENT '状态',
                                 create_by varchar(64) DEFAULT NULL COMMENT '创建人',
                                 create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 update_by varchar(64) DEFAULT NULL COMMENT '更新人',
                                 update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 del_flag int(11) NOT NULL DEFAULT '1' COMMENT '删除标志',
                                 PRIMARY KEY (tid)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='渠道组表';

####渠道组关联表###
CREATE TABLE t_channel_group_relation (
                                          tid int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                          channel_group_id int(11) NOT NULL COMMENT '渠道组ID',
                                          type int(11) DEFAULT NULL COMMENT '类型',
                                          channel_id int(11) NOT NULL COMMENT '渠道ID',
                                          goods_group_id int(11) NOT NULL COMMENT '商品组ID',
                                          create_by varchar(64) DEFAULT NULL COMMENT '创建人',
                                          create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          update_by varchar(64) DEFAULT NULL COMMENT '更新人',
                                          update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          del_flag int(11) NOT NULL DEFAULT '1' COMMENT '删除标志',
                                          PRIMARY KEY (tid),
                                          KEY idx_channel_group_id (channel_group_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='渠道组关联表';


####商品组表###
CREATE TABLE t_goods_group (
                               tid int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                               name varchar(255) DEFAULT NULL COMMENT '商品组名称',
                               type int(11) DEFAULT NULL COMMENT '商品组类型',
                               effect_status int(11) DEFAULT NULL COMMENT '生效状态',
                               start_time datetime DEFAULT NULL COMMENT '生效开始时间',
                               end_time datetime DEFAULT NULL COMMENT '生效结束时间',
                               status int(11) DEFAULT '0' COMMENT '状态',
                               create_by varchar(64) DEFAULT NULL COMMENT '创建人',
                               create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               update_by varchar(64) DEFAULT NULL COMMENT '更新人',
                               update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               del_flag int(11) NOT NULL DEFAULT '1' COMMENT '删除标志',
                               PRIMARY KEY (tid)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='商品组表';


-- auto-generated definition
create table t_frontend_config
(
    tid           int auto_increment comment '主键'
        primary key,
    function_name varchar(255)  null comment '功能名称',
    type          int(10)       null comment '类型，1：部分字段转Object或者array,2:表单类型,3：业务变量，4：默认表单组件模版，5：可变参数；6：固定参数',
    name          varchar(255)  null comment '类型的名称',
    value         varchar(5000) null comment '类型的值',
    version       int(10)       null,
    del_flag      int           null comment '删除标志 1：未删除 2：已删除'
)
    comment '前端配置表' charset = utf8mb4;

create index idx_function_name
    on t_frontend_config (function_name);

create index idx_name
    on t_frontend_config (name);


INSERT INTO t_frontend_config (tid, function_name, type, name, value, version, del_flag) VALUES (9, '资源管理', 1, '数组或对象', '[{"+操作：":"+操作(object)："},{"+行为：":"+行为(object)："},{"+主体数组：":"+主体数组(array)："},{"+api：":"+api(object)："},{"+数据：":"+数据(object)："},{"+初始化API：":"+初始化API(object)："},{"+主体：":"+主体(object)："},{"+字段：":"+字段(array)："},{"+新增接口API：":"+新增接口API(object)："},{"+成功回调：":"+成功回调(object)："},{"+更新接口：":"+更新接口(object)："},{"+删除接口：":"+删除接口(object)："},{"+message：":"+message(object)："},{"+字段展示：":"+字段展示(object)："},{"+过滤：":"+过滤(object)："},{"+新增：":"+新增(object)："},{"+值数组：":"+值数组(array)："}]
', 2, 1);
INSERT INTO t_frontend_config (tid, function_name, type, name, value, version, del_flag) VALUES (10, '资源管理', 8, '业务枚举', '{"类型枚举":"[{\\"label\\":\\"目录\\",\\"value\\":1},{\\"label\\":\\"页面\\",\\"value\\":2}]","显示状态枚举":"[{\\"label\\":\\"显示中\\",\\"value\\":1},{\\"label\\":\\"已隐藏\\",\\"value\\":2}]","层级枚举":"[{\\"label\\":\\"一级\\",\\"value\\":1},{\\"label\\":\\"二级\\",\\"value\\":2},{\\"label\\":\\"三级\\",\\"value\\":3},{\\"label\\":\\"四级\\",\\"value\\":4}]","上级目录枚举":"{\\"url\\":\\"#layerListApi?layer=layer\\",\\"data\\":{\\"system\\":1,\\"layer\\":\\"#layer-1\\",\\"status\\":1,\\"pageNo\\":1,\\"pageSize\\":500}}"}', 2, 1);
INSERT INTO t_frontend_config (tid, function_name, type, name, value, version, del_flag) VALUES (11, '资源管理', 2, '表单类型', '[{"name":"表单","value":"form","showField":"form","targetField":"form"},{"name":"输入框","value":"input-text","showField":"input-text","targetField":"input-text"},{"name":"单选框","value":"radios","showField":"radios","targetField":"radios"},{"name":"选择框","value":"select","showField":"select","targetField":"select"},{"name":"按钮","value":"button","showField":"button","targetField":"button"},{"name":"输入框表格","value":"input-table","showField":"input-table","targetField":"input-table"}]', 2, 1);
INSERT INTO t_frontend_config (tid, function_name, type, name, value, version, del_flag) VALUES (12, '资源管理', 3, '业务变量', '[{"name":"资源管理菜单.主键id","value":"tid","showField":"tid","targetField":"tid"},{"name":"资源管理菜单.名称","value":"name","showField":"name","targetField":"name"},{"name":"资源管理菜单.菜单代码","value":"code","showField":"code","targetField":"code"},{"name":"资源管理菜单.菜单图标","value":"icon","showField":"icon","targetField":"icon"},{"name":"资源管理菜单.页面路由","value":"url","showField":"url","targetField":"url"},{"name":"资源管理菜单.类型","value":"type","showField":"type","targetField":"type"},{"name":"资源管理菜单.权限级别","value":"level","showField":"level","targetField":"level"},{"name":"资源管理菜单.层级","value":"layer","showField":"layer","targetField":"layer"},{"name":"资源管理菜单.排序","value":"order_num","showField":"orderNum","targetField":"orderNum"},{"name":"资源管理菜单.上级目录","value":"parent_id","showField":"parentId","targetField":"parentId"},{"name":"资源管理菜单.层级关系","value":"path","showField":"path","targetField":"path"},{"name":"资源管理菜单.按钮数量","value":"child_num","showField":"childNum","targetField":"childNum"},{"name":"资源管理菜单.渠道标识","value":"channel_flag","showField":"channelFlag","targetField":"channelFlag"},{"name":"资源管理菜单.创建人ID","value":"create_id","showField":"createId","targetField":"createId"},{"name":"资源管理菜单.更新人ID","value":"update_id","showField":"updateId","targetField":"updateId"},{"name":"资源管理菜单.创建时间","value":"create_time","showField":"createTime","targetField":"createTime"},{"name":"资源管理菜单.更新时间","value":"update_time","showField":"updateTime","targetField":"updateTime"},{"name":"资源管理菜单.删除标志","value":"del_flag","showField":"delFlag","targetField":"delFlag"},{"name":"资源管理菜单.系统类型","value":"system","showField":"system","targetField":"system"},{"name":"资源管理菜单.菜单状态","value":"status","showField":"status","targetField":"status"}]', 2, 1);
INSERT INTO t_frontend_config (tid, function_name, type, name, value, version, del_flag) VALUES (13, '资源管理', 7, '前端模版参数映射', '[{"name":"操作","value":"operation","showField":"operation","targetField":"operation"},{"name":"新增","value":"actions","showField":"actions","targetField":"actions"},{"name":"等级","value":"level","showField":"level","targetField":"level"},{"name":"文案","value":"label","showField":"label","targetField":"label"},{"name":"行为","value":"action","showField":"action","targetField":"action"},{"name":"标题","value":"title","showField":"title","targetField":"title"},{"name":"主体","value":"body","showField":"body","targetField":"body"},{"name":"主体数组","value":"body","showField":"body","targetField":"body"},{"name":"是否隐藏","value":"hidden","showField":"hidden","targetField":"hidden"},{"name":"前端组件类型","value":"type","showField":"type","targetField":"type"},{"name":"映射类型","value":"type","showField":"type","targetField":"type"},{"name":"来源","value":"options","showField":"options","targetField":"options"},{"name":"是否可见","value":"visibleOn","showField":"visibleOn","targetField":"visibleOn"},{"name":"是否必须","value":"required","showField":"required","targetField":"required"},{"name":"是否多选","value":"multiple","showField":"multiple","targetField":"multiple"},{"name":"字段名称","value":"labelField","showField":"labelField","targetField":"labelField"},{"name":"字段值","value":"valueField","showField":"valueField","targetField":"valueField"},{"name":"需要条件","value":"requiredOn","showField":"requiredOn","targetField":"requiredOn"},{"name":"字段","value":"columns","showField":"columns","targetField":"columns"},{"name":"模版","value":"templateCode","showField":"templateCode","targetField":"templateCode"},{"name":"值数组","value":"value","showField":"value","targetField":"value"},{"name":"行为类型","value":"actionType","showField":"actionType","targetField":"actionType"},{"name":"大小","value":"size","showField":"size","targetField":"size"},{"name":"对话框","value":"dialog","showField":"dialog","targetField":"dialog"},{"name":"是否显示页脚","value":"showFooter","showField":"showFooter","targetField":"showFooter"},{"name":"初始化API","value":"initApi","showField":"initApi","targetField":"initApi"},{"name":"链接","value":"url","showField":"url","targetField":"url"},{"name":"数据","value":"data","showField":"data","targetField":"data"},{"name":"页数","value":"pageNo","showField":"pageNo","targetField":"pageNo"},{"name":"分页大小","value":"pageSize","showField":"pageSize","targetField":"pageSize"},{"name":"值","value":"value","showField":"value","targetField":"value"},{"name":"是否只读","value":"readOnly","showField":"readOnly","targetField":"readOnly"},{"name":"需要确认","value":"needConfirm","showField":"needConfirm","targetField":"needConfirm"},{"name":"添加能力","value":"addable","showField":"addable","targetField":"addable"},{"name":"新增接口API","value":"addApi","showField":"addApi","targetField":"addApi"},{"name":"成功回调","value":"successCallback","showField":"successCallback","targetField":"successCallback"},{"name":"请求方式","value":"method","showField":"method","targetField":"method"},{"name":"更新接口","value":"updateApi","showField":"updateApi","targetField":"updateApi"},{"name":"删除接口","value":"deleteApi","showField":"deleteApi","targetField":"deleteApi"},{"name":"添加成功","value":"addSuccess","showField":"addSuccess","targetField":"addSuccess"},{"name":"是否可移除","value":"removable","showField":"removable","targetField":"removable"},{"name":"事件","value":"onEvent","showField":"onEvent","targetField":"onEvent"},{"name":"编辑表格","value":"editable","showField":"editable","targetField":"editable"},{"name":"组件名称","value":"componentName","showField":"componentName","targetField":"componentName"},{"name":"提交后重置","value":"resetAfterSubmit","showField":"resetAfterSubmit","targetField":"resetAfterSubmit"},{"name":"表单名称","value":"formName","showField":"formName","targetField":"formName"},{"name":"类名","value":"className","showField":"className","targetField":"className"},{"name":"模式","value":"mode","showField":"mode","targetField":"mode"},{"name":"文案对齐方式","value":"labelAlign","showField":"labelAlign","targetField":"labelAlign"},{"name":"页面id","value":"pageTid","showField":"pageTid","targetField":"pageTid"},{"name":"行为s","value":"action","showField":"action","targetField":"action"},{"name":"源","value":"source","showField":"source","targetField":"source"},{"name":"字段展示","value":"columns","showField":"columns","targetField":"columns"},{"name":"过滤","value":"filter","showField":"filter","targetField":"filter"},{"name":"路径","value":"path","showField":"path","targetField":"path"},{"name":"名称","value":"name","showField":"name","targetField":"name"},{"name":"成功","value":"success","showField":"success","targetField":"success"},{"name":"映射","value":"map","showField":"map","targetField":"map"}]', 2, 1);
INSERT INTO t_frontend_config (tid, function_name, type, name, value, version, del_flag) VALUES (14, '资源管理', 9, '默认组件模版', '[{"type":"button","templateCode":"默认","level":"link"},{"type":"button","templateCode":"新增按钮","level":"success","action":{"actionType":"dialog","api":{"successCallback":{"actionType":"ajax"}}}},{"type":"button","templateCode":"删除按钮","behavior":"Delete","className":"m-r-xstext-danger","level":"link","confirmText":"确认要删除页面吗？","action":{"actionType":"ajax","api":{"method":"post","messages":{"success":"操作成功"}}}},{"type":"radios","templateCode":"默认","required":true},{"type":"form","templateCode":"默认","mode":"horizontal","labelAlign":"top","size":"md","formName":"updateButtonForm","actionType":"dialog","api":{"method":"post"}},{"type":"form","templateCode":"新增表单","mode":"horizontal","labelAlign":"top","size":"md","formName":"updateButtonForm","api":{"method":"post","successCallback":{"actionType":"ajax"}}},{"type":"form","templateCode":"无页脚","actionType":"dialog","dialog":{"showFooter":false},"mode":"horizontal","labelAlign":"top","size":"md","formName":"updateButtonForm"},{"type":"form","templateCode":"无页脚且提交后重置","actionType":"dialog","dialog":{"showFooter":false},"mode":"horizontal","labelAlign":"top","size":"md","debug":true,"resetAfterSubmit":true,"formName":"updateButtonForm"},{"type":"select","templateCode":"默认","multiple":false},{"type":"input-text","templateCode":"默认","required":false},{"type":"input-table","templateCode":"默认","name":"buttonList","needConfirm":true,"addable":true,"editable":true,"removable":true,"value":"#items","addApi":{"successCallback":{"actionType":"ajax","api":{"method":"post"}}},"onEvent":{"addSuccess":{"actions":[{"actionType":"reload","componentName":"updateButtonForm"}]}}}]', 2, 1);
INSERT INTO t_frontend_config (tid, function_name, type, name, value, version, del_flag) VALUES (15, '资源管理', 6, '模版中的常量参数', '[{"name":"primaryField","type":1,"desc":"主键key","value":"tid"},{"name":"rowShowExpand","type":1,"desc":"树形展开条件","value":"record.type === 1"}]', 2, 1);
INSERT INTO t_frontend_config (tid, function_name, type, name, value, version, del_flag) VALUES (16, '资源管理', 5, '模版中的可变参数', '{"operation":{"name":"operation","type":1,"desc":"列操作操作","value":""},"columns":{"name":"columns","type":1,"desc":"列展示","value":""},"filter":{"name":"filter","type":1,"desc":"筛选条件","value":""},"actions":{"name":"actions","type":1,"desc":"功能","value":""}}', 2, 1);
INSERT INTO t_frontend_config (tid, function_name, type, name, value, version, del_flag) VALUES (17, '资源管理', 4, '请求链接', '[{"name":"总部资源列表查询","value":"listApi","showField":"listApi","targetField":"#listApi"},{"name":"总部资源新增","value":"addApi","showField":"addApi","targetField":"#addApi"},{"name":"总部资源更新","value":"updateApi","showField":"updateApi","targetField":"#updateApi"},{"name":"总部资源删除","value":"deleteApi","showField":"deleteApi","targetField":"#deleteApi"},{"name":"总部资源更新path和level","value":"updatePathLevelApi","showField":"updatePathLevelApi","targetField":"#updatePathLevelApi"},{"name":"总部资源显示和隐藏","value":"showHideApi","showField":"showHideApi","targetField":"#showHideApi"},{"name":"总部资源排序菜单查询","value":"subListApi","showField":"subListApi","targetField":"#subListApi"},{"name":"总部资源获取下拉的菜单列表","value":"layerListApi","showField":"layerListApi","targetField":"#layerListApi"},{"name":"总部资源更新菜单排序","value":"sortUpdateApi","showField":"sortUpdateApi","targetField":"#sortUpdateApi"},{"name":"资源页面对应按钮的列表查询","value":"menuButtonListApi","showField":"menuButtonListApi","targetField":"#menuButtonListApi"},{"name":"资源页面对应按钮新增","value":"menuButtonAddApi","showField":"menuButtonAddApi","targetField":"#menuButtonAddApi"},{"name":"页面资源对应按钮更新信息","value":"menuButtonUpdateApi","showField":"menuButtonUpdateApi","targetField":"#menuButtonUpdateApi"},{"name":"资源页面对应按钮的删除","value":"menuButtonDeleteApi","showField":"menuButtonDeleteApi","targetField":"#menuButtonDeleteApi"}]', 2, 1);

