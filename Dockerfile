FROM openjdk:17-jdk-slim
ADD target/WalletService-1.0-SNAPSHOT.jar wallet_service.jar
ENTRYPOINT ["java","-jar","/wallet_service.jar"]