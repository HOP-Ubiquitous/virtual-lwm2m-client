# lwm2miotagentclient


## Construcción, despliegue y ejecución

El proyecto proporciona una serie de perfiles 
en Maven que nos permitirán simplificar los procesos 
que dan título a la sección.
En primer lugar debemos distinguir los perfiles de 
construcción: generate-jar, docker-build y docker-push; 
de los perfiles de versionado de leshan: leshan-version-0.1.11-M9 y
leshan-version-LATEST. 
Los perfiles de versionado de leshan nos permiten determinar
la versión con que se construirá el cliente. En este caso
las opciones son excluyentes.
Los perfiles de construcción nos permiten construir
y desplegar el servicio de modo secuencial. Cada uno de ellos aprovecha
la funcionalidad del anterior y le añade la propia. Pudiendo
seleccionar solo el primero o los tres de forma simultánea.

### generate-jar
Construye el jar de los distintos módulos y los situa en
sus respectivos directorios target.
### docker-build
Construye la imagen docker en la máquina local con el nombre
especificado y los tags develop y latest.
### docker-push
Sube la imagen al registry privado especificado con el tag latest. 


## Lanzar imagen en docker

`docker run -it --name virtual -v $(pwd)/devices.json:/opt/lwm2m-client/lib/devices.json <registry>/virtual-lwm2m-client-<version>:<tag>`
