FROM openjdk:17
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN javac TCPServer.java
EXPOSE 4999
CMD ["java","TCPServer"]