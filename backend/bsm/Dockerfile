# Use a imagem do OpenJDK 11 como base
FROM openjdk:17

# Define a pasta de trabalho como /app
WORKDIR /app

# Copia o arquivo .jar da aplicação para a pasta /app
COPY target/bsm-0.0.1-SNAPSHOT.jar app.jar

# Define a porta em que a aplicação vai rodar
EXPOSE 5000

# Define o comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]