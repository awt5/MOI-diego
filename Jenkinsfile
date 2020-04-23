pipeline {
    agent any
    environment {
        EMAIL_TEAM = 'dramahp13@gmail.com, jdhpp_perez@hotmail.com, nanrehd.13@gmail.com'
        EMAIL_ADMIN = 'nanrehd.13@gmail.com'
        PROJECT_NAME = 'moi-app'
        PROJECT_VERSION = '1.1'
        DOCKER_CREDS = 'docker_id'
        DOCKER_HUB_IMAGE = 'jdiego13/${PROJECT_NAME}'
        BUILD_VERSION = '1.0.$BUILD_NUMBER'
    }
    stages {
        stage('Build'){
            steps {
                sh 'echo "Start building app"'
                sh 'chmod u+x gradlew'
                //sh 'exit -1'
                sh './gradlew clean build'  
            }
            post {
                always {
                    sh 'touch build/test-results/test/*.xml'
                    junit 'build/test-results/test/*.xml'
                }
                success {
                    publishHTML (target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'build/reports/tests/test',
                    reportFiles: 'index.html',
                    reportName: "MOI-app test Report"
                    ])
                    publishHTML (target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'build/reports/jacoco/test/html',
                    reportFiles: 'index.html',
                    reportName: "MOI-app test Coverage"
                    ])
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                }
            }
        }
        stage('Unit Tests and Code Analysis'){
            steps{
                sh 'echo "Running Unit Tests"'
                sh './gradlew jacocoTestReport'

                sh 'echo "Analyzing Code"'
                sh './gradlew sonarqube'
            }
        }
        stage('Deploy To Dev Environment'){
            environment{
                APP_PORT=9092
                DB_PORT=3307
                DEV_HOME='/deployments/dev'
            }
            when {
                anyOf{
                    branch 'develop'
                    branch 'master'
                }
            }
            steps {
                sh 'echo "Deploying to Dev Environment"'
                sh 'cp docker-compose.yaml $DEV_HOME/'
                sh 'cd $DEV_HOME'
                sh 'docker-compose down -v'
                sh 'docker-compose up -d --build'
            }
        }
        stage('Publish to Artifactory'){
            parallel {
                stage('For a snapshot'){
                    when{
                        branch 'develop'
                    }
                    steps{
                        sh './gradlew artifactoryPublish'
                    }
                }
                stage('For a release'){
                    when{
                        branch 'master'
                    }         
                    steps{
                        sh './gradlew -Partifactory_repokey=libs-release-local artifactoryPublish'
                    }
                }
            }
        }
        stage('Publish To Docker Hub'){ 
            stages {
                stage('Publish develop'){
                    when {
                        branch 'develop'
                    }
                    steps{
                        withDockerRegistry([ credentialsId: "${DOCKER_CREDS}", url: "https://index.docker.io/v1/" ]) {
                            sh 'docker tag ${PROJECT_NAME}:latest ${DOCKER_HUB_IMAGE}:${BUILD_VERSION}'
                            sh 'docker push ${DOCKER_HUB_IMAGE}'
                        }
                    }
                }
                stage('Publish release'){
                    when {
                        branch 'master'
                    }
                    steps{
                        withDockerRegistry([ credentialsId: "${DOCKER_CREDS}", url: "https://index.docker.io/v1/" ]) {
                            sh 'docker tag ${PROJECT_NAME}:latest ${DOCKER_HUB_IMAGE}:${PROJECT_VERSION}'
                            sh 'docker push ${DOCKER_HUB_IMAGE}'
                        }
                    }
                }
            }
        }
        stage('Promote To QA Environment'){
            environment{
                APP_PORT=9093
                DB_PORT=3308
                QA_HOME='/deployments/qa'
            }
            when {
                branch 'develop'
            }
            steps {
                sh 'echo "Deploying to QA Environment"'
                sh 'cp docker-compose.yaml $QA_HOME/'
                sh 'cd $QA_HOME'
                sh 'docker-compose down -v'
                sh 'docker-compose up -d --build'
            }
        }
        stage('Workspace clean up'){
            environment{
                APP_PORT=9092
                DB_PORT=3307
            }
            steps{
                sh 'docker-compose down -v'
                //sh 'docker rmi $(docker images -aq -f dangling=true)'
                sh 'docker image prune -f'
                cleanWs()
            }
        }
    }
    post {
        always {
            emailext to: "${EMAIL_ADMIN}",
                 subject: "Jenkins Build ${currentBuild.currentResult} # {$env.BUILD_NUMBER}: Job ${env.JOB_NAME}",
                 body: "The pipeline: ${currentBuild.fullDisplayName} has been executed with the next result: ${currentBuild.currentResult}"
        }
        failure {
            emailext to: "${EMAIL_TEAM}",
                 subject: "[${currentBuild.currentResult}] Pipeline in ${currentBuild.fullDisplayName}",
                 body: "The pipeline: ${currentBuild.fullDisplayName} has been executed with the next result: ${currentBuild.currentResult} Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL} \n Pipeline: ${env.BUILD_URL} has been executed.",
                 attachLog: true
        }
    }
}
