  
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
        stage('Publish to Artifactory') {
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