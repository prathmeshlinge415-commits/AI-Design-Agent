FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY backend ./backend
COPY lib/mysql-connector-j-26.7.0.jar ./lib/mysql-connector-j-26.7.0.jar

RUN mkdir -p generated-images

RUN javac -cp "lib/mysql-connector-j-26.7.0.jar" backend/*.java

EXPOSE 8080

CMD ["java", "-cp", ".:lib/mysql-connector-j-26.7.0.jar", "backend.Main"]