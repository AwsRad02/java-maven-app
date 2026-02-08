#!/usr/bin/env groovy

pipeline {
  agent any

  tools {
    maven 'maven'
  }

  environment {
    AWS_REGION    = 'us-east-1'
    CLUSTER_NAME  = 'demo-cluster'

    // ECR registry + repo
    ECR_REGISTRY  = '299482272529.dkr.ecr.us-east-1.amazonaws.com'
    ECR_REPO      = 'java-mvn-app'
    IMAGE_REPO    = "${ECR_REGISTRY}/${ECR_REPO}"

    // Image tag
    IMAGE_NAME    = "${BUILD_NUMBER}"

    APP_NAME      = 'java-maven-app'

    // kubeconfig location
    KUBECONFIG    = "${WORKSPACE}/kubeconfig"
  }

  stages {

    stage('build app') {
      steps {
        sh 'mvn -B clean package'
      }
    }

    stage('build & push image') {

      environment {
        AWS_ACCESS_KEY_ID     = credentials('jenkins_aws_access_key_id')
        AWS_SECRET_ACCESS_KEY = credentials('jenkins_aws_secret_access_key')
      }

      steps {
        sh '''
          set -e

          echo "Checking AWS identity..."
          aws sts get-caller-identity

          echo "Logging in to ECR..."
          aws ecr get-login-password --region "$AWS_REGION" | \
            docker login --username AWS --password-stdin "$ECR_REGISTRY"

          echo "Building Docker image..."
          docker build -t "$IMAGE_REPO:$IMAGE_NAME" .

          echo "Pushing image to ECR..."
          docker push "$IMAGE_REPO:$IMAGE_NAME"
        '''
      }
    }

    stage('deploy') {

      environment {
        AWS_ACCESS_KEY_ID     = credentials('jenkins_aws_access_key_id')
        AWS_SECRET_ACCESS_KEY = credentials('jenkins_aws_secret_access_key')
      }

      steps {
        sh '''
          set -e

          echo "Updating kubeconfig..."
          aws eks update-kubeconfig \
            --region "$AWS_REGION" \
            --name "$CLUSTER_NAME" \
            --kubeconfig "$KUBECONFIG"

          echo "Deploying to Kubernetes..."
          export APP_NAME IMAGE_REPO IMAGE_NAME

          envsubst < kubernetes/deployment.yaml | kubectl --kubeconfig "$KUBECONFIG" apply -f -
          envsubst < kubernetes/service.yaml    | kubectl --kubeconfig "$KUBECONFIG" apply -f -
