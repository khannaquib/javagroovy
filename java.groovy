pipeline {
    agent any

    environment {
        IMAGE_NAME = "pythonapp"
        DOCKERHUB_REPO = "aquibkhan456/pythonapp"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/khannaquib/finalproject.git'
            }
        }

        stage('creating a docker image') {
            steps {
                sshagent(['docker-server']) {
                    sh 'ssh -o StrictHostKeyChecking=no ubuntu@35.154.113.239 "docker build -t pythonapp ."'
                }
            }
        }

        stage('Docker Hub Login') {
            steps {
                sshagent(['docker-server']) {

                    sh 'ssh -o StrictHostKeyChecking=no ubuntu@35.154.113.239 "docker tag ${IMAGE_NAME} ${DOCKERHUB_REPO}:$BUILD_ID"'

                    withCredentials([
                        string(credentialsId: 'aquibkhan456', variable: 'dockerpass')
                    ]) {
                        sh 'ssh -o StrictHostKeyChecking=no ubuntu@35.154.113.239 "docker login -u aquibkhan456 -p ${dockerpass}"'
                        sh 'ssh -o StrictHostKeyChecking=no ubuntu@35.154.113.239 "docker image push ${DOCKERHUB_REPO}:$BUILD_ID"'
                        sh 'ssh -o StrictHostKeyChecking=no ubuntu@35.154.113.239 "docker image push ${DOCKERHUB_REPO}:latest"'
                    }
                }
            }
        }
    }
}
