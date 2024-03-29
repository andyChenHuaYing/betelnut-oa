#!/bin/bash

echo "[Pre-Requirement] Makesure install JDK 6.0+ and set the JAVA_HOME."
echo "[Pre-Requirement] Makesure install Maven 3.0.3+ and set the PATH."
	
set MAVEN_OPTS=$MAVEN_OPTS -XX:MaxPermSize=128m

echo "[Step 1] Install all betelnut modules to local maven repository."
cd modules
mvn clean install

echo "[Step 2] Install archetype modules to local maven repository."
cd support
mvn clean install