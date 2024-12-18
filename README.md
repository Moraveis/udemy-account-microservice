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

## Dashboards

### Eureka server

[Dashboard for Eureka Server](http://localhost:8070)

### Grafana

[Dashboard for Grafana](http://localhost:3000)

[Documentation](https://grafana.com/docs/loki/latest/)

[Public Dashboards](https://grafana.com/grafana/dashboards/)

### Prometheus

[Documentation](https://prometheus.io/docs/visualization/grafana/)

## Security 

### Keycloak

[Documentation](https://www.keycloak.org/getting-started/getting-started-docker)

```shell
docker run -p 7080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.0.7 start-dev
```
