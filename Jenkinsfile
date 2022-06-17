pipeline{
    agent any
    triggers{
        pollSCM('0-59 * * * *')
    }

    stages{
        stage('Build'){
            steps{
                withMaven(maven:'Maven3'){
                    echo env.GIT_BRANCH
                    echo "Builing the project ${env.GIT_BRANCH}"
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Test'){
            steps{
                withMaven(maven:'Maven3'){
                    echo 'Testing the Application'

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
