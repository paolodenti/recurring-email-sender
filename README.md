# Recurring email sender

Quick tooling for my friend Walter.

Send an email every *n* minutes.

### Docker execution

```bash

# send an email every hour

read -r -d '' BODY << EOM
This is
the body
of the email
EOM

docker run --rm -it \
-e SMTP_USER="<smtp username>" \
-e SMTP_PASS="<smtp password>" \
-e SMTP_HOST="<smtp host>" \
-e SMTP_PORT="<smtp port>" \
-e SMTP_AUTH="<true|false>" \
-e SMTP_START_TLS="<true|false>" \
-e SMTP_FROM="someone@example.com" \
-e MAIL_PLAIN_NAME="Someone" \
-e MAIL_DELAY_MINUTES=60 \
-e MAIL_TITLE="Test email" \
-e MAIL_BODY="$BODY" \
-e MAIL_TO="someone1@<example.com>,someone2@<example.com>" \
paolodenti/recurring-email-sender:latest
```

### Local development

Java 21 needed.

```bash

docker compose -f ./.docker/docker-compose.yml up -d

SMTP_HOST="127.0.0.1" \
SMTP_PORT="1025" \
SMTP_AUTH="false" \
SMTP_START_TLS="false" \
SMTP_FROM="someone@example.com" \
MAIL_PLAIN_NAME="Someone" \
MAIL_DELAY_MINUTES=1 \
MAIL_TITLE="Test email" \
MAIL_BODY="some body content here" \
MAIL_TO="someone1@<example.com>,someone2@<example.com>" \
APP_LOG_LEVEL="DEBUG" \
./mvnw spring-boot:run
```
