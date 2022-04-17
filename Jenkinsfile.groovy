pipeline {
//     stage('Git clone') {
//       steps {
//         git credentialsId: 'cbf50f14-c18f-4bc0-a792-668780641040',
//         url: 'https://github.com/f-lab-edu/Fire-inform'
//       }
//     }
//     stage('Build') {
//         sh "./gradlew clean build"
//     }

    agent any
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/f-lab-edu/Fire-inform', branch: 'main',
                credentialsId: 'cbf50f14-c18f-4bc0-a792-668780641040'
            }
        }

        stage('Build') {
            steps {
                sh "./gradlew clean build"
            }
        }

        stage('Transfer') {
            steps {
                sh "sshpass -p Jhd680110! scp -P 12308 /var/lib/jenkins/workspace/flab/build/libs/fire_inform-0.0.1-SNAPSHOT.jar root@106.10.59.248:build -debug"
            }
        }

//         stage('Connect Deploy Server') {
//             steps {
//                 sh "ssh -p 12308 root@106.10.59.248"
//             }
//         }
    }
}