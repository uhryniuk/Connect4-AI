#!/bin/sh
echo Running Build...
# mvn package 
# mvn clean install
# mvn clean dependency:copy-dependencies package
# mvn clean package
mvn clean compile assembly:single

echo Running Server...
# java -jar "./target/connect4-backend-1.0-SNAPSHOT.jar" 
java -jar "./target/connect4-backend-1.0-SNAPSHOT-jar-with-dependencies.jar" 