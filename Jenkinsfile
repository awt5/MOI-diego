  
pipeline {
    agent any
    stages {
        stage('Build'){
            steps {
                sh 'echo "Start building app"'
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
}