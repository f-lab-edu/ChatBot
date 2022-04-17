node {
//     stage('Git clone') {
//       steps {
//         git credentialsId: 'cbf50f14-c18f-4bc0-a792-668780641040',
//         url: 'https://github.com/f-lab-edu/Fire-inform'
//       }
//     }
//     stage('Build') {
//         sh "./gradlew clean build"
//     }

    stage('Ready') {
        echo "Ready"
    }

    stage('Build') {
        echo "Build"
    }

    stage('Transfer') {
        echo "Transfer"
    }

    stage('Run') {
        echo "Run"
    }
}