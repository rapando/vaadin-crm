FROM openjdk:11
WORKDIR /
ADD app.jar app.jar
RUN useradd -m myuser
USER myuser
EXPOSE 8080
CMD java -jar -Dspring.profiles.active=prod app.jar