FROM mariadb:10.6.12

# Imposta le variabili d'ambiente obbligatorie
ENV MARIADB_ROOT_PASSWORD=rootpassword
ENV MARIADB_DATABASE=san_martino_friendship_day
ENV MARIADB_USER=user
ENV MARIADB_PASSWORD=password

# Copia lo script SQL di inizializzazione nel path previsto da MariaDB
COPY san_martino_friendship_day.sql /docker-entrypoint-initdb.d/

# RUN apt update -y && apt install mysql-client -y
# RUN chmod +X /usr/local/bin/docker-entrypoint.sh && chown mysql /usr/local/bin/docker-entrypoint.sh

# ENTRYPOINT ["docker-entrypoint.sh"]
