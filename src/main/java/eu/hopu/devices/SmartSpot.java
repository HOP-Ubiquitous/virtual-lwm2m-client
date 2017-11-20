package eu.hopu.devices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import eu.hopu.dto.DeviceDto;
import eu.hopu.dto.LocationDto;
import eu.hopu.dto.SensorDto;
import eu.hopu.objects.*;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.ObjectModel;

import java.util.List;

import static org.eclipse.leshan.LwM2mId.*;
import static org.eclipse.leshan.LwM2mId.LOCATION;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SmartSpot extends DeviceBase {

    public static final String DEFINITION = "smart_spots.json";

    private LocationDto location;
    private List<SensorDto> temperatures;
    private List<SensorDto> humidities;
    private SensorDto loudness;
    private List<SensorDto> gasses;
    private String physicalUrl;
    private boolean crowdMonitoring;


    public SmartSpot() {
        super();
    }

    public SmartSpot(String name, String serverUrl, String serverPort, int lifetime, DeviceDto device, LocationDto location, List<SensorDto> temperatures, List<SensorDto> humidities, SensorDto loudness, List<SensorDto> gasses, String physicalUrl, boolean crowdMonitoring, String localAddress, int localPort) {
        super(name, serverUrl, serverPort, lifetime, device, localAddress, localPort);
        this.location = location;
        this.temperatures = temperatures;
        this.humidities = humidities;
        this.loudness = loudness;
        this.gasses = gasses;
        this.physicalUrl = physicalUrl;
        this.crowdMonitoring = crowdMonitoring;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
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


    public ObjectsInitializer getObjectInitializer(List<ObjectModel> models) {
        ObjectsInitializer initializer = super.getObjectInitializer(models);

        LocationDto location = getLocation();
        if (location != null)
            initializer.setInstancesForObject(LOCATION, new LocationObject(location.getLatitude(), location.getLongitude(), location.getAltitude()));

        List<SensorDto> temperatures = getTemperatures();
        if (temperatures != null) {
            IpsoTemperatureObject[] ipsoTemp = new IpsoTemperatureObject[temperatures.size()];
            int index = 0;
            for (SensorDto temperature : temperatures) {
                ipsoTemp[index++] = new IpsoTemperatureObject(
                        temperature.getMaxValue(), temperature.getMinValue(), temperature.getSensorValue());
            }
            initializer.setInstancesForObject(3303, ipsoTemp);
        }

        List<SensorDto> humidities = getHumidities();
        if (humidities != null) {
            IpsoHumidityObject[] ipsoHum = new IpsoHumidityObject[humidities.size()];
            int index = 0;
            for (SensorDto humidity : humidities) {
                ipsoHum[index++] = new IpsoHumidityObject(
                        humidity.getMaxValue(), humidity.getMinValue(), humidity.getSensorValue());
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
                        gas.getMaxValue(), gas.getMinValue(), gas.getSensorValue());
            }
            initializer.setInstancesForObject(3325, ipsoGasses);
        }

        if (getPhysicalUrl() != null)
            initializer.setInstancesForObject(10000, new SmartSpotObject(getPhysicalUrl()));

        if (hasCrowdMonitoring())
            initializer.setInstancesForObject(10001, new NearWifiDevicesObject());

        return initializer;
    }

    @Override
    List<LwM2mObjectEnabler> getDeviceEnabledObjects(ObjectsInitializer objectsInitializer) {
        return objectsInitializer.create(SECURITY, SERVER, DEVICE, LOCATION, 3303, 3304, 3324, 3325, 10000, 10001);
    }

    @Override
    public String toString() {
        return "SmartSpot{" +
                "name='" + getName() + '\'' +
                ", serverUrl='" + getServerUrl() + '\'' +
                ", serverPort='" + getServerPort() + '\'' +
                ", lifetime=" + getLifetime() +
                ", device=" + getDevice() +
                ", location=" + location +
                ", temperatures=" + temperatures +
                ", humidities=" + humidities +
                ", loudness=" + loudness +
                ", gasses=" + gasses +
                ", physicalUrl='" + physicalUrl + '\'' +
                ", crowdMonitoring=" + crowdMonitoring +
                ", localAddress='" + getLocalAddress() + '\'' +
                ", localPort=" + getLocalPort() +
                '}';
    }
}
