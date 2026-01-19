pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Fetch the repository using SSH credentials
                git branch: 'main', url: 'https://github.com/khannaquib/finalproject.git'
                sh "scp -r * ubuntu@43.204.147.221:/home/ubuntu"
            }
       
        }
        stage('creating a docker image') {
            steps {
                sshagent(['docker-server']) {
                     sh 'ssh -o StrictHostKeyChecking=no ubuntu@43.204.147.221 "docker build -t pythonapp:1 ."'
                     }
                }
        }         
    }
    
}