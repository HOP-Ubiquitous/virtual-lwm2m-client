package eu.hopu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDto {

    private String name;
    private String type;
    private int batteryStatus;
    private int batteryLevel;

    public DeviceDto() {
    }

    public DeviceDto(String name, String type, int batteryStatus, int batteryLevel) {
        this.name = name;
        this.type = type;
        this.batteryStatus = batteryStatus;
        this.batteryLevel = batteryLevel;
    }

    public int getBatteryStatus() {
        return batteryStatus;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryStatus(int batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DeviceDto{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", batteryStatus=" + batteryStatus +
                ", batteryLevel=" + batteryLevel +
                '}';
    }
}
