# CloudSql + Hibernate + Jpa + RestEasy -> No Injection Framework

## Requirements
* [Apache Maven](http://maven.apache.org) 3.0 or greater
* a local MySQL server (for running locally with devserver)
* JDK 7+

## MySql Server
 * Install: sudo apt-get install mysql-server
 * Commands: 

 ```sh
    sudo /usr/local/Cellar/mysql/5.7.17/support-files/mysql.server start
    /usr/local/Cellar/mysql/5.7.17/support-files/mysql.server restart
```
 * Execute "scripts/sql/start_bd.sql"

 * CloudSQL tutorial
 	https://cloud.google.com/appengine/docs/java/cloud-sql/

## Appengine
 * Run Local:
	`
		mvn appengine:devserver
	`
 * Deploy Dev:
	`
		mvn appengine:update
	`

## MVC 
 1. REST(RestEasy):
 	* Name: *RS.java
 	* Não deve conter lógica de negócio, apenas validação de parâmetros se necessário e parse de JSONs
 2. SERVICE:
 	* Name: *Service.java
 	* Extends: Service.java
 	* Este é o lugar em que a lógica de negócio deve acontecer, pode se comunicar com REPOSITORY e INTEGRATION.
 3. REPOSITORY:
 	* Name: *Repository.java
 	* Extends: BaseRepository.java
 	* Este lugar é responsável por fazer queries e persistir dados no BD
 