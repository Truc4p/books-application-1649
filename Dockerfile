FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . /app

# Create bin directory and compile all Java files into it
RUN mkdir -p bin
RUN find . -name "*.java" > sources.txt && javac -d bin @sources.txt

EXPOSE 8080

CMD ["java", "-cp", "bin", "BooksApplication.WebServer"]
