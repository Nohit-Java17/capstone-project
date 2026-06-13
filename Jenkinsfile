pipeline {
    agent any
    
    environment {
        // Cấu hình gửi thông báo Telegram từ credential Jenkins.
        TOKEN = credentials('telegram_token')
        CHAT_ID = credentials('telegram_chatid')

        // Nội dung thông báo mô tả commit đang được build/deploy.
        GIT_MESSAGE = sh(returnStdout: true, script: "git log -n 1 --format=%s ${GIT_COMMIT}").trim()
        GIT_AUTHOR = sh(returnStdout: true, script: "git log -n 1 --format=%ae ${GIT_COMMIT}").trim()
        GIT_COMMIT_SHORT = sh(returnStdout: true, script: "git rev-parse --short ${GIT_COMMIT}").trim()
        GIT_INFO = "Branch: ${GIT_BRANCH}\nLast Message: ${GIT_MESSAGE}\nAuthor: ${GIT_AUTHOR}\nCommit: ${GIT_COMMIT_SHORT}"
        TEXT_BREAK = '----------------------------------------'
        TEXT_PRE = "${TEXT_BREAK}\n${GIT_INFO}"
        TEXT_BUILD = "${JOB_NAME} is Building"
        TEXT_PUSH = "${JOB_NAME} is Pushing"
        TEXT_CLEAN = "${JOB_NAME} is Cleaning"
        TEXT_RUN = "${JOB_NAME} is Running"

        // Trạng thái cuối pipeline gửi về Telegram.
        TEXT_SUCCESS_BUILD = "${JOB_NAME} is Success"
        TEXT_FAILURE_BUILD = "${JOB_NAME} is Failure"
    }

    stages {
        stage('Build') {
            steps {
                sh "curl --location --request POST 'https://api.telegram.org/bot${TOKEN}/sendMessage' --form text='${TEXT_PRE}' --form chat_id='${CHAT_ID}'"
                sh "curl --location --request POST 'https://api.telegram.org/bot${TOKEN}/sendMessage' --form text='${TEXT_BUILD}' --form chat_id='${CHAT_ID}'"
                sh 'docker build -t yamiannephilim/ecommerce:latest .'
            }
        }

        stage('Push') {
            steps {
                sh "curl --location --request POST 'https://api.telegram.org/bot${TOKEN}/sendMessage' --form text='${TEXT_PUSH}' --form chat_id='${CHAT_ID}'"

                withDockerRegistry(credentialsId: 'docker_hub', url: 'https://index.docker.io/v1/') {
                    sh 'docker push yamiannephilim/ecommerce'
                }
            }
        }

        stage('Clean') {
            steps {
                sh "curl --location --request POST 'https://api.telegram.org/bot${TOKEN}/sendMessage' --form text='${TEXT_CLEAN}' --form chat_id='${CHAT_ID}'"

                script {
                    def containerId = sh(returnStdout: true, script: 'docker ps -aqf "name=ecommerce"').trim()
                    if (containerId) {
                        sh "docker stop $containerId"
                        sh "docker rm $containerId"
                    }
                }
            }
        }

        stage('Run') {
            steps {
                sh "curl --location --request POST 'https://api.telegram.org/bot${TOKEN}/sendMessage' --form text='${TEXT_RUN}' --form chat_id='${CHAT_ID}'"
                sh 'docker rm -f ecommerce || echo "this container does not exist"'
                sh 'docker network create yan || echo "this network exist"'
                sh 'docker container prune -f'
                sh 'docker run --name ecommerce --network yan --restart=unless-stopped -d yamiannephilim/ecommerce:latest'
            }
        }
    }

    post {
        always {
            cleanWs()
        }

        success {
            script {
                sh "curl --location --request POST 'https://api.telegram.org/bot${TOKEN}/sendMessage' --form text='${TEXT_SUCCESS_BUILD}' --form chat_id='${CHAT_ID}'"
            }
        }

        failure {
            script {
                sh "curl --location --request POST 'https://api.telegram.org/bot${TOKEN}/sendMessage' --form text='${TEXT_FAILURE_BUILD}' --form chat_id='${CHAT_ID}'"
            }
        }
    }
}
