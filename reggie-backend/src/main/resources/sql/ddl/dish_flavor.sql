DROP TABLE IF EXISTS `dish_flavor`;
CREATE TABLE `dish_flavor` (
    `id` bigint(20) NOT NULL COMMENT '主键',
    `dish_id` bigint(20) NOT NULL COMMENT '菜品',
    `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '口味名称',
    `value` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '口味数据list',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    `create_user` bigint(20) NOT NULL COMMENT '创建人',
    `update_user` bigint(20) NOT NULL COMMENT '修改人',
    `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜品口味关系表';