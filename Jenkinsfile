pipeline {
  environment {
    imageName = '/tesis/grupo-de-investigadores'     //path project gitlab
    gitlabCredential = 'gitlab-deployment-user-access-token'
    argoCDFolderApp = 'ggi'     //path argocd
    dockerImage = ''
  }

  agent {
    kubernetes {
      yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: dind
            image: docker:19.03.1-dind
            securityContext:
              privileged: true
            env:
              - name: DOCKER_TLS_CERTDIR
                value: ""
          - name: kustomize
            image: k8s.gcr.io/kustomize/kustomize:v4.1.3
            command:
            - cat
            tty: true
          - name: gradle
            image: gradle:jdk17-alpine
            command:
            - cat
            resources:
              requests:
                memory: "512Mi"
                cpu: "2"
              limits:
                memory: "4Gi"
                cpu: "4"
            tty: true
            volumeMounts:
            - name: pvol
              mountPath: /root/.m2
          volumes:
          - name: pvol
            persistentVolumeClaim:
              claimName: "jenkins-maven-m2"
        '''
    }
  }
  stages {
     stage('Build project') {
      when {
        expression { BRANCH_NAME ==~ /(master|develop)/ }
      }
      steps {
        container('gradle') {
          echo 'Building project..'
          sh 'gradle clean build -x test'
        }
      }
    }
    stage('Build docker image') {
      when {
        expression { BRANCH_NAME ==~ /(master|develop)/ }
      }
      steps {
        container('dind') {
          echo 'Building docker images..'
          script {
            dockerImage = docker.build env.REGISTRY + imageName + ":${env.GIT_COMMIT}"
          }
        }
      }
    }
    stage('Publish container') {
      when {
        expression { BRANCH_NAME ==~ /(master|develop)/ }
      }
      steps {
        container('dind') {
          echo 'Deploying docker image to registry..'
          script {
            docker.withRegistry('https://' + env.REGISTRY, gitlabCredential) {
              dockerImage.push()
            }
          }
        }
      }
    }
    stage('Deploy project develop') {
      when {
        expression { BRANCH_NAME ==~ /(develop)/ }
      }
      steps {
        container('kustomize') {
          echo 'Deploying to test environment..'
          checkout([$class: 'GitSCM',
            branches: [[name: '*/master' ]],
            extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'argocd-develop-backend'], [$class: 'ScmName', name: 'argocd-develop-backend']],
            userRemoteConfigs: [[
              url: 'https://gitlab.espe.edu.ec/devops/argocd-develop-backend.git',
              credentialsId: gitlabCredential
            ]]
          ])
          dir('./argocd-develop-backend/' + argoCDFolderApp + '/settings') {
            sh('kustomize edit set image ' + env.REGISTRY + imageName + ':' + env.GIT_COMMIT)
            sh('git config --global user.email jenkinsci@ci.com')
            sh('git config --global user.name Jenkins Pipeline')
            sh "git commit -am 'Publish new version ${argoCDFolderApp }'"
            withCredentials([usernamePassword(credentialsId: gitlabCredential, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
              sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@${env.ARGO_APPS_URL_DEVELOP_BACKEND} HEAD:master || echo 'no changes'"
            }
          }
        }
      }
    }
    stage('Deploy project master') {
      when {
        expression { BRANCH_NAME ==~ /(master)/ }
      }
      steps {
        container('kustomize') {
          echo 'Deploying to test environment..'
          checkout([$class: 'GitSCM',
            branches: [[name: '*/master' ]],
            extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'argocd-master-backend'], [$class: 'ScmName', name: 'argocd-master-backend']],
            userRemoteConfigs: [[
              url: 'https://gitlab.espe.edu.ec/devops/argocd-master-backend.git',
              credentialsId: gitlabCredential
            ]]
          ])
          dir('./argocd-master-backend/' + argoCDFolderApp + '/settings' ) {
            sh('kustomize edit set image ' + env.REGISTRY + imageName + ':' + env.GIT_COMMIT)
            sh('git config --global user.email jenkinsci@ci.com')
            sh('git config --global user.name Jenkins Pipeline')
            sh "git commit -am 'Publish new version ${argoCDFolderApp }'"
            withCredentials([usernamePassword(credentialsId: gitlabCredential, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
              sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@${env.ARGO_APPS_URL_MASTER_BACKEND} HEAD:master || echo 'no changes'"
            }
          }
        }
      }
    }
  }
}
