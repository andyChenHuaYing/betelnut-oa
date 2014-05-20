#!/bin/bash

echo "Install parent module to local repository."

set MAVEN_OPTS=$MAVEN_OPTS -XX:MaxPermSize=128m

# First step clean now target, second step install parent module pom to local repository.
mvn clean install -Dmaven.test.skip=true