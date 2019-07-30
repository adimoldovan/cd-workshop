#!/usr/bin/env bash

if [[ ${DEPLOYMENT_ENV} == "test" ]]; then
    export API_PORT=8980
else
    export API_PORT=8981
fi
echo ${API_PORT}