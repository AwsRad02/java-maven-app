def buildJar(){
    echo "building the application..."
    sh 'mvn package'
}

def testApp(){
    echo ' testing the application...'
}


return this 

