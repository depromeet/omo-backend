DROP TABLE `location`;
DROP TABLE `recommendation`;
DROP TABLE `stamp`;
DROP TABLE `omakase`;
DROP TABLE `user`;

CREATE TABLE `user`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `nickname` VARCHAR(8) NOT NULL UNIQUE,
    `email` VARCHAR(45) NOT NULL UNIQUE,
    `is_activated` TINYINT(1) NOT NULL DEFAULT 1,
    `profile_url` VARCHAR(255) NOT NULL,
    `created_date` DATETIME NOT NULL,
    `last_stamp_date` DATE,
    `role` VARCHAR(5) NOT NULL,
    PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `omakase`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `county` VARCHAR(10) NOT NULL,
    `phone_number` VARCHAR(11),
    `photo_url` VARCHAR(255),
    `description` VARCHAR(255) NOT NULL,
    `level` VARCHAR(7) NOT NULL,
    `category` VARCHAR(20) NOT NULL,
    `price_information` VARCHAR(100),
    `business_hours` VARCHAR(100),
    `recommendation_count` BIGINT NOT NULL,
    `holiday` VARCHAR(10),
    PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `location`(
    `omakase_id` BIGINT NOT NULL AUTO_INCREMENT,
    `latitude` DECIMAL(15, 10) NOT NULL,
    `longitude` DECIMAL(15, 10) NOT NULL,
    PRIMARY KEY(`omakase_id`),
    FOREIGN KEY(`omakase_id`) REFERENCES `omakase`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `stamp`(
     `id` BIGINT NOT NULL AUTO_INCREMENT,
     `created_date` DATETIME NOT NULL,
     `receipt_issuance_date` DATE,
     `is_certified` TINYINT(1) NOT NULL,
     `file_url` VARCHAR(255),
     `omakase_id` BIGINT NOT NULL,
     `user_id` BIGINT NOT NULL,
     PRIMARY KEY(`id`),
     FOREIGN KEY(`omakase_id`) REFERENCES `omakase`(`id`),
     FOREIGN KEY(`user_id`) REFERENCES `user`(`id`),
     CONSTRAINT uniq UNIQUE (`user_id`, `omakase_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `recommendation`(
      `user_id` BIGINT NOT NULL,
      `omakase_id` BIGINT NOT NULL,
      PRIMARY KEY(`user_id`, `omakase_id`),
      FOREIGN KEY(`omakase_id`) REFERENCES `omakase`(`id`),
      FOREIGN KEY(`user_id`) REFERENCES `user`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;