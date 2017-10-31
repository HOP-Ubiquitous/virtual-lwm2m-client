FROM alpine:edge
MAINTAINER felipe@hopu.eu

RUN apk add --no-cache openjdk8

COPY *.jar /opt/lwm2m-iotagent-client/lib/
COPY *.json /opt/lwm2m-iotagent-client/lib/

ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/lwm2m-iotagent-client/lib/lwm2m-iotagent-client-1.0-jar-with-dependencies.jar", "docker"]

EXPOSE 1234
EXPOSE 1235
EXPOSE 1236