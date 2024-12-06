


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
