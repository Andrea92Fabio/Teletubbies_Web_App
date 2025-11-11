Avvia Minikune e controllare lo stato
	minikube start
	minikube docker-env
	kubectl get nodes
	
A. Configura l'Ambiente Docker, usare  il demone docker di minikube (altrimenti k8s non troverà le immagini)
	eval $(minikube docker-env)

B. Ricostruzione delle Immagini
	docker build -t teletubbies-db:latest ./DataBase
	docker build -t teletubbies-backend:latest ./Back-End/sanmartino-friendship-day
	docker build -t teletubbies-frontend:latest ./Front-End
	
C. Disconnettere la shell da demone docker e tornare a docker locale(Opzionale)
	eval $(minikube docker-env -u)

-------------------------------------------------------

Entra nella seguente Directory ed esegui i comandi come seguono;

	andrea@Andrea-Home:~/Desktop/Repository_GitHub/Teletubbies_Web_App/kubernetes

Assicura che Ingress sia attivo in Minikube.
	minikube addons enable ingress	
	
1. Crea il PVC per il volume persistente.
	kubectl apply -f db/k8s-db-pvc.yaml	
	
2. Crea le credenziali (Secret).
	kubectl apply -f config/k8s-secrets.yaml
	
3. Crea la ConfigMap con lo script SQL.
	kubectl apply -f db/k8s-db-schema-configmap.yaml
	
4. Crea il Service del DB.
	kubectl apply -f db/k8s-db-service.yaml	
	
5. Crea il Deployment del DB (avvierà l'Init Container).
	kubectl apply -f db/k8s-db-deployment.yaml	

--------------------------------------------------------------

- Pausa!	
	Controlla: Attendi che teletubbies-db sia 1/1 Running.
	kubectl get pods

--------------------------------------------------------------

6. Crea il Service del Backend.
	kubectl apply -f backend/k8s-backend-service.yaml	
	
7. Crea il Deployment del Backend.
	kubectl apply -f backend/k8s-backend-deployment.yaml	
	
- Pausa!
	Controlla: Attendi che tutti i Pod Backend siano 1/1 Running.
	
8. Crea il Deployment del Frontend.
	kubectl apply -f frontend/k8s-frontend-deployment.yaml	
	
9. Crea il Service del Frontend.
	kubectl apply -f frontend/k8s-frontend-service.yaml	
	
10. Crea l'Ingress (per accedere dall'esterno).
	kubectl apply -f frontend/k8s-ingress.yaml	

------------------------------------------------------------------------------

- Pausa!
	Controlla: Accedi ala pagina e compila la form con i dati richiesti
	
	http://progetto.teletubbies.com/

--------------------------------------------------------------------------------	
		
- Pausa!
	Controlla: Entra nel database e verifica che i dati della form siano stati salvati

11. Trova il Nome del Pod DB Corrente
	kubectl get pods -l component=db

12. Entra nella Shell del Pod DB
	kubectl exec -it <NOME_DEL_POD_DB> -- /bin/bash
	
13. Connettiti al Monitor MariaDB
	mariadb -u root -pmysecretpassword san_martino_friendship_day

14. Visualizza i Dati (Verticale e Ordinato)
	SELECT * FROM customers ORDER BY id ASC \G

-------------------------------------------------------------------------------------

15. Eliminare tutte le immagini Docker
	docker rmi $(docker images -a -q)

16. Come eliminare tutti i Container
	docker rm -f $(docker ps -a -q)

17. Eliminare tutti i POD
	kubectl delete pods --all
	kubectl delete deployment,service,configmap,pvc,secret --all