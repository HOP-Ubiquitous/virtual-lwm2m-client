FROM alpine:edge
MAINTAINER felipe@hopu.eu

RUN apk add --no-cache openjdk8

COPY target/virtual-lwm2m-client.jar /opt/lwm2m-client/lib/
COPY src/main/resources/devices.json /opt/lwm2m-client/lib/

ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/lwm2m-client/lib/virtual-lwm2m-client.jar", "docker"]