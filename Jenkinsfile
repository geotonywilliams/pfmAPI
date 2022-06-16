pipeline{
    agent any

    stages{
        stage('Build'){
            steps{
                withMaven(maven:'Maven3'){
                    echo branch
                    sh 'mvn clean compile'
                }
            }
        }



        stage('Test'){
            steps{
                withMaven(maven:'Maven3'){
                    echo 'Testing the Application'
                    sh 'mvn clean compile'
                }
            }
        }



        stage('Deploy'){
            steps{
                withMaven(maven:'Maven3'){
                    sh 'mvn clean compile'
                }
            }
        }








    }
}
