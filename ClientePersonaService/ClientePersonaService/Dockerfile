# Usar una imagen base de OpenJDK 17
FROM openjdk:17-jdk-alpine

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo JAR de tu aplicación al directorio de trabajo del contenedor
COPY target/ClientePersonaService-0.0.1-SNAPSHOT.jar /app/ClientePersonaService.jar

# Exponer el puerto en el que tu aplicación Spring Boot se ejecuta (por defecto es 8080)
EXPOSE 8080

# Comando para ejecutar tu aplicación
ENTRYPOINT ["java", "-jar", "ClientePersonaService.jar"]
