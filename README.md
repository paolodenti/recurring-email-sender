# Recurring email sender

Quick tooling for my friend Walter.

### Container Setup

```bash
MAIL_PLAIN_NAME="<THe name of the sender>"
SMTP_FROM="<email sender>"
SMTP_HOST="<your smtp host>"
SMTP_PORT="<your smtp port>"
SMTP_USER="<your smtp username>"
SMTP_PASS="<your smtp password>"
SMTP_AUTH="<true|false>"
SMTP_START_TLS="<true|false>"
```

### Start locally

```bash
docker compose -f ./.docker/docker-compose.yml up -d

SMTP_HOST="127.0.0.1" SMTP_PORT="1025" SMTP_AUTH="false" SMTP_START_TLS="false" \
MAIL_PLAIN_NAME="Walter" SMTP_FROM="walter@localhost" \
./mvnw spring-boot:run

# browse http://localhost:8025
```
