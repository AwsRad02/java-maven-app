pipeline{
    agent any
    tools{
        maven 'Maven'
    }
    stages{
        stage("build jar"){
            steps{
                script{
                    echo "building the application..."
                    sh 'mvn package'
                }
            }
        }

         stage("build image"){
            steps{
                script{
                    echo "building the docker image..."
                    withCredentials([usernamePassword(credentialsId:'dockerhub-rep',passwordVariable:'PASS',usernameVariable:'USER')])
                    sh 'docker build -t awsradaideh/my-repo:jma-2.0 . '
                    sh "docker login -u $USER -p $PASS"
                    sh 'docker  push awsradaideh/my-repo:jma-2.0'
                }
            }
        }


        stage("deploy"){
            steps{
                script{
                    echo " Depolying the application..."
                }
            }
        }
    }
}
