pipeline {
    agent any
    stage('Front clone'){
        steps{
            git branch: 'FE', credentialsId: 'GitLab-Last', url: 'https://lab.ssafy.com/s10-final/S10P31D203.git'
        }
    }

    stage('Docker front image build') {
        steps {
            dir('S10P31D203/whoru-frontend') {
                sh "docker stop frontend || true && docker rm frontend || true"
                sh 'docker rmi frontend || true'
                sh 'docker build -t frontend .'
            }
        }
    }

    stage('Docker front container run') {
        steps {
            dir('S10P31D203/whoru-frontend') {
                sh 'docker run -it -d -p 5173:80 --name frontend frontend'
            }
        }
    }
    stage('Docker network connect') {
        steps {
            dir('S10P31D203/whoru-frontend') {
                sh 'docker network connect whoru frontend'
            }
        }
    }

}
