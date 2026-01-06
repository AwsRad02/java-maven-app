pipeline {
  agent any

  tools {
    maven 'maven-3.9'
  }

  stages {
    stage("build jar") {
      steps {
        echo "building the application..."
        sh 'mvn -B -DskipTests clean package'
      }
    }

    stage("build image") {
      steps {
        echo "building the docker image..."
        withCredentials([usernamePassword(credentialsId: 'dockerhub-rep',
                                          usernameVariable: 'USER',
                                          passwordVariable: 'PASS')]) {
          sh 'docker build -t awsradaideh/my-repo:jma-2.0 .'
          // safer than -p on CLI (doesn't show in process list)
          sh 'echo "$PASS" | docker login -u "$USER" --password-stdin'
          sh 'docker push awsradaideh/my-repo:jma-2.0'
        }
      }
    }

    stage("deploy") {
      steps {
        echo "Deploying the application..."
      }
    }
  }
}
