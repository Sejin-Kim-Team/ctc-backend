FROM sh827kim/ctc-builder:latest

WORKDIR /app
COPY ./ ./

RUN gradle clean bootJar
COPY build/libs/*.jar backend.jar

EXPOSE 8080
ENTRYPOINT java -Dspring.profiles.active=production -DDB_PASS=$DB_PASS -DDB_URL=$DB_URL -DKA_CID=$KA_CID -DKA_SKEY=$KA_SKEY -DKA_AKEY=$KA_AKEY -jar backend.jar
