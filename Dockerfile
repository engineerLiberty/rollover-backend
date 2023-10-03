FROM openjdk:17

LABEL authors="engineerliberty"

ADD target/rollover.jar /rollover.jar

ENTRYPOINT ["java", "-jar", "rollover.jar"]