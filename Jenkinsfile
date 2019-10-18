pipeline {
    agent { docker { image 'gradle:5.6.2-jdk8' } }
    stages {
        stage('build') {
            steps {
                sh ' ./gradlew test'
            }
        }
    }
}