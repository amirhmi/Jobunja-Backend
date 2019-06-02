#Build
FROM maven:3.5-jdk-8 as BUILD

COPY src /usr/src/myapp/src

COPY pom.xml /usr/src/myapp

RUN mvn -f /usr/src/myapp/pom.xml clean package

#Deploy
FROM tomcat:9.0.20-jre11

COPY --from=BUILD /usr/src/myapp/target/jobunja-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]