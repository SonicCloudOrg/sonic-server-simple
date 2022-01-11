FROM ubuntu:bionic-20200311

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y \
    pkg-config ccache clang python python3.6-dev python3-pip swig file wget tar patch autoconf-archive autogen automake make cmake libtool bison flex perl nasm curl gfortran libasound2-dev freeglut3-dev libgtk2.0-dev libusb-dev zlib1g libffi-dev libbz2-dev zlib1g-dev \
    && rm -rf /var/lib/apt/lists/*

RUN wget -O java.tar.gz https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.tar.gz && \
    tar zxvf java.tar.gz && rm java.tar.gz && \
    chmod a+x -R /jdk-17.0.1 && \
    chown -R root:root /jdk-17.0.1

ENV JAVA_HOME="/jdk-17.0.1" \
    PATH=$PATH:$JAVA_HOME/bin

ADD /sonic-server-simple.jar /
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN cd / && mkdir keepFiles imageFiles recordFiles packageFiles logs
ENTRYPOINT ["java","-server","-Dfile.encoding=utf-8","-Xmx800M","-Xms800M","-Xmn300M","-Xss256K","-XX:-UseGCOverheadLimit","-XX:+DisableExplicitGC","-XX:SurvivorRatio=1","-XX:LargePageSizeInBytes=128M","-XX:SoftRefLRUPolicyMSPerMB=0","-Djava.security.egd=file:/dev/./urandom","-jar","/sonic-server-simple.jar"]