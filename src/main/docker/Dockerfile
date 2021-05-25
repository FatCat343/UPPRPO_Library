FROM openjdk:15
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
RUN useradd -m myuser
USER myuser
EXPOSE 8090
CMD java -jar -Dspring.profiles.active=prod application.jar