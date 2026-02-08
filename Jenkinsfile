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
                    echo 'init'
                }
            }
        }

        stage("build jar") {
            steps {
                script {
                    echo 'building jar'

                    // sh "mvn clean package"   <-- build jar command (commented)
                }
            }
        }

        stage("build image") {
            steps {
                script {
                    echo 'building the image'

                    // sh "docker build -t myapp:latest ."  <-- docker image build (commented)
                }
            }
        }

        stage("deploy") {
            steps {
                script {

                    echo "deploying application..."

                    // Docker compose command (commented)
                    // def dockerComposeCmd = "docker compose -f docker-compose.yaml up -d"

                    // SSH agent block (commented)
                    // sshagent(['ec2-server-key']) {

                        // Copy docker-compose file to EC2 server (commented)
                        // sh "scp docker-compose.yaml ec2-user@34.224.67.52:/home/ec2-user"

                        // Run docker compose remotely on EC2 server (commented)
                        // sh "ssh -o StrictHostKeyChecking=no ec2-user@34.224.67.52 ${dockerComposeCmd}"

                    // }

                    echo "deploy stage finished (commands are commented)"
                }
            }
        }
    }
}
