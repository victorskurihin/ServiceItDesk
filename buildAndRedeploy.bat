@ECHO OFF
CHCP 65001
REM mvn clean package glassfish:redeploy
mvn clean package dependency:copy-dependencies glassfish:redeploy
