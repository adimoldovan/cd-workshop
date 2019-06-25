#!/usr/bin/env bash

docker-compose down

cd polling-app-server
mvn clean package -DskipTests=true
cd ..
cd polling-app-client
rm -rf build
npm install
npm run build
cd ..

docker-compose up --build --force-recreate -d