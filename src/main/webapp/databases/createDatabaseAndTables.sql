DROP DATABASE IF EXISTS ridisearch;
CREATE DATABASE ridisearch;
USE ridisearch;

SET GLOBAL max_allowed_packet = 1024*1024*1024;

CREATE TABLE role (
  id int auto_increment primary key,
  role_name varchar(50) NOT NULL UNIQUE
);

INSERT INTO role (role_name)
VALUES ('ROLE_ADMIN'), ('ROLE_USER');


CREATE TABLE user (
  id int auto_increment primary key,
  name varchar(100),
  user_name varchar(50) unique key NOT NULL,
  password varchar(50) NOT NULL,
  address varchar (250),
  phone_number varchar(20),
  date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,             
  date_updated DATETIME                                         -- has a trigger to be updated on every update to the row		
);

DROP TRIGGER IF EXISTS `update_user_trigger`;
DELIMITER //
	CREATE TRIGGER `update_user_trigger` BEFORE UPDATE ON `user`
 	FOR EACH ROW SET NEW.`date_updated` = NOW()
//
DELIMITER ;

CREATE TABLE user_role (
  user_id int NOT NULL,
  role_id int NOT NULL, 
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (role_id) REFERENCES role (id)
);


CREATE TABLE items (
  id int auto_increment primary key,
  item_name varchar(50) NOT NULL,
  stored_location varchar(250) NOT NULL,
  item_type varchar(250) NOT NULL,
  is_private bit(1) DEFAULT 0,
  date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                   -- written once and not changed on every update
  date_updated DATETIME,                                              -- has a trigger to be updated on every update to the row
  user_id int,
  file LONGBLOB,
  FOREIGN KEY (user_id) REFERENCES user(id) 
);

DROP TRIGGER IF EXISTS `item_trigger`;
DELIMITER //
        CREATE TRIGGER `item_trigger` BEFORE UPDATE ON `items`
        FOR EACH ROW SET NEW.`date_updated` = NOW()
//
DELIMITER ;



CREATE TABLE user_log (
  id int auto_increment primary key,
  logged_in_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  user_name varchar(250),
  name varchar(100),
  user_id int,
  action varchar(250)  
);



  ---------------------------------------------------------------------CREATE ADMIN USER-----------------------------------------------------------------------------------
INSERT INTO USER (name, user_name, password, address, phone_number) 
VALUES ("Abhinayak Swar", "aswar@deerwalk.com", MD5("P@ssw0rd"), "Maitidevi, Kathmandu", "8881212");

INSERT INTO USER_ROLE (user_id, role_id) values (1,1);
INSERT INTO USER_ROLE (user_id, role_id) values (1,2);




