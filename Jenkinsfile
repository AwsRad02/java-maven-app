#!/usr/bin/env groovy


pipeline {
    agent any

    tools {
        maven 'maven'
    }

    stages {

        stage("init") {
            steps {
                script {
                    
                   echo 'inint'
                }
            }
        }

        stage("build jar") {
            steps {
                script {
                    echo 'building jar'
                }
            }
        }

        stage("build image") {
            steps {
                script {
                    echo 'building the image' 
                }
            }
        }
          stage("deploy") {
            steps {
                script {
                   def dockerComposeCmd="docker-compose -f docker-compose.yaml up "
                    sshagent(['ec2-server-key']) {
                        sh "scp docker-compose.yaml ec2-user@34.224.67.52:/home/ec2-user"
                        sh "ssh -o StrictHostKeyChecking=no ec2-user@34.224.67.52  ${dockerComposeCmd}"
                    }
                }
            }
        }

       
    }
}
