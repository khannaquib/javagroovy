pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Fetch the repository using SSH credentials
                git branch: 'main', url: 'https://github.com/khannaquib/finalproject.git'
            }
       
        }
        stage('List Remote Files') {
            steps {
                sshagent(['docker-server']) {
                     sh 'ssh -o StrictHostKeyChecking=no ubuntu@13.232.181.188 "ls -la ~/"'
                     }
                }
        }         
    }
    
}