FROM openjdk:17
EXPOSE 8080
ADD /target/yourcryptobot-1.0.0.jar yourcryptobot.jar
ENTRYPOINT ["java","-jar","yourcryptobot.jar"]