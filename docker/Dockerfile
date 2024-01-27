FROM openjdk:8
ENV TZ=Asia/Shanghai
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
COPY auto-imaotai-server-1.0.0-SNAPSHOT.jar /application.jar
ENTRYPOINT ["/entrypoint.sh"]