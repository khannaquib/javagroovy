pipeline {
    agent any
    environment {
        IMAGE_NAME = "pythonapp"
        DOCKERHUB_REPO = "aquibkhan/pythonapp"
    }

    
    stages {
        stage('Checkout') {
            steps {
                // Fetch the repository using SSH credentials
                git branch: 'main', url: 'https://github.com/khannaquib/finalproject.git'
                // sh "scp -r * ubuntu@:/home/ubuntu"
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
            steps{
                sshagent(['docker-server']) {
                  sh 'ssh -o StrictHostKeyChecking=no ubuntu@35.154.113.239 "docker tag ${IMAGE_NAME} ${aquibkhan456/pythonapp}:$BUILD_ID"' 
                                        
                 withCredentials([string(credentialsId: 'aquibkhan456', variable: 'dockerpass')]) {
                sh 'ssh -o StrictHostKeyChecking=no ubuntu@35.154.113.239 "docker login -u aquibkhan456 -p ${dockerpass}"'
                sh 'ssh -o StrictHostKeyChecking=no ubuntu@35.154.113.239 "docker image push aquibkhan456/${IMAGE_NAME}:v1.$BUILD_ID"'
                sh 'ssh -o StrictHostKeyChecking=no ubuntu@35.154.113.239 "docker image push aquibkhan456/${IMAGE_NAME}:latest"'
             }
            }
        }
        
        }
                 
 }
    
}