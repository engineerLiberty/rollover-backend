FROM openjdk:17

LABEL authors="engineerliberty"

ADD target/rollover.jar /rollover.jar

EXPOSE 9672

ENTRYPOINT ["java", "-jar", "rollover.jar"]