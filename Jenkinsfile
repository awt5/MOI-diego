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
        stage('Publish to Artifactory'){
            steps {
                sh 'exit -1'
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
    }
    environment {
        EMAIL_ME = 'dramahp13@gmail.com'
    }
    post {
        always {
            junit 'build/test-results/**/*.xml'
            emailext body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL} \n Pipeline: ${env.BUILD_URL} has been executed",
                 recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                 subject: "Jenkins Build ${currentBuild.currentResult} # {$env.BUILD_NUMBER}: Job ${env.JOB_NAME}!"
        }

    }
}
