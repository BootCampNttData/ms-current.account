@echo on
mvn clean verify sonar:sonar -Dsonar.projectKey=CurrentAccount -Dsonar.host.url=http://20.120.185.62:9000 -Dsonar.login=1be1d5296d3ffa45b95e95df5107fcbf93092322
cd..