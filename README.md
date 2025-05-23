# Recurring email sender

Quick tooling for my friend Walter.

Send an email every *n* milliseconds.

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
-e SMTP_PASS="<mtp password>" \
-e SMTP_HOST="<the smtp host>" \
-e SMTP_PORT="<the smtp port>" \
-e SMTP_AUTH="<true|false>" \
-e SMTP_START_TLS="<true|false>" \
-e SMTP_FROM="someone@example.com" \
-e MAIL_PLAIN_NAME="Someone" \
-e MAIL_DELAY=3600000 \
-e MAIL_TITLE="Test email" \
-e MAIL_BODY="$BODY" \
-e MAIL_TO="someone1@<example.com>,someone2@<example.com>" \
paolodenti/recurring-email-sender:latest
```
