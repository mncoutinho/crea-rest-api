#!groovy
// Ideal caso precisemos rodar mais de um nó e também para logs
def LABEL_ID = "rest-api-${UUID.randomUUID().toString()}"
// {
    node() {
        def REPOS
        def GIT_REPOS_URL  = 'git@gitlab.com:crearj/crea-rest-api.git'
        def GIT_BRANCH = env.BRANCH_NAME
        def ENVIRONMENT
        def DOCKER_REPO = 'registry.gitlab.com/crearj/rest-api'
        def IMAGE_NAME = "rest-api"
        def IMAGE_VERSION
        def KUBE_NAMESPACE
        def CHARTMUSEUM_URL = "http://192.168.1.25:8081"
        def HELM_REPO_NAME = 'repositorio'
        def HELM_CHART_NAME = 'rest-api'
        def HELM_DEPLOY_NAME
        def COMMIT = sh(returnStdout: true, script: 'git rev-parse HEAD')
                
        stage('Checkout') {
            echo 'Iniciando Checkout do repositório'
            echo "commit: ${COMMIT}"
            
            REPOS = checkout([$class: 'GitSCM', 
                    branches: [[name: "${GIT_BRANCH}"]], 
                    doGenerateSubmoduleConfigurations: false, 
                    extensions: [], 
                    submoduleCfg: [], 
                    userRemoteConfigs: [
                        [credentialsId: 'Gitlab',
                        //  refspec: '+refs/*:refs/remotes/origin/*', 
                         url: 'git@gitlab.com:crearj/crea-rest-api.git']]
                    ])

            if (GIT_BRANCH.equals("desenvolvimento")) {
                echo 'dev'
                KUBE_NAMESPACE = 'development'
                IMAGE_NAME = 'dev-rest-api'
                IMAGE_VERSION = COMMIT
                ENVIRONMENT = 'dev'
                HELM_DEPLOY_NAME = ENVIRONMENT + '-rest-api'
            } else if (GIT_BRANCH.contains("release")) {
                echo "prod"
                KUBE_NAMESPACE = 'prod'
                IMAGE_VERSION = GIT_BRANCH.substring(7)
                ENVIRONMENT = 'prod'
            } else {
                echo "Nao existe pipeline para a branch ${GIT_BRANCH}"
                exit 0
            }
            IMAGE_VERSION = IMAGE_VERSION.trim()
        }
        stage('Build') {
            echo 'Iniciando build do projeto'
            sh "docker build -t ${DOCKER_REPO}/${IMAGE_NAME}:${IMAGE_VERSION} --build-arg ambiente=${ENVIRONMENT} ."
            sh "docker push ${DOCKER_REPO}/${IMAGE_NAME}:${IMAGE_VERSION}"
        }
        stage('Deploy') {
            echo 'Iniciando Deploy com Helm'
            sshagent(['jenkins']) {
                def SSH_ACCESS = "ssh -o StrictHostKeyChecking=no -l root 192.168.1.25"
                // inicia o client do helm
                sh "${SSH_ACCESS} helm init --client-only"
                // cria o repositorio
                sh "${SSH_ACCESS} helm repo add ${HELM_REPO_NAME} ${CHARTMUSEUM_URL}"
                // atualiza o repositorio
                sh "${SSH_ACCESS} helm repo update"
                // faz o upgrade do deploy
                // se não, faz o deploy (install) no kubernetes a partir do chart já existente no chartmuseum                
                try {
                    sh "${SSH_ACCESS} helm upgrade --namespace=${KUBE_NAMESPACE} ${HELM_DEPLOY_NAME} ${HELM_REPO_NAME}/${HELM_CHART_NAME} --set image.tag=${IMAGE_VERSION} --set image.name=${IMAGE_NAME}"
                } catch (Exception e) {
                    sh "${SSH_ACCESS} helm install --namespace=${KUBE_NAMESPACE} --name ${HELM_DEPLOY_NAME} ${HELM_REPO_NAME}/${HELM_CHART_NAME} --set image.tag=${IMAGE_VERSION} --set image.name=${IMAGE_NAME}"
                }
            }
        }
    }
// }
