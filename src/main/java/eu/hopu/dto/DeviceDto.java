package eu.hopu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDto {

    private String name;
    private int batteryStatus;
    private int batteryLevel;

    public DeviceDto() {
    }

    public DeviceDto(String name, int batteryStatus, int batteryLevel) {
        this.name = name;
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

    @Override
    public String toString() {
        return "DeviceObject{" +
                "batteryStatus=" + batteryStatus +
                ", batteryLevel=" + batteryLevel +
                '}';
    }
}
