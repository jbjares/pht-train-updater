FROM pht/parent-spring 

ARG JAR_FILE
COPY ${JAR_FILE} /app/app.jar
ENTRYPOINT ["dockerize", "-timeout", "5m", "-wait", "http://193.196.20.86:8761/actuator/health", "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar", "--spring.profiles.active=denbi"]
EXPOSE 6003

