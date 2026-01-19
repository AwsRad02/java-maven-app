#!/usr/bin/env groovy
@Library('jenkins-shared-library')
def gv

pipeline {
    agent any

    tools {
        maven 'maven-3.9'
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
                    def dockerCmd = 'docker run -p 3080:3080 -d awsradaideh/my-repo:jma-2.0'


                    sshagent(['ec2-server-key']) {
                        sh "ssh -o StrictHostKeyChecking=no ec2-user@34.224.67.52 ${dockerCmd}"
                    }
                }
            }
        }

       
    }
}
