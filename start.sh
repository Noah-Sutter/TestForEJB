#!/usr/bin/bash

./gradlew war
docker-compose up --build -d