FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia prima il pom.xml per sfruttare la cache delle dipendenze
# Aggiornato per riflettere il percorso corretto
COPY ./pom.xml .
RUN mvn dependency:go-offline -B

# Copia il codice sorgente e compila
# Aggiornato per riflettere il percorso corretto
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar ./app.jar

# Variabili d'ambiente con valori di default per sviluppo
ENV SERVER_PORT=8080 \
    DB_HOST=database \
    DB_PORT=3306 \
    DB_NAME=san_martino_friendship_day \
    DB_USER=root \
    DB_PASSWORD=password \
    SMTP_USER=teletubbies.pw@gmail.com \
    SMTP_PASSWORD=your_smtp_password \
    BASE_URL=http://localhost:3000

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

