pipeline {
    agent any
    stages {
        stage('Build'){
            steps {
                sh 'echo "Start building app"'
                sh 'chmod u+x gradlew'
                sh './gradlew clean build'
            }
        }
        stage('Sonar Scan'){
            steps{
                sh 'echo "Running Sonar"'
                sh './gradlew sonarqube'
            }
        }
        stage('Deploy'){
            parallel {
                stage('DeployToDevEnv'){
                    steps {
                        sh 'echo "Deploying to Dev Environment"'
                    }
                }
                stage('DeployToQAEnv'){
                    steps {
                        sh 'echo "Deploying to QA Environment" '
                    }
                }
            }
        }
        stage('Publish Report') {
            steps {
                publishHTML (target: [
                allowMissing: false,
                alwaysLinkToLastBuild: false,
                keepAll: true,
                reportDir: 'build/reports/tests/test',
                reportFiles: 'index.html',
                reportName: "MOI-project test Report"
                ])   
            }
        }
        stage('Publish Coverage') {
            steps {
                publishHTML (target: [
                allowMissing: false,
                alwaysLinkToLastBuild: false,
                keepAll: true,
                reportDir: 'build/reports/jacoco/test/html',
                reportFiles: 'index.html',
                reportName: "MOI-project test Coverage"
                ])   
            }
        }
    }
    environment {
        EMAIL_TEAM = 'dramahp13@gmail.com, jdhpp_perez@hotmail.com, nanrehd.13@gmail.com'
        EMAIL_ADMIN = 'nanrehd.13@gmail.com'
        EMAIL_ME = 'dramahp13@gmail.com'
    }
    post {
        always {
            sh 'touch build/test-results/test/*.xml'
            junit 'build/test-results/test/*.xml'
            emailext to: "${EMAIL_ADMIN}",
                 subject: "Jenkins Build ${currentBuild.currentResult} # {$env.BUILD_NUMBER}: Job ${env.JOB_NAME}",
                 body: "The pipeline: ${currentBuild.fullDisplayName} has been executed with the nest result: ${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL} \n Pipeline: ${env.BUILD_URL} has been executed."
        }
        success {
            archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
            emailext to: "${EMAIL_ME}", 
                 subject: "Jenkins build ${currentBuild.currentResult} # {$env.BUILD_NUMBER}: Job ${env.JOB_NAME}",
                 body: "The pipeline: ${currentBuild.fullDisplayName} has been executed with the nest result: ${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL} \n Pipeline: ${env.BUILD_URL} has been executed."
        }
        failure {
            emailext to: "${EMAIL_TEAM}",
                 subject: "${currentBuild.currentResult} Pipeline in ${currentBuild.fullDisplayName}",
                 body: "The pipeline: ${currentBuild.fullDisplayName} has been executed with the nest result: ${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL} \n Pipeline: ${env.BUILD_URL} has been executed."
        }
    }
}
