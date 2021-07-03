#docker instructions
Run
1) docker run -p 127.0.0.1:3306:3306  --name mariadb -e MYSQL_ROOT_PASSWORD=root -d mariadb:latest

2) docker exec -it mariadb /bin/bash

3) mysql -uroot -proot

4) create database simpledb;

5) exit;

6) exit;

- 

To exit:

7) docker stop mariadb 

8) to delete: docker rm mariadb

9) to delete image: docker rmi mariadb
