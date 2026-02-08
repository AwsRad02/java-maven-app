#!/usr/bin/env groovy

pipeline {
  agent any

  stages {
    stage('build app') {
      steps {
        echo "building the application..."
      }
    }

    stage('build image') {
      steps {
        echo "building the docker image..."
      }
    }

    stage('deploy') {
      environment {
        AWS_ACCESS_KEY_ID     = credentials('jenkins_aws_access_key_id')
        AWS_SECRET_ACCESS_KEY = credentials('jenkins_aws_secret_access_key')
        AWS_DEFAULT_REGION    = 'us-east-1'
        AWS_REGION            = 'us-east-1'
      }
      steps {
        echo 'deploying docker image...'

        sh '''
          set -e

          # sanity checks
          aws --version
          kubectl version --client

          # prove credentials work
          aws sts get-caller-identity

          # generate/overwrite kubeconfig with correct CA + token method
          aws eks update-kubeconfig --region us-east-1 --name demo-cluster

          # verify access
          kubectl get nodes

          # deploy
          kubectl create deployment nginx-deployment --image=nginx
        '''
      }
    }
  }
}
