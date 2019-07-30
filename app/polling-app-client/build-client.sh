#!/usr/bin/env bash
##cd ..
##cd app/polling-app-client
##rm -rf build
##npm install
##npm run build
docker build -t polls-app-client:latest .
docker tag polls-app-client 10.115.8.91:5000/polls-app-client
docker push 10.115.8.91:5000/polls-app-client
