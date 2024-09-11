FROM amazoncorretto:17 AS builder

WORKDIR /boda-server

COPY . .

RUN ./gradlew clean build -x test

############################################

FROM amazoncorretto:17-alpine AS runner

WORKDIR /boda-server

COPY --from=builder /boda-server/build/libs/boda-server-0.0.1-SNAPSHOT.jar .

CMD [ "java", "-jar", "boda-server-0.0.1-SNAPSHOT.jar" ]

EXPOSE 80 443
