FROM eclipse-temurin:21

COPY target/*.jar spring-security-project.jar

ENTRYPOINT ["java","-jar","/spring-security-project.jar"]