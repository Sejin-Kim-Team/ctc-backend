FROM gradle:jdk17-alpine as builder

WORKDIR /app

COPY ./ ./
RUN gradle clean build -x test --no-daemon

FROM gradle:jdk17-alpine
COPY --from=builder /root/.gradle /root/.gradle