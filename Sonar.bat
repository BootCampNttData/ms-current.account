@echo on
mvn clean verify sonar:sonar -Dsonar.projectKey=CurrentAccount -Dsonar.host.url=http://localhost:9000 -Dsonar.login=fbed262b3fc5c28c8c07ffef39104a908211d356
cd..