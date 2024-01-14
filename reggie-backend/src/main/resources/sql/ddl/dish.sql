DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish` (
    `id` bigint(20) NOT NULL COMMENT '主键',
    `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜品名称',
    `category_id` bigint(20) NOT NULL COMMENT '菜品分类id',
    `price` decimal(10,2) DEFAULT NULL COMMENT '菜品价格',
    `code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '商品码',
    `image` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '图片',
    `description` varchar(400) COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
    `status` int(11) NOT NULL DEFAULT '1' COMMENT '0 停售 1 起售',
    `sort` int(11) NOT NULL DEFAULT '0' COMMENT '顺序',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    `create_user` bigint(20) NOT NULL COMMENT '创建人',
    `update_user` bigint(20) NOT NULL COMMENT '修改人',
    `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `idx_dish_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜品管理';