pipeline{
    agent any
    triggers{
        pollSCM('0-59 * * * *')
    }

    tools{
    maven 'Maven3'
    jdk 'JDK9'
    }

    stages{
        stage('Build'){
            steps{
                    echo "Building the project ${env.GIT_BRANCH}"
                    sh 'mvn clean compile'
            }
        }

        stage('Test'){
            steps{
                    echo 'Testing the Application'
            }
        }

        stage('Deploy'){
            steps{
                 echo 'Deploying the Application'
                withCredentials([usernamePassword(credentialsId:'MyJBossCredentials', usernameVariable: 'USER', passwordVariable: 'PWD')]){
                    sh 'jboss-cli.sh --connect --controller=localhost:9995 --user=admin --password=Temp123$ --command="deploy C:/ProgramData/Jenkins/.jenkins/workspace/pfmAPI/target/pfmAPI2.war --name=pfmAPI --runtime-name=pfmAPI.war --force"'
                }
            }
        }












    }
}
