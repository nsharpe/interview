@echo off
call .\gradlew.bat build bootJar -x :media-player-ui:build -x :media-player-ui:bootJar

docker compose -f docker-compose.yml -f docker-compose.fixedport.yml -f docker-compose.stack.yml -f docker-compose.stack.fixedport.yml up -d --build

call .\gradlew.bat npmStart

docker compose -f docker-compose.yml -f docker-compose.stack.yml down