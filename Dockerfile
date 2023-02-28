FROM openjdk:11.0.10-jdk-slim

RUN mkdir /vulngrpckotlin /app
COPY . /vulngrpckotlin/
RUN sed -i 's/localhost\:5432/db\:5432/' /vulngrpckotlin/src/main/resources/application-postgresql.yml

RUN cd /vulngrpckotlin \
&& ./gradlew --no-daemon bootJar \
&& cp build/libs/vuln-grpc-kotlin-0.1.0.jar /app/ \
&& rm -Rf build/ \
&& cd / \
&& rm -Rf /vulngrpckotlin /root/.gradle/

WORKDIR /app

ENV PWD=/app
CMD ["java", "-jar", "/app/vuln-grpc-kotlin-0.1.0.jar"]
