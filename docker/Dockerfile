FROM openjdk:11 AS builder

WORKDIR /tmp/locking-poc

COPY . .

RUN ./gradlew clean build

FROM openjdk:11

WORKDIR /app

COPY --from=builder /tmp/locking-poc/hazelcast/build/libs/hazelcast-all.jar ./hazelcast.jar
COPY --from=builder /tmp/locking-poc/redis/build/libs/redis-all.jar ./redis.jar
COPY --from=builder /tmp/locking-poc/zookeeper/build/libs/zookeeper-all.jar ./zookeeper.jar
COPY --from=builder /tmp/locking-poc/database/build/libs/database-all.jar ./database.jar

RUN chmod +x *.jar

ENTRYPOINT ["/bin/bash", "-c", "java $JVM_ARGS -jar /app/$APP.jar $APP_ARGS"]