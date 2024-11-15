USE `locking-poc`;

DROP TABLE IF EXISTS `jobs`;

CREATE TABLE `jobs` (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `available` tinyint(1) NOT NULL DEFAULT '1',
    `holder_uuid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `start_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;