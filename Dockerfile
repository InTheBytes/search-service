FROM openjdk:17
ADD target/searchservice-0.0.1-SNAPSHOT.jar SearchService.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.datasource.hikari.maximum-pool-size=1","-jar","SearchService.jar"]
