node("build-server") {
    try {
        stage("Build") {
            build job: 'Pipelines/build-pipeline', parameters: [[$class: 'StringParameterValue', name: 'DEPLOYMENT_ENV', value: "test"]]
            build job: 'Pipelines/build-pipeline', parameters: [[$class: 'StringParameterValue', name: 'DEPLOYMENT_ENV', value: "prod"]]
        }
        stage("Deploy in Test") {
            build job: 'Pipelines/deploy-pipeline', parameters: [[$class: 'StringParameterValue', name: 'DEPLOYMENT_ENV', value: "test"]]
        }
        stage("Test in Test") {
            build job: 'Pipelines/test-pipeline', parameters: [[$class: 'StringParameterValue', name: 'DEPLOYMENT_ENV', value: "test"]]
        }
        stage("Deploy in Prod") {
            build job: 'Pipelines/deploy-pipeline', parameters: [[$class: 'StringParameterValue', name: 'DEPLOYMENT_ENV', value: "prod"]]
        }
//        stage("Test in Prod") {
//            build job: 'Pipelines/test-pipeline', parameters: [[$class: 'StringParameterValue', name: 'DEPLOYMENT_ENV', value: "prod"]]
//        }
    } catch (Exception e) {
        println e
        currentBuild.result = "FAILURE"
    }
}
