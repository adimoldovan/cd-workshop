node("build-server") {
    try {
        stage("Checkout") {
            checkout scm
        }

        stage("Build API") {
            sh 'cd ./app/polling-app-server && ls && mvn clean package -DskipTests=true'
        }

        stage("Build API image") {
            sh 'cd ./app/polling-app-server && ls && docker build -t polls-app-server-${DEPLOYMENT_ENV}:latest --no-cache .'
            sh 'docker tag polls-app-server-${DEPLOYMENT_ENV} 10.115.8.91:5000/polls-app-server-${DEPLOYMENT_ENV}'
        }

        stage("Push API image") {
            sh 'docker push 10.115.8.91:5000/polls-app-server-${DEPLOYMENT_ENV}'
        }

        stage("Build frontend") {
            sh 'if [[ ${DEPLOYMENT_ENV} == "test" ]]; then export API_PORT=8980; else export API_PORT=8981; fi; \
                cd ./app/polling-app-client && \
                ls && rm -rf build && \
                npm install && \
                export REACT_APP_SERVER_URL=http://10.115.8.91:${API_PORT}/api && \
                npm run build'
        }
        stage("Build frontend image") {
            sh 'cd ./app/polling-app-client && ls && docker build -t polls-app-client-${DEPLOYMENT_ENV}:latest --no-cache .'
            sh 'docker tag polls-app-client-${DEPLOYMENT_ENV} 10.115.8.91:5000/polls-app-client-${DEPLOYMENT_ENV}'
        }

        stage("Push frontend image") {
            sh 'docker push 10.115.8.91:5000/polls-app-client-${DEPLOYMENT_ENV}'
        }
    }
    catch (Exception e) {
        println e
        currentBuild.result = "FAILURE"
    }
}