pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'your-docker-repo/spring-project:latest' // Customize if you're using Docker for deployment
        // DEPLOY_SERVER = 'remote-user@your-server-ip'  // SSH user and server IP
        // DEPLOY_PATH = '/path/to/deployment/folder/'   // Path on the remote server
        DEPLOY_PATH = '/home/ubuntu/aisamdev'   // Path on the remote server
        JAR_FILE = 'aisamdev_java.jar'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/aisam94/aisamdev_java.git'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        // stage('Docker Build & Push') {
        //     when {
        //         expression { return env.BRANCH_NAME == 'main' }
        //     }
        //     steps {
        //         script {
        //             docker.build(DOCKER_IMAGE).push() // Customize Docker repo details
        //         }
        //     }
        // }

        stage('Deploy') {
            steps {
                // script {
                //     // Example of running a docker container for deployment
                //     // sh 'docker stop spring-app || true && docker rm spring-app || true'
                //     // sh 'docker run -d --name spring-app -p 8080:8080 your-docker-repo/spring-project:latest'
                // }

                // Stop the currently running app, if any
                script {
                    def appRunning = sh(script: "ps -ef | grep '${JAR_FILE}' | grep -v grep || true", returnStdout: true).trim()
                    if (appRunning) {
                        echo 'Stopping the current instance of the application'
                        sh "pkill -f '${JAR_FILE}' || true"
                    } else {
                        echo 'No running instance found, continuing with the deployment...'
                    }
                }

                // Copy the generated jar to the deployment directory
                // sh "cp target/*.jar ${DEPLOY_PATH}/${JAR_FILE}"
                sh "whoami"
                sh "cp target/*.jar ${DEPLOY_PATH}"

                // Run the new version of the application
                sh '''
                nohup java -jar ${DEPLOY_PATH}/${JAR_FILE} > ${DEPLOY_PATH}/app.log 2>&1 &
                '''
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        // success {
        //     mail to: 'pulldtrigger94@gmail.com',
        //          subject: "Successful Pipeline: ${currentBuild.fullDisplayName}",
        //          body: "Build successful. Check Jenkins console output."
        // }
        // failure {
        //     mail to: 'pulldtrigger94@gmail.com',
        //          subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
        //          body: "Build failed. Check Jenkins console output."
        // }
    }
}
