package eu.hopu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {

    private String name;
    private String serverUrl;
    private String serverPort;
    private int lifetime;
    private DeviceDto device;
    private Location location;
    private List<Sensor> temperatures;
    private List<Sensor> humidities;
    private Sensor loudness;
    private List<Sensor> gasses;
    private String physicalUrl;
    private boolean crowdMonitoring;
    private String localAddress;
    private int localPort;

    public Device() {
    }

    public Device(String name, String serverUrl, String serverPort, int lifetime, DeviceDto device, Location location, List<Sensor> temperatures, List<Sensor> humidities, Sensor loudness, List<Sensor> gasses, String physicalUrl, boolean crowdMonitoring, String localAddress, int localPort) {
        this.name = name;
        this.serverUrl = serverUrl;
        this.serverPort = serverPort;
        this.lifetime = lifetime;
        this.device = device;
        this.location = location;
        this.temperatures = temperatures;
        this.humidities = humidities;
        this.loudness = loudness;
        this.gasses = gasses;
        this.physicalUrl = physicalUrl;
        this.crowdMonitoring = crowdMonitoring;
        this.localAddress = localAddress;
        this.localPort = localPort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public DeviceDto getDevice() {
        return device;
    }

    public void setDevice(DeviceDto device) {
        this.device = device;
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

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public boolean isCrowdMonitoring() {
        return crowdMonitoring;
    }

    public void setCrowdMonitoring(boolean crowdMonitoring) {
        this.crowdMonitoring = crowdMonitoring;
    }

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", serverUrl='" + serverUrl + '\'' +
                ", serverPort='" + serverPort + '\'' +
                ", lifetime=" + lifetime +
                ", device=" + device +
                ", location=" + location +
                ", temperatures=" + temperatures +
                ", humidities=" + humidities +
                ", loudness=" + loudness +
                ", gasses=" + gasses +
                ", physicalUrl='" + physicalUrl + '\'' +
                ", crowdMonitoring=" + crowdMonitoring +
                ", localAddress='" + localAddress + '\'' +
                ", localPort=" + localPort +
                '}';
    }
}
