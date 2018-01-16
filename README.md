# LWM2M IoT Agent Client

## Introducción
lwm2miotagentclient es un cliente virtual que simula un dispositivo lwm2m. Su objetivo es servir para pruebas en el desarrollo
de sistemas para la gestión de dispositivos IoT. Concretamente se usa para pruebas sobre la plataforma HOMARD así como
para pruebas sobre la arquitectura FIWARE.

## Arquitectura
Se parte del cliente que proporciona leshan y se añade una capa de abstracción para ser capaz de crear objetos, instancias
y recursos mediante un fichero json. El objetivo es proporcionar una manera sencilla de simular un gran número de dispositivos
de una manera semiautomatizada.

## Compatibilidad y versiones
Actualmente existen dos versiones del cliente virtual: 0.1.11-M9 y LATEST.

### 0.1.11-M9
Compatible con la versión modificada por HOPU de lwm2m-iotagent.
Compatible con homard-legacy y homard-staging

### LATEST == 1.0.0-M4
Compatible con homard-legacy y homard-staging

### Sin compatibilidad
Ahora mismo no hay versión compatible con leshan server demo

## Objetos y recursos implementados
A continuación encontramos un ejemplo del fichero json que definirá los dispositivos a lanzar.

```json
[
   {
     "name": "HOP240ac406a79e",
     "serverUrl": "coap://staging.hopu.eu",
     "serverPort": "5683",
     "isBootstrap": false,
     "lifetime": 15,
     "device": {
       "model": "SmartSpot",
       "batteryLevel": 99,
       "batteryStatus": 0
     },
     "location": {
       "latitude": 19.42422,
       "longitude": -99.13322,
       "altitude": 1
     },
     "temperatures": [
       {
         "maxValue": 25,
         "minValue": 19,
         "sensorValue": 22
       }
     ],
     "humidities": [
       {
         "maxValue": 60,
         "minValue": 30,
         "sensorValue": 45
       }
     ],
     "loudness": {
       "maxValue": 20,
       "minValue": 10,
       "sensorValue": 15
     },
     "gasses": [
       {
         "maxValue": 19,
         "minValue": 0,
         "sensorValue": 6
       },
       {
         "maxValue": 52,
         "minValue": 0,
         "sensorValue": 17
       },
       {
         "maxValue": 56,
         "minValue": 0,
         "sensorValue": 21
       },
       {
         "maxValue": 100,
         "minValue": 0,
         "sensorValue": 40
       },
       {
         "maxValue": 10,
         "minValue": 0,
         "sensorValue": 2
       }
     ],
     "physicalUrl": "http://laperalimonera.com",
     "crowdMonitoring": true
   }
]
   ```

### Flags
Para facilitar el proceso de creación de ficheros se añaden una serie de flags en cada recurso que permitirán al desarrollador
tener información adicional del recurso en cuestión.

- Mandatory[**M**] | Optional[**O**]
  - Un objeto opcional puede tener, en el momento en que se añade, recursos mandatory.
- Type: Object[**Ob**] | Array[**A**] | Integer[**I**] | Float[**F**] | String[**S**] | Boolean[**B**]
- Observable[**N**]
  - Recursos sobre los que se pueden crear observaciones.

### Device ID [M|S]
- **name [M|S]** → representa el identificador con el que el dispositivo se registrará en un servidor.

### 0 - LWM2M Security [M] 
- **serverUrl (/0/0/0) [M|S]** → Url del servidor al que se conectará el dispositivo.
- **serverPort (/0/0/0) [M|S]** → Puerto del servidor al que se conectará el dispositivo.
- **isBootstrap (/0/0/1) [M|B]** →  Especifica si el servidor es Bootstrap.

### 1 - LWM2M Server [M]
- **lifetime (/1/0/1) [M|I]** → Especifica el número de segundos entre UPDATES del dispositivo al servidor.

### 3 - Device [M|Ob]
- **model (/3/0/0) [M|S]** → TBD
- **batteryLevel (/3/0/9) [M|I|N]** → Contains the current battery  level as a percentage (with a range from 0 to 100).
- **batteryStatus (/3/0/20) [M|I]** → 0=Normal The battery is operating normally and not on power, 1=Charging The battery 
is currently charging, 2=Charge Complete The battery is fully charged and still on power, 3=Damaged The battery has some 
problem, 4=Low Battery The battery is low on charge, 5= Not Installed The battery is not installed, 6 = Unknown The 
battery information is not available.

### 6 - Location [O|Ob]
- **latitude (/6/0/0) [M|F]** → The decimal notation of latitude, e.g., -43.5723 [World Geodetic System 1984].
- **longitude (/6/0/1) [M|F]** → The decimal notation of longitude, e.g., 153.21760 [World Geodetic System 1984].
- **altitude (/6/0/2) [M|F]** → The decimal notation of altitude in meters above sea level.

### 3303 - IPSO Temperature [O|A]
- **Sensor value (/3303/0/5700) [N]** → This resource type returns the Temperature Value in °C. Se proporcionan tres valores:
  - **maxValue [M|I]** → Valor máximo que puede tomar el recurso.
  - **minValue [M|I]** → Valor mínimo que puede tomar el recurso.
  - **sensorValue [M|I]** → Valor con el que se iniciará el recurso.

### 3304 - IPSO Humidity [O|A]  
- **Sensor value (/3304/0/5700) [N]** → This resource type returns the Humidity Value in relative humidity (%RH). Se proporcionan tres valores:
  - **maxValue [M|I]** → Valor máximo que puede tomar el recurso.
  - **minValue [M|I]** → Valor mínimo que puede tomar el recurso.
  - **sensorValue [M|I]** → Valor con el que se iniciará el recurso.

### 3324 - IPSO Loudness [O|Ob]
- **Sensor value (/3324/0/5700) [N]** → This resource type returns the Loudness Value in dB. Se proporcionan tres valores:
  - **maxValue [M|I]** → Valor máximo que puede tomar el recurso.
  - **minValue [M|I]** → Valor mínimo que puede tomar el recurso.
  - **sensorValue [M|I]** → Valor con el que se iniciará el recurso.

### Gasses [O|A]
En esta sección se añaden los diferentes gases que el dispositivo puede representar. El orden determina el gas representado.
De este modo cada una de las elementos en el array representan en orden secuencial:

#### 3325 - NO2 [O|Ob]
- **Sensor value (/3325/0/5700) [N]** → This resource type returns the Concentration Value. Se proporcionan tres valores:
  - **maxValue [M|I]** → Valor máximo que puede tomar el recurso.
  - **minValue [M|I]** → Valor mínimo que puede tomar el recurso.
  - **sensorValue [M|I]** → Valor con el que se iniciará el recurso.

#### 3325 - SO2 [O|Ob]
- **Sensor value (/3325/1/5700) [N]** → This resource type returns the Concentration Value. Se proporcionan tres valores:
  - **maxValue [M|I]** → Valor máximo que puede tomar el recurso.
  - **minValue [M|I]** → Valor mínimo que puede tomar el recurso.
  - **sensorValue [M|I]** → Valor con el que se iniciará el recurso.

#### 3325 - O3 [O|Ob]
- **Sensor value (/3325/2/5700) [N]** → This resource type returns the Concentration Value. Se proporcionan tres valores:
  - **maxValue [M|I]** → Valor máximo que puede tomar el recurso.
  - **minValue [M|I]** → Valor mínimo que puede tomar el recurso.
  - **sensorValue [M|I]** → Valor con el que se iniciará el recurso.

#### 3325 - H2S [O|Ob]
- **Sensor value (/3325/3/5700) [N]** → This resource type returns the Concentration Value. Se proporcionan tres valores:
  - **maxValue [M|I]** → Valor máximo que puede tomar el recurso.
  - **minValue [M|I]** → Valor mínimo que puede tomar el recurso.
  - **sensorValue [M|I]** → Valor con el que se iniciará el recurso.

#### 3325 - CO [O|Ob]
- **Sensor value (/3325/4/5700) [N]** → This resource type returns the Concentration Value. Se proporcionan tres valores:
  - **maxValue [M|I]** → Valor máximo que puede tomar el recurso.
  - **minValue [M|I]** → Valor mínimo que puede tomar el recurso.
  - **sensorValue [M|I]** → Valor con el que se iniciará el recurso.

### 10000 - SmartSpot [O|S]
- **physicalUrl (/10000/0/0) [O|S]** → Shows URL advertised.

### 10001 - Near WiFi devices [O|B]
- **crowdMonitoring [O|B]** → Este recurso activa la opción crowd monitoring que generará los siguientes tres recursos
del objeto:
  - **/10001/0/1 [N]** → Devices found in the last min.
  - **/10001/0/2 [N]** → Devices found in the last 10min.
  - **/10001/0/3 [N]** → Devices found in the last hour.


## Construcción, despliegue y ejecución

El proyecto proporciona una serie de perfiles en Maven que nos permitirán simplificar los procesos que dan título a la sección.
En primer lugar debemos distinguir los perfiles de construcción: generate-jar, docker-build y docker-push; de los perfiles 
de versionado de leshan: leshan-version-0.1.11-M9 y leshan-version-LATEST. 

Los perfiles de versionado de leshan nos permiten determinar la versión con que se construirá el cliente. En este caso
las opciones son excluyentes.
Los perfiles de construcción nos permiten construir y desplegar el servicio de modo secuencial. Cada uno de ellos aprovecha
la funcionalidad del anterior y le añade la propia. Pudiendo seleccionar solo el primero o los tres de forma simultánea.

### generate-jar
Construye el jar de los distintos módulos y los situa en sus respectivos directorios target.
### docker-build
Construye la imagen docker en la máquina local con el nombre especificado y los tags develop y latest.
### docker-push
Sube la imagen al registry privado especificado con el tag latest. 


## Lanzar imagen en docker

### Ejecutar docker en línea de comandos
```bash
docker run -it --name virtual -v $(pwd)/devices.json:/opt/lwm2m-client/lib/devices.json <registry>/virtual-lwm2m-client-<version>:<tag>
```

### Ejecutar mediante docker-compose
```
version: '3'

services:
  virtual-lwm2m-client:
    container_name: virtual
    image: <registry>/virtual-lwm2m-client-<version>:<tag>
    volumes:
      - "$(pwd)/devices.json:/opt/lwm2m-client/lib/devices.json"          

```

### Parámetros
El único parámetro a resaltar es el fichero que se le pasa al docker. El fichero devices.json representa el conjunto de
dispositivos que se lanzarán. Siguiendo el formato presentado en secciones anteriores.

### TBD 
- Explicar caso de la creación de rutas.
- Explicar la función del parámetro device.model
- Añadir imagen arquitectura por módulos.
- Extender segmento arquitectura.
- Añadir sección explicando la forma de añadir un nuevo recurso.