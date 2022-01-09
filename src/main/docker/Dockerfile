FROM ubuntu:bionic-20200311

ENV DEBIAN_FRONTEND=noninteractive

WORKDIR /root

RUN apt-get update && apt-get install -y \
    libxcb-shape0 \
    libxcb-xfixes0 \
    wget \
    && rm -rf /var/lib/apt/lists/*

RUN wget -O java.tar.gz https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.tar.gz && \
    tar zxvf java.tar.gz && rm java.tar.gz && \
    chmod a+x -R /root/jdk-17.0.1 && \
    chown -R root:root /root/jdk-17.0.1

ENV JAVA_HOME="/root/jdk-17.0.1" \
    PATH=$PATH:$JAVA_HOME/bin

ADD /sonic-server-simple.jar /root
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && ls
ENTRYPOINT ["/root/jdk-17.0.1/bin/java","-server","-Dfile.encoding=utf-8","-Xmx800M","-Xms800M","-Xmn300M","-Xss256K","-XX:-UseGCOverheadLimit","-XX:+DisableExplicitGC","-XX:SurvivorRatio=1","-XX:LargePageSizeInBytes=128M","-XX:SoftRefLRUPolicyMSPerMB=0","-Djava.security.egd=file:/dev/./urandom","-jar","sonic-server-simple.jar"]