#!/usr/bin/env groovy

pipeline {
  agent any

  tools {
    maven 'maven'
  }

  environment {
    AWS_REGION    = 'us-east-1'
    CLUSTER_NAME  = 'demo-cluster'

    // Your ECR registry + repo
    ECR_REGISTRY  = '299482272529.dkr.ecr.us-east-1.amazonaws.com'
    ECR_REPO      = 'java-mvn-app'
    IMAGE_REPO    = "${ECR_REGISTRY}/${ECR_REPO}"

    // Image tag (fixes your MissingPropertyException)
    IMAGE_NAME    = "${BUILD_NUMBER}"

    APP_NAME      = 'java-maven-app'

    // Make kubeconfig deterministic in CI
    KUBECONFIG    = "${WORKSPACE}/kubeconfig"
  }

  stages {

    stage('build app') {
      steps {
        sh 'mvn -B clean package'
      }
    }

    stage('build & push image') {
      steps {
        withCredentials([[
          $class: 'AmazonWebServicesCredentialsBinding',
          credentialsId: 'aws-jenkins'
        ]]) {
          sh '''
            set -e

            aws sts get-caller-identity

            aws ecr get-login-password --region "$AWS_REGION" | \
              docker login --username AWS --password-stdin "$ECR_REGISTRY"

            docker build -t "$IMAGE_REPO:$IMAGE_NAME" .
            docker push "$IMAGE_REPO:$IMAGE_NAME"
          '''
        }
      }
    }

    stage('deploy') {
      steps {
        withCredentials([[
          $class: 'AmazonWebServicesCredentialsBinding',
          credentialsId: 'aws-jenkins'
        ]]) {
          sh '''
            set -e

            aws sts get-caller-identity

            aws eks update-kubeconfig \
              --region "$AWS_REGION" \
              --name "$CLUSTER_NAME" \
              --kubeconfig "$KUBECONFIG"

            # envsubst uses these vars in your YAML ($APP_NAME, $IMAGE_REPO, $IMAGE_NAME)
            export APP_NAME IMAGE_REPO IMAGE_NAME

            envsubst < kubernetes/deployment.yaml | kubectl --kubeconfig "$KUBECONFIG" apply -f -
            envsubst < kubernetes/service.yaml    | kubectl --kubeconfig "$KUBECONFIG" apply -f -

            kubectl --kubeconfig "$KUBECONFIG" rollout status deployment/"$APP_NAME" --timeout=120s
          '''
        }
      }
    }
  }
}
