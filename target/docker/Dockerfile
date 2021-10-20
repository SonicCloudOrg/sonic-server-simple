FROM adoptopenjdk/openjdk15:alpine AS builder
ADD /sonic-server-simple.jar //
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
ENTRYPOINT ["java","-server","-Xmx800M","-Xms800M","-Xmn300M","-Xss256K","-XX:-UseGCOverheadLimit","-XX:+DisableExplicitGC","-XX:SurvivorRatio=1","-XX:LargePageSizeInBytes=128M","-XX:SoftRefLRUPolicyMSPerMB=0","-Djava.security.egd=file:/dev/./urandom","-jar","/sonic-server-simple.jar"]
