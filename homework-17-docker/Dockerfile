FROM maven:3.6.3-openjdk-11-slim

ENV PROJECT_DIR=/opt/app/library

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
RUN mkdir -p $PROJECT_DIR/library-app
ADD ./library-app/src/ $PROJECT_DIR/library-app/src
ADD ./library-app/pom.xml $PROJECT_DIR/library-app/
RUN mkdir -p $PROJECT_DIR/library-app/library-web-app
ADD ./library-web-app/src/ $PROJECT_DIR/library-web-app/src
ADD ./library-web-app/pom.xml $PROJECT_DIR/library-web-app
ADD ./library-web-app/package-lock.json $PROJECT_DIR/library-web-app
ADD ./library-web-app/package.json $PROJECT_DIR/library-web-app
ADD ./library-web-app/webpack.config.js $PROJECT_DIR/library-web-app
ADD ./library-web-app/webpack.dev.config.js $PROJECT_DIR/library-web-app
RUN mvn clean package -Dmaven.test.skip=trueco

FROM openjdk:11-jre-slim

ENV PROJECT_DIR=/opt/app/library

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

COPY --from=0 $PROJECT_DIR/library-app/target/library* $PROJECT_DIR/

EXPOSE 8080

CMD ["java", "-jar", "/opt/app/library/library-app-1.0-SNAPSHOT.jar"]