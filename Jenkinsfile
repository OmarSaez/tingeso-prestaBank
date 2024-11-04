pipeline {
    agent any

    environment {
        GITHUB_CREDENTIALS_ID = 'github_credentials' // ID de tus credenciales de GitHub
        DOCKER_CREDENTIALS_ID = 'docker_hub' // ID de tus credenciales de Docker Hub
        REPO_URL = 'https://github.com/OmarSaez/tingeso-prestaBank'
        BACKEND_IMAGE = 'omarsaez/prestabank-backend:latest'
        FRONTEND_IMAGE = 'omarsaez/prestabank-frontend:latest'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: "${REPO_URL}", credentialsId: "${GITHUB_CREDENTIALS_ID}"
            }
        }

        stage('Run Tests') {
            steps {
                dir('Backend/prestabank-backend') {
                    sh 'chmod +x mvnw && ./mvnw test'
                }
            }
        }


        stage('Build Docker Images') {
            steps {
                script {
                    // Construir imágenes Docker usando rutas específicas
                    sh "docker build -t ${BACKEND_IMAGE} -f Backend/prestabank-backend/Dockerfile Backend/prestabank-backend"
                    sh "docker build -t ${FRONTEND_IMAGE} -f Frontend/prestabank-frontend/Dockerfile Frontend/prestabank-frontend"
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                    script {
                        // Iniciar sesión en Docker Hub y subir imágenes
                        sh "echo '${DOCKER_HUB_PASSWORD}' | docker login -u '${DOCKER_HUB_USERNAME}' --password-stdin"
                        sh "docker push ${BACKEND_IMAGE}"
                        sh "docker push ${FRONTEND_IMAGE}"
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
