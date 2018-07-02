# LWM2M IoT Agent Client

## Introducción
lwm2miotagentclient is a virtual client that simulates an lwm2m device. Its aim is to serve for tests in the development
of systems for the management of IoT devices. Specifically, it is used for tests on the HOMARD platform, as well as for 
tests on the FIWARE architecture.


## Arquitectura
It starts from the client that provides leshan and an abstraction layer is added to be able to create objects, instances
 and resources using a json file. The aim is to provide a simple way to simulate a large number of devices in a semi-automated manner.

## API

## Dependencies
- leshan-client-cf (version 0.1.11-M9 and latest): OMA LWM2M Client implementation. The library serves as main component
to reach the reach the interaction client-server.

## Configuration
### Objetos y recursos implementados
Below, we can see an example of the json file that will define the devices to be launched.

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
     "metadata": {
       "place": "<place-name>",
       "image": "<image-url>" 
     },
     "physicalUrl": "http://laperalimonera.com",
     "crowdMonitoring": true
   }
]
   ```

### Flags
To facilitate the process of creating files, a series of flags are added in each resource that will allow the developer 
to have additional information about the resource in question.

- Mandatory[**M**] | Optional[**O**]
  - An optional object may have, at the time it is added, mandatory resources.
- Type: Object[**Ob**] | Array[**A**] | Integer[**I**] | Float[**F**] | String[**S**] | Boolean[**B**]
- Observable[**N**]
  - Resources on which observations can be created.

### Device ID [M|S]
- **name [M|S]** →  it represents the identifier with which the device will register on a server.

### 0 - LWM2M Security [M] 
- **serverUrl (/0/0/0) [M|S]** → Server url to which the device will connect.
- **serverPort (/0/0/0) [M|S]** → Server port to which the device will connect.
- **isBootstrap (/0/0/1) [M|B]** →  It specifies if the server is Bootstrap.

### 1 - LWM2M Server [M]
- **lifetime (/1/0/1) [M|I]** → It specifies the number of seconds between UPDATES from the device to the server.

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
  - **maxValue [M|I]** → Maximum value that the resource can take.
  - **minValue [M|I]** → Minimum value that the resource can take.
  - **sensorValue [M|I]** → Value at which the resource will start.

### 3304 - IPSO Humidity [O|A]  
- **Sensor value (/3304/0/5700) [N]** → This resource type returns the Humidity Value in relative humidity (%RH). Se proporcionan tres valores:
  - **maxValue [M|I]** → Maximum value that the resource can take.
  - **minValue [M|I]** → Minimum value that the resource can take.
  - **sensorValue [M|I]** → Value at which the resource will start.

### 3324 - IPSO Loudness [O|Ob]
- **Sensor value (/3324/0/5700) [N]** → This resource type returns the Loudness Value in dB. Se proporcionan tres valores:
  - **maxValue [M|I]** → Maximum value that the resource can take.
  - **minValue [M|I]** → Minimum value that the resource can take.
  - **sensorValue [M|I]** → Value at which the resource will start.

### Gasses [O|A]
In this section, the different gases which the device can represent are added. The order determines the gas represented.
 In this way, each element in the array represent in sequential order:

#### 3325 - NO2 [O|Ob]
- **Sensor value (/3325/0/5700) [N]** → This resource type returns the Concentration Value. Se proporcionan tres valores:
  - **maxValue [M|I]** → Maximum value that the resource can take.
  - **minValue [M|I]** → Minimum value that the resource can take.
  - **sensorValue [M|I]** → Value at which the resource will start.

#### 3325 - SO2 [O|Ob]
- **Sensor value (/3325/1/5700) [N]** → This resource type returns the Concentration Value. Se proporcionan tres valores:
  - **maxValue [M|I]** → Maximum value that the resource can take.
  - **minValue [M|I]** → Minimum value that the resource can take.
  - **sensorValue [M|I]** → Value at which the resource will start.

#### 3325 - O3 [O|Ob]
- **Sensor value (/3325/2/5700) [N]** → This resource type returns the Concentration Value. Se proporcionan tres valores:
  - **maxValue [M|I]** → Maximum value that the resource can take.
  - **minValue [M|I]** → Minimum value that the resource can take.
  - **sensorValue [M|I]** → Value at which the resource will start.

#### 3325 - H2S [O|Ob]
- **Sensor value (/3325/3/5700) [N]** → This resource type returns the Concentration Value. Se proporcionan tres valores:
  - **maxValue [M|I]** → Maximum value that the resource can take.
  - **minValue [M|I]** → Minimum value that the resource can take.
  - **sensorValue [M|I]** → Value at which the resource will start.

#### 3325 - CO [O|Ob]
- **Sensor value (/3325/4/5700) [N]** → This resource type returns the Concentration Value. Se proporcionan tres valores:
  - **maxValue [M|I]** → Maximum value that the resource can take.
  - **minValue [M|I]** → Minimum value that the resource can take.
  - **sensorValue [M|I]** → Value at which the resource will start.

### 10000 - SmartSpot [O|S]
- **physicalUrl (/10000/0/0) [O|S]** → Shows URL advertised.

### 10001 - Near WiFi devices [O|B]
- **crowdMonitoring [O|B]** → This resource activates the crowd monitoring option that will generate the following three 
resources of the object:
  - **/10001/0/1 [N]** → Devices found in the last min.
  - **/10001/0/2 [N]** → Devices found in the last 10min.
  - **/10001/0/3 [N]** → Devices found in the last hour.

### 32970 - SmartSpot Metadata [O|Ob]
- **name (/32970/0/0) [O|A]** → This resource specifies a non technical name for the device. It helps in frontend representations
- **metadata [O|Ob]** → Object that contains place name and image url
  - **place (/32970/0/1) [O|S]** → This resource stores the device location in a readable way
  - **image (/32970/0/2) [O|S]** → This resource stores a url that will redirect us to the image of the site where the device has been deployed

## Build, deploy and run
The project provides a series of profiles in Maven which will allow us to simplify the processes that give title to the 
section. First of all, we must distinguish the construction profiles: generate-jar, docker-build and docker-push; of the
 leshan versioning profiles: leshan-version-0.1.11-M9 and leshan-version-LATEST.
The leshan versioning profiles allow us to determine the version with which the client will be built. In this case, the 
options are exclusive. The construction profiles allow us to build and deploy the service sequentially. Each of them takes
 advantage of the functionality of the previous one and adds its own, being able to select only the first or the three simultaneously.

#### generate-jar
Build the jar of the different modules and place them in their respective target directories.

#### docker-build
Build the docker image on the local machine with the specified name and the develop and latest tags.

#### docker-push
Upload the image to the private registry specified with the latest tag.

### Lanzar imagen en docker
#### Ejecutar docker en línea de comandos
```bash
docker run -it --name virtual -v $(pwd)/devices.json:/opt/lwm2m-client/lib/devices.json <registry>/virtual-lwm2m-client-<version>:<tag>
```
#### Ejecutar mediante docker-compose
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
The only parameter to highlight is the file that is passed to the docker. The file devices.json represents the set of devices that will be launched, following the format presented in previous sections.

## Compatibility and versions
Currently, there are two versions of the virtual client: 0.1.11-M9 and LATEST.

### 0.1.11-M9
Compatible with the modified version by HOPU of lwm2m-iotagent. Compatible with homard-legacy and homard-staging.

### LATEST == 1.0.0-M4
Compatible with homard-legacy and homard-staging.

### Sin compatibilidad
Currently, there is no compatible version with leshan server demo.

### To Be Done 
- Explain the case of the creation of routes.
- Explain the function of the device.model parameter
- Add architecture image by modules.
- Extend architecture segment.
- Add section explaining how to add a new resource.

## Extensibility
### Represent a new OMA LWM2M Object
This section exposes the easiest way for add a new OMA LWM2M Object. 
1. **Define object representation in device json specification**. As can be seen in configuration section, the relation 
between resources and the device json representation must be declared. The SmartSpot Metadata object is taken as an 
example to illustrate the process. In this case, the object has three interesting resources: name, place and image. 
In this case the name is already defined, so a new first level attribute is defined, named metadata, for place and image 
resources.
2. **Create new DeviceModel or modify existing**. Assuming that the DeviceModel is SmartSpot, a new object representation
implies the eu.hopu.devices.SmartSpot class defining a new class field and, in this case a new custom dto for the data. Once
the data is stored, the new OMA LWM2M object must be initialized in getObjectInitilizer method.
- Modify constructor with jsonDevice as parameter,

```java
public class SmartSpot extends DeviceBase {

    public SmartSpot(JsonObject jsonDevice) {
        this(..., gson.fromJson(jsonDevice.get("metadata"), MetadataDto.class)
        );
    }    
}
```

- Define, if necessary a new Dto
```java
public class MetadataDto {

    private String place;
    private String image;

    public MetadataDto() {
    }

    public MetadataDto(String place, String image) {
        this.place = place;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "MetadataDto{" +
                "place='" + place + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

}
```

- Modify constructor with each atribute as parameter and add new field
```java
public class SmartSpot extends DeviceBase {

    private MetadataDto metadata;

    public SmartSpot(..., MetadataDto metadata) {
        this.metadata = metadata;
    }
}
```

- Modify getObjectInitializer method adding a new OMA LWM2M Object
```java
public class SmartSpot extends DeviceBase {

    public ObjectsInitializer getObjectInitializer(List<ObjectModel> models) {
        ObjectsInitializer initializer = super.getObjectInitializer(models);
        
        // Object added
        if (getMetadata() != null)
            initializer.setInstancesForObject(32970, new MetadataObject(getMetadata()));

        return initializer;
    }

}
```

- Create new OMA LWM2M Object

```java
public class MetadataObject extends BaseInstanceEnabler {

    public static final int ID = 32970;

    private final String name;
    private final String place;
    private final String image;

    public MetadataObject() {
        this.name = "";
        this.place = "";
        this.image = "";
    }

    public MetadataObject(String name, String place, String image) {
        this.name = name;
        this.place = place;
        this.image = image;
    }

    @Override
    public ReadResponse read(int resourceid) {
        switch (resourceid) {
            case 0:
                return ReadResponse.success(resourceid, getName());
            case 1:
                return ReadResponse.success(resourceid, getPlace());
            case 2:
                return ReadResponse.success(resourceid, getImage());
            default:
                return super.read(resourceid);
        }
    }

    @Override
    public ExecuteResponse execute(int resourceid, String params) {
        return ExecuteResponse.success();
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        return WriteResponse.success();
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getImage() {
        return image;
    }
}
```

- Add new OMA LWM2M object to getDeviceEnabledObjectsMethod
```java
public class SmartSpot extends DeviceBase {

    @Override
    List<LwM2mObjectEnabler> getDeviceEnabledObjects(ObjectsInitializer objectsInitializer) {
        return objectsInitializer.create(SECURITY, SERVER, DEVICE,
                IpsoTemperatureObject.ID,
                IpsoHumidityObject.ID,
                IpsoLoudnessObject.ID,
                IpsoConcentrationObject.ID,
                SmartSpotObject.ID,
                NearWifiDevicesObject.ID,
                MetadataObject.ID,
                LOCATION);
    }
}
```

At this point, the service must be able to represent the desired new OMA LWM2M Object. 