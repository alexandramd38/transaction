FROM adoptopenjdk/openjdk11:latest
MAINTAINER alexandra.dan
COPY transaction-springboot/target/transaction-springboot-0.0.1.jar transaction-0.0.1.jar
ENTRYPOINT ["java","-jar","/transaction-0.0.1.jar"]