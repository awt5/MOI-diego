pipeline {
    agent any
    environment {
        EMAIL_TEAM = 'dramahp13@gmail.com, jdhpp_perez@hotmail.com, nanrehd.13@gmail.com'
        EMAIL_ADMIN = 'nanrehd.13@gmail.com'
        PROJECT_NAME = 'moi-app'
        DOCKER_CREDS = 'docker_id'
        USER_DOCKER_HUB = 'jdiego13'
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
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                }
            }
        }
        stage('Sonar Scan'){
            steps{
                sh 'echo "Running Sonar"'
                sh './gradlew sonarqube'
            }
        }
        stage('Deploy To Dev Environment'){
            environment{
                APP_PORT=9092
                DB_PORT=3307
                //DEV_HOME='~/awt05/deployments/dev'
            }
            when {
                anyOf{
                    branch 'develop'
                    branch 'master'
                }
            }
            steps {
                sh 'echo "Deploying to Dev Environment"'
                sh 'docker-compose down -v'
                sh 'docker-compose config'
                sh 'docker-compose build'
                sh 'docker-compose up -d'
            }
        }
        stage('Publish to Artifactory'){
            parallel {
                stage('For a snapshot'){
                    when{
                        branch 'develop'
                    }
                    steps{
                        sh './gradlew -Partifactory_repokey=libs-snapshot-local artifactoryPublish'
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
            when {
                branch 'develop'
            }
            steps{
                withDockerRegistry([ credentialsId: "${DOCKER_CREDS}", url: "https://index.docker.io/v1/" ]) {
                    sh 'docker tag ${PROJECT_NAME}:latest ${USER_DOCKER_HUB}/${PROJECT_NAME}:v1.0-$BUILD_NUMBER'
                    sh 'docker push ${USER_DOCKER_HUB}/${PROJECT_NAME}'
                }
            }
        }
        stage('Deploy To QA Environment'){
            environment{
                APP_PORT=9093
                DB_PORT=3308
                QA_HOME='~/awt05/deployments/qa'
            }
            when {
                branch 'develop'
            }
            steps {
                sh 'echo "Deploying to QA Environment"'
                sh 'ls -l $QA_HOME'
                sh 'cp docker-compose.yaml $QA_HOME/'
                sh 'cd $QA_HOME'
                sh 'docker-compose down -v'
                sh 'docker-compose config'
                sh 'docker-compose build'
                sh 'docker-compose up -d'
            }
        }
        stage('Workspace clean up'){
            environment{
                APP_PORT=9092
                DB_PORT=3307
            }
            steps{
                sh 'docker-compose down -v'
                sh 'docker rmi $(docker images -aq -f dangling=true)'
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
