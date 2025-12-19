FROM amazoncorretto:21
COPY final.jar my-final.jar
ENTRYPOINT [ "java", "-jar", "my-final.jar" ]