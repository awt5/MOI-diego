  
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'echo "Start building app"'
            }
        }
        stage('Tests') {
            steps {
                sh 'echo "Running Tests"'
                sh 'java -version'
            }
        }
        stage('Deploy'){
            parallel {
                stage('DeployToDevEnv'){
                    step {
                        sh 'echo "Deploying to Dev Environment"'
                    }
                }
                stage('DeployToQAEnv'){
                    step {
                        sh 'echo "Deploying to QA Environment" '
                    }
                }
            }
        }
    }
}