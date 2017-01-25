CREATE DATABASE my_base;
CREATE USER 'jdbc_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'jdbc_user'@'localhost' WITH GRANT OPTION;