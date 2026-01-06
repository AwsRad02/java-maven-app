def buildJar(){
    echo "building the application..."
    sh 'mvn package'
}

def buildImage(){
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId:'dockerhub-rep',passwordVariable:'PASS',usernameVariable:'USER')])
    sh 'docker build -t awsradaideh/my-repo:jma-2.0 . '
    sh "docker login -u $USER -p $PASS"
    sh 'docker  push awsradaideh/my-repo:jma-2.0'
}


return this 

