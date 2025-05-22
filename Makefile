service = recurring-email-sender

ROOT_DIR:=$(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))

define checkstyle
#!/bin/sh
make style
exit $?
endef
export checkstyle

.PHONY=setup
setup:
	@cd $(ROOT_DIR) && \
echo "$$checkstyle" > .git/hooks/pre-push && chmod 755 .git/hooks/pre-push && echo "setup completed"

.PHONY=version
version:
	@cd $(ROOT_DIR) && \
VERSION="$$($(ROOT_DIR)/mvnw -q -s settings.xml -Dexec.executable=echo -Dexec.args='$${project.version}' --non-recursive exec:exec)" && \
echo "$${VERSION}"

.PHONY=upgrade
upgrade:
	@cd $(ROOT_DIR) && \
VERSION="$$($(ROOT_DIR)/mvnw -q -s settings.xml -Dexec.executable=echo -Dexec.args='$${project.version}' --non-recursive exec:exec)" && \
NEXTVERSION=$$(echo $${VERSION} | awk -F. -v OFS=. '{$$NF += 1 ; print}') && \
echo "$${NEXTVERSION}" | $(ROOT_DIR)/mvnw -q versions:set -DgenerateBackupPoms=false

.PHONY=style
style:
	@cd $(ROOT_DIR) && \
$(ROOT_DIR)/mvnw -s settings.xml --no-transfer-progress checkstyle:check

.PHONY=clean
clean:
	@cd $(ROOT_DIR) && \
$(ROOT_DIR)/mvnw -s settings.xml --no-transfer-progress clean -P-test

.PHONY=test
test: clean style
	@cd $(ROOT_DIR) && \
$(ROOT_DIR)/mvnw -s settings.xml --no-transfer-progress test

.PHONY: package
package: clean
	@cd $(ROOT_DIR) && \
$(ROOT_DIR)/mvnw -s settings.xml --no-transfer-progress package -P-test

.PHONY=build
build:
	@cd $(ROOT_DIR) && \
IMGVER="$$($(ROOT_DIR)/mvnw -q -s settings.xml -Dexec.executable=echo -Dexec.args='$${project.version}' --non-recursive exec:exec)" && \
docker build \
  --build-arg GITHUB_USERNAME=${GITHUB_USERNAME} \
  --build-arg GITHUB_PASSWORD=${GITHUB_PASSWORD} \
  -t paolodenti/${service}:$${IMGVER} \
  -f .docker/Dockerfile .
