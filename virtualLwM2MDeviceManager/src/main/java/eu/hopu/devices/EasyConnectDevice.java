package eu.hopu.devices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import eu.hopu.dto.*;
import eu.hopu.objects.*;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.ObjectModel;

import java.util.List;

import static org.eclipse.leshan.LwM2mId.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EasyConnectDevice extends DeviceBase {

    private final static Gson gson = new Gson();

    private List<SensorDto> temperatures;
    private List<SensorDto> humidities;
    private SensorDto loudness;
    private List<SensorDto> gasses;
    private String physicalUrl;
    private APNConnectionDto apnConnectivity;
    private WlanConnectionDto wlanConnectivity;
    private boolean crowdMonitoring;

    public EasyConnectDevice() {
        super();
    }

    public EasyConnectDevice(String name, String serverUrl, String serverPort, int lifetime, DeviceDto device, LocationDto location, List<SensorDto> temperatures, List<SensorDto> humidities, SensorDto loudness, List<SensorDto> gasses, String physicalUrl, boolean crowdMonitoring, Boolean isBootstrap
            ,APNConnectionDto apnConnectivity, WlanConnectionDto wlanConnectivity) {
        super(name, serverUrl, serverPort, lifetime, device, location, isBootstrap);
        this.temperatures = temperatures;
        this.humidities = humidities;
        this.loudness = loudness;
        this.gasses = gasses;
        this.physicalUrl = physicalUrl;
        this.apnConnectivity = apnConnectivity;
        this.wlanConnectivity = wlanConnectivity;
        this.crowdMonitoring = crowdMonitoring;
    }

    public EasyConnectDevice(JsonObject jsonDevice) {
        this(
                jsonDevice.get("name").getAsString(), jsonDevice.get("serverUrl").getAsString(),
                jsonDevice.get("serverPort").getAsString(),
                jsonDevice.get("lifetime").getAsInt(),
                gson.fromJson(jsonDevice.get("device"), DeviceDto.class),
                gson.fromJson(jsonDevice.get("location"), LocationDto.class),
                (List<SensorDto>) gson.fromJson(
                        jsonDevice.get("temperatures"),
                        new TypeToken<List<SensorDto>>() {
                        }.getType()
                ),
                (List<SensorDto>) gson.fromJson(
                        jsonDevice.get("humidities"),
                        new TypeToken<List<SensorDto>>() {
                        }.getType()
                ),
                gson.fromJson(jsonDevice.get("loudness"), SensorDto.class),
                (List<SensorDto>) gson.fromJson(
                        jsonDevice.get("gasses"),
                        new TypeToken<List<SensorDto>>() {
                        }.getType()
                ),
                jsonDevice.get("physicalUrl").getAsString(),
                jsonDevice.get("crowdMonitoring").getAsBoolean(),
                jsonDevice.get("isBootstrap").getAsBoolean(),
                gson.fromJson(jsonDevice.get("apnConnectivity"), APNConnectionDto.class),
                gson.fromJson(jsonDevice.get("wlanConnectivity"), WlanConnectionDto.class)
        );
    }


    public List<SensorDto> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<SensorDto> temperatures) {
        this.temperatures = temperatures;
    }

    public List<SensorDto> getHumidities() {
        return humidities;
    }

    public void setHumidities(List<SensorDto> humidities) {
        this.humidities = humidities;
    }

    public SensorDto getLoudness() {
        return loudness;
    }

    public void setLoudness(SensorDto loudness) {
        this.loudness = loudness;
    }

    public List<SensorDto> getGasses() {
        return gasses;
    }

    public void setGasses(List<SensorDto> gasses) {
        this.gasses = gasses;
    }

    public String getPhysicalUrl() {
        return physicalUrl;
    }

    public void setPhysicalUrl(String physicalUrl) {
        this.physicalUrl = physicalUrl;
    }

    public boolean hasCrowdMonitoring() {
        return crowdMonitoring;
    }

    public boolean isCrowdMonitoring() {
        return crowdMonitoring;
    }

    public void setCrowdMonitoring(boolean crowdMonitoring) {
        this.crowdMonitoring = crowdMonitoring;
    }

    public APNConnectionDto getApnConnectivity() {
        return apnConnectivity;
    }

    public void setApnConnectivity(APNConnectionDto apnConnectivity) {
        this.apnConnectivity = apnConnectivity;
    }

    public WlanConnectionDto getWlanConnectivity() { return wlanConnectivity; }

    public void setWlanConnectivity(WlanConnectionDto wlanConnectivity) { this.wlanConnectivity = wlanConnectivity; }

    public ObjectsInitializer getObjectInitializer(List<ObjectModel> models) {
        ObjectsInitializer initializer = super.getObjectInitializer(models);

        List<SensorDto> temperatures = getTemperatures();
        if (temperatures != null) {
            IpsoTemperatureObject[] ipsoTemp = new IpsoTemperatureObject[temperatures.size()];
            int index = 0;
            for (SensorDto temperature : temperatures) {
                ipsoTemp[index++] = new IpsoTemperatureObject(
                        temperature.getMaxValue(),
                        temperature.getMinValue(),
                        temperature.getSensorValue()
                );
            }
            initializer.setInstancesForObject(IpsoTemperatureObject.ID, ipsoTemp);
        }

        List<SensorDto> humidities = getHumidities();
        if (humidities != null) {
            IpsoHumidityObject[] ipsoHum = new IpsoHumidityObject[humidities.size()];
            int index = 0;
            for (SensorDto humidity : humidities) {
                ipsoHum[index++] = new IpsoHumidityObject(
                        humidity.getMaxValue(),
                        humidity.getMinValue(),
                        humidity.getSensorValue()
                );
            }
            initializer.setInstancesForObject(3304, ipsoHum);
        }

        SensorDto loudness = getLoudness();
        if (loudness != null)
            initializer.setInstancesForObject(3324, new IpsoLoudnessObject(
                    loudness.getMaxValue(), loudness.getMinValue(), loudness.getSensorValue()));

        List<SensorDto> gasses = getGasses();
        if (gasses != null) {
            IpsoConcentrationObject[] ipsoGasses = new IpsoConcentrationObject[gasses.size()];
            int index = 0;
            for (SensorDto gas : gasses) {
                ipsoGasses[index++] = new IpsoConcentrationObject(
                        gas.getMaxValue(),
                        gas.getMinValue(),
                        gas.getSensorValue()
                );
            }
            initializer.setInstancesForObject(3325, ipsoGasses);
        }


        if (getPhysicalUrl() != null)
            initializer.setInstancesForObject(10000, new SmartSpotObject(getPhysicalUrl()));

        if (hasCrowdMonitoring())
            initializer.setInstancesForObject(10001, new NearWifiDevicesObject());

        if (getApnConnectivity() != null)
            initializer.setInstancesForObject(11,new APNConnectionObject());

        if (getWlanConnectivity() != null)
            initializer.setInstancesForObject(12,new WlanConnectionObject());

        return initializer;
    }

    @Override
    List<LwM2mObjectEnabler> getDeviceEnabledObjects(ObjectsInitializer objectsInitializer) {
        return objectsInitializer.create(SECURITY, SERVER, DEVICE,
                IpsoTemperatureObject.ID,
                IpsoHumidityObject.ID,
                IpsoLoudnessObject.ID,
                IpsoConcentrationObject.ID,
                SmartSpotObject.ID,
                NearWifiDevicesObject.ID,
                APNConnectionObject.ID,
                WlanConnectionObject.ID,
                LOCATION);
    }

    @Override
    public String toString() {
        return "EasyConnectDevice{" +
                "name='" + getName() + '\'' +
                ", serverUrl='" + getServerUrl() + '\'' +
                ", serverPort='" + getServerPort() + '\'' +
                ", lifetime=" + getLifetime() +
                ", device=" + getDevice() +
                ", location=" + getLocation() +
                ", temperatures=" + temperatures +
                ", humidities=" + humidities +
                ", loudness=" + loudness +
                ", gasses=" + gasses +
                ", physicalUrl='" + physicalUrl + '\'' +
                ", crowdMonitoring=" + crowdMonitoring +
                ", apnConnectivity=" + apnConnectivity +
                ", wlanConnectivity=" + wlanConnectivity +
                ", localAddress='" + getLocalAddress() + '\'' +
                ", localPort=" + getLocalPort() +
                '}';
    }
}
