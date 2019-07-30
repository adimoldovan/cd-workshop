node("build-server") {
    stage('Prepare test data') {
        build job: '../Test/Test_data', parameters: [[$class: 'StringParameterValue', name: 'DEPLOYMENT_ENV', value: "${DEPLOYMENT_ENV}"]]
    }
    stage('API tests') {
        build job: '../Test/API_tests', parameters: [[$class: 'StringParameterValue', name: 'DEPLOYMENT_ENV', value: "${DEPLOYMENT_ENV}"]]
    }
    stage("GUI tests") {
        build job: '../Test/GUI_tests', parameters: [[$class: 'StringParameterValue', name: 'DEPLOYMENT_ENV', value: "${DEPLOYMENT_ENV}"]]
    }
}
