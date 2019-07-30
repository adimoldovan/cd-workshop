node("deploy-server") {
    try {
        stage("Checkout") {
            checkout scm
        }
        stage("Stop applications") {
            sh 'cd ./jenkins/${DEPLOYMENT_ENV} && ls && docker-compose -f deploy-${DEPLOYMENT_ENV}.yml down'
        }
        stage("Start applications") {
            sh 'cd ./jenkins/${DEPLOYMENT_ENV} && ls && docker-compose -f deploy-${DEPLOYMENT_ENV}.yml pull && docker-compose -f deploy-${DEPLOYMENT_ENV}.yml up --remove-orphans --force-recreate -d'
        }
        stage("Clean up old images") {
            sh 'docker rmi $(docker images -a | grep "polls-app-.*none" | awk \'{print $3}\') || true'
        }
    }
    catch (Exception e) {
        println e
        currentBuild.result = "FAILURE"
    }
}
