pipeline{
    agent any
    triggers{
        pollSCM('0-59 * * * *')
    }

    environment{
        JBOSS_CRED = credentials('MyJBossCredentials')
    }

    stages{
        stage('Build'){
            steps{
                withMaven(maven:'Maven3'){
                    echo env.GIT_BRANCH
                    echo "Builing the project ${env.GIT_BRANCH}"
                    sh 'mvn clean compile'
                }

                withCredentials([usernamePassword(credentials:'MyJBossCredentials', usernameVariable: 'USER', passwordVariable: 'PWD')]){
                    echo "Username is ${USER} and Password is ${PWD}"
                }
            }
        }

        stage('Test'){
            steps{
                withMaven(maven:'Maven3'){
                    echo 'Testing the Application'
                    echo JBOSS_CRED;
                }
            }
        }

        stage('Deploy'){
            steps{
                withMaven(maven:'Maven3'){
                 echo 'Deploying the Application'
                }
            }
        }














    }
}
