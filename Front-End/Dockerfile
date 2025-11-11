FROM nginx:alpine

# Copia tutti i file del frontend
COPY . /usr/share/nginx/html

# Configurazione Nginx personalizzata
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Espone la porta 80
EXPOSE 80

# Mantiene l'immagine leggera rimuovendo cache non necessaria
RUN rm -rf /var/cache/apk/*
