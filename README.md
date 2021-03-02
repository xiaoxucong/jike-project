# jike-project 
使用数据库为  mysql8.0 ,derby 没有使用过


# 表结构
#
CREATE TABLE `user` (
       `id` bigint NOT NULL AUTO_INCREMENT,
        `username` varchar(255) NOT NULL,
        `password` varchar(255) NOT NULL,
       `createtime` datetime DEFAULT NULL,
      `time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
#

