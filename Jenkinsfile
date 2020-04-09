  
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
    }
}