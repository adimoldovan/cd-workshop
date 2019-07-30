#!/usr/bin/env bash

cd ./app/polling-app-client && \
ls && \
rm -rf build && \
npm install && \
export REACT_APP_SERVER_URL=http://polls-app-server-${DEPLOYMENT_ENV}:8080/api && \
echo '++ Build client app' && \
echo ${REACT_APP_SERVER_URL} && \
npm run build