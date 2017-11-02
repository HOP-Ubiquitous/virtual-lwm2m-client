package eu.hopu.devices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import eu.hopu.dto.Battery;
import eu.hopu.dto.Location;
import eu.hopu.dto.Sensor;
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

    private Location location;
    private List<Sensor> temperatures;
    private List<Sensor> humidities;
    private Sensor loudness;
    private List<Sensor> gasses;
    private String physicalUrl;
    private boolean crowdMonitoring;


    public SmartSpot() {
        super();
    }

    public SmartSpot(String name, String serverUrl, String serverPort, int lifetime, Battery device, Location location, List<Sensor> temperatures, List<Sensor> humidities, Sensor loudness, List<Sensor> gasses, String physicalUrl, boolean crowdMonitoring, String localAddress, int localPort) {
        super(name, serverUrl, serverPort, lifetime, device, localAddress, localPort);
        this.location = location;
        this.temperatures = temperatures;
        this.humidities = humidities;
        this.loudness = loudness;
        this.gasses = gasses;
        this.physicalUrl = physicalUrl;
        this.crowdMonitoring = crowdMonitoring;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Sensor> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<Sensor> temperatures) {
        this.temperatures = temperatures;
    }

    public List<Sensor> getHumidities() {
        return humidities;
    }

    public void setHumidities(List<Sensor> humidities) {
        this.humidities = humidities;
    }

    public Sensor getLoudness() {
        return loudness;
    }

    public void setLoudness(Sensor loudness) {
        this.loudness = loudness;
    }

    public List<Sensor> getGasses() {
        return gasses;
    }

    public void setGasses(List<Sensor> gasses) {
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

        Location location = getLocation();
        if (location != null)
            initializer.setInstancesForObject(LOCATION, new LocationObject(location.getLatitude(), location.getLongitude(), location.getAltitude()));

        List<Sensor> temperatures = getTemperatures();
        if (temperatures != null) {
            IpsoTemperatureObject[] ipsoTemp = new IpsoTemperatureObject[temperatures.size()];
            int index = 0;
            for (Sensor temperature : temperatures) {
                ipsoTemp[index++] = new IpsoTemperatureObject(
                        temperature.getMaxValue(), temperature.getMinValue(), temperature.getSensorValue());
            }
            initializer.setInstancesForObject(3303, ipsoTemp);
        }

        List<Sensor> humidities = getHumidities();
        if (humidities != null) {
            IpsoHumidityObject[] ipsoHum = new IpsoHumidityObject[humidities.size()];
            int index = 0;
            for (Sensor humidity : humidities) {
                ipsoHum[index++] = new IpsoHumidityObject(
                        humidity.getMaxValue(), humidity.getMinValue(), humidity.getSensorValue());
            }
            initializer.setInstancesForObject(3304, ipsoHum);
        }

        Sensor loudness = getLoudness();
        if (loudness != null)
            initializer.setInstancesForObject(3324, new IpsoLoudnessObject(
                    loudness.getMaxValue(), loudness.getMinValue(), loudness.getSensorValue()));

        List<Sensor> gasses = getGasses();
        if (gasses != null) {
            IpsoConcentrationObject[] ipsoGasses = new IpsoConcentrationObject[gasses.size()];
            int index = 0;
            for (Sensor gas : gasses) {
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
