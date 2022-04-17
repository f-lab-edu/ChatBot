node {
    stage('Git clone') {
      steps {
        git credentialsId: 'cbf50f14-c18f-4bc0-a792-668780641040',
        url: 'https://github.com/f-lab-edu/Fire-inform'
      }
    }
    stage('Build') {
        sh "./gradlew clean build"
    }
}