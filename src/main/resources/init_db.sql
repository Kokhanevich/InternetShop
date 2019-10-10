CREATE SCHEMA `internetshop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internetshop`.`items` (
  `item_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`item_id`));

  INSERT INTO `internetshop`.`items` (`name`, `price`) VALUES ('Nokia 5800', '3000');

  CREATE TABLE `internetshop`.`users_roles` (
  `users_roles_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `role_id` bigint(11) NOT NULL,
  PRIMARY KEY (`users_roles_id`),
  INDEX `user_role_user_fk_idx` (`user_id` ASC) VISIBLE,
  INDEX `user_role_role_fk_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `user_role_user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internetshop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_role_role_fk`
    FOREIGN KEY (`role_id`)
    REFERENCES `internetshop`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `buckets` (
  `bucket_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  PRIMARY KEY (`bucket_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  KEY `user_id_fk_idx` (`user_id`),
  CONSTRAINT `user_id_fk`
  FOREIGN KEY (`user_id`)
  REFERENCES `users` (`user_id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TABLE `buckets_items` (
  `buckets_items_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `bucket_id` bigint(11) NOT NULL,
  `item_id` bigint(11) NOT NULL,
  PRIMARY KEY (`buckets_items_id`),
  KEY `bucket_id_fk_idx` (`bucket_id`),
  KEY `item_id_fk_idx` (`item_id`),
  CONSTRAINT `bucket_id_fk`
  FOREIGN KEY (`bucket_id`)
  REFERENCES `buckets` (`bucket_id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  CONSTRAINT `item_id_fk`
  FOREIGN KEY (`item_id`
   REFERENCES `items` (`item_id`)
   ON DELETE CASCADE
   ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TABLE `orders` (
  `order_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `orders_users_fk_idx` (`user_id`),
  CONSTRAINT `orders_users_fk`
  FOREIGN KEY (`user_id`)
  REFERENCES `users` (`user_id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

CREATE TABLE `orders_items` (
  `orders_items_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(11) NOT NULL,
  `item_id` bigint(11) NOT NULL,
  PRIMARY KEY (`orders_items_id`),
  KEY `orders_items_orders_fk_idx` (`order_id`),
  KEY `orders_items_items_fk_idx` (`item_id`),
  CONSTRAINT `orders_items_items_fk`
  FOREIGN KEY (`item_id`)
  REFERENCES `items` (`item_id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  CONSTRAINT `orders_items_orders_fk`
  FOREIGN KEY (`order_id`)
  REFERENCES `orders` (`order_id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

CREATE TABLE `roles` (
  `role_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO roles (name) values ('USER');
INSERT INTO roles (name) values ('ADMIN');

CREATE TABLE `users_roles` (
  `users_roles_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `role_id` bigint(11) NOT NULL,
  PRIMARY KEY (`users_roles_id`),
  KEY `user_role_user_fk_idx` (`user_id`),
  KEY `user_role_role_fk_idx` (`role_id`),
  CONSTRAINT `user_role_role_fk`
  FOREIGN KEY (`role_id`)
  REFERENCES `roles` (`role_id`),
  CONSTRAINT `user_role_user_fk`
  FOREIGN KEY (`user_id`)
  REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `user_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(500) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `salt` blob,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

