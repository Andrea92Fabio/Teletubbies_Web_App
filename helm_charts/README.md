1. Configurare la shell per usare il Docker all'interno di Minikube
    eval $(minikube docker-env)

2. Cerca le immagini Docker
    docker images

3. Ripristina l'ambiente Docker locale
    eval $(minikube docker-env -u)

4. Carica le immagini su Minikube
    minikube image load teletubbies-backend:v1.0.0
    minikube image load teletubbies-db:v1.0.0
    minikube image load teletubbies-frontend:v1.0.0

5. Verifica se le immagini sono state caricate
    minikube image ls

6. Eseguire Helm
    helm install teletubbies-app .