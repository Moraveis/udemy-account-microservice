# udemy-account-microservice

## Set or override properties with java and spring boot

### Commandline arguments
```shell
java -jar ./target/accounts-0.0.1-SNAPSHOT.jar --spring.profiles.active="qa"
```

### JVM system
```shell
java -jar ./target/accounts-0.0.1-SNAPSHOT.jar -Dspring.profiles.active="qa"
```

### Environment variables
```shell
export SPRING_PROFILE_ACTIVE="qa"
java -jar ./target/accounts-0.0.1-SNAPSHOT.jar
```

### Using maven commandline
```shell
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active='qa'"
```

### Run Mysql docker image
```shell
docker run -p 3306:3306 --name accountsdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=accounts -d mysql
docker logs --follow accountsdb
docker exec -it accountsdb mysql -u root -p
```