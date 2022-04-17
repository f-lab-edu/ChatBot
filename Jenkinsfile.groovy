pipeline {
    agent any
    environment {
        DEPLOY_SERVER_ID = credentials('deployServerId')
        DEPLOY_SERVER_PASSWORD = credentials('deployServerPassword')
    }

    stages {
//         stage('Checkout') {
//             steps {
//                 git url: 'https://github.com/f-lab-edu/Fire-inform', branch: 'main',
//                 credentialsId: 'cbf50f14-c18f-4bc0-a792-668780641040'
//             }
//         }
//
//         stage('Build') {
//             steps {
//                 sh "./gradlew clean build"
//             }
//         }

//         stage('Transfer Jar') {
//             steps {
//                 sh 'sshpass -p $DEPLOY_SERVER_PASSWORD scp -P 12308 -o StrictHostKeyChecking=no /var/lib/jenkins/workspace/flab/build/libs/fire_inform-0.0.1-SNAPSHOT.jar $DEPLOY_SERVER_ID@106.10.59.248:build'
//             }
//         }

        stage('Connect Deploy Server') {
            steps {
                sh 'sshpass -p $DEPLOY_SERVER_PASSWORD ssh -T -p 12308 $DEPLOY_SERVER_ID@106.10.59.248'
                //sh 'pkill -9 -ef build/fire_inform-0.0.1-SNAPSHOT.jar'
                sh 'nohup java -jar build/fire_inform-0.0.1-SNAPSHOT.jar &'
            }
        }
    }
}