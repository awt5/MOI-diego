pipeline {
    agent any
    environment {
        EMAIL_TEAM = 'dramahp13@gmail.com, jdhpp_perez@hotmail.com, nanrehd.13@gmail.com'
        EMAIL_ADMIN = 'nanrehd.13@gmail.com'
    }
    stages {
        stage('Build'){
            steps {
                sh 'echo "Start building app"'
                sh 'chmod u+x gradlew'
                sh './gradlew clean build'  
            }
            post {
                success {
                    publishHTML (target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'build/reports/tests/test',
                    reportFiles: 'index.html',
                    reportName: "MOI-project test Report"
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
            steps {
                sh 'echo "Deploying to Dev Environment"'
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
        stage('Deploy To QA Environment'){
            steps {
                sh 'echo "Deploying to QA Environment"'
            }
        }
        stage('Workspace clean up'){
            steps{
                sh 'docker-compose down -v'
                sh 'docker rmi $(docker images -aq -f dangling=true)'
                cleanWs()
            }
        }
    }
    post {
        always {
            sh 'touch build/test-results/test/*.xml'
            junit 'build/test-results/test/*.xml'
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
