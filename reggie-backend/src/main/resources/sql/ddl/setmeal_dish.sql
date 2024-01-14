DROP TABLE IF EXISTS `setmeal_dish`;
CREATE TABLE `setmeal_dish` (
    `id` bigint(20) NOT NULL COMMENT '主键',
    `setmeal_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '套餐id ',
    `dish_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '菜品id',
    `name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '菜品名称 （冗余字段）',
    `price` decimal(10,2) DEFAULT NULL COMMENT '菜品原价（冗余字段）',
    `copies` int(11) NOT NULL COMMENT '份数',
    `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    `create_user` bigint(20) NOT NULL COMMENT '创建人',
    `update_user` bigint(20) NOT NULL COMMENT '修改人',
    `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='套餐菜品关系';