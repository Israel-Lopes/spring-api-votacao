FROM openjdk:17-jdk-slim-buster AS java-builder

WORKDIR /app

COPY . /app

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY --from=java-builder /app/target/app-0.0.1-SNAPSHOT.jar /app/app.jar
COPY --from=java-builder /app/node-api-valida-cpf /app/node-api-valida-cpf

ENV PORT_JAVA=8080
ENV PORT_NODE=9090

# Expõe as portas 8080 e 9090
EXPOSE 8080 9090

RUN apt-get update && apt-get install -y nodejs

CMD java -jar /app/app.jar & cd /app/node-api-valida-cpf && node main.js