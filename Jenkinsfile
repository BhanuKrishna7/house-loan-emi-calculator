pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps { git url: 'https://your.git.repo/houseloan-emi-calculator.git', branch: 'main' }
    }
    stage('Build') {
      steps { sh 'mvn clean package -DskipTests' }
      post { success { archiveArtifacts artifacts: 'target/*.war', fingerprint: true } }
    }
    stage('Deploy to Tomcat') {
      steps {
        // Requires Jenkins credentials and Tomcat manager plugin or script
        // Example using curl to Tomcat manager (replace variables)
        sh "curl --upload-file target/houseloan-emi-calculator.war \"http://$TOMCAT_USER:$TOMCAT_PASS@${TOMCAT_HOST}:${TOMCAT_PORT}/manager/text/deploy?path=/houseloan-emi-calculator&update=true\""
      }
    }
  }
}