DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
    `id` bigint(20) NOT NULL COMMENT '主键',
    `type` int(11) DEFAULT NULL COMMENT '类型   1 菜品分类 2 套餐分类',
    `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '分类名称',
    `sort` int(11) NOT NULL DEFAULT '0' COMMENT '顺序',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    `create_user` bigint(20) NOT NULL COMMENT '创建人',
    `update_user` bigint(20) NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `idx_category_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜品及套餐分类';