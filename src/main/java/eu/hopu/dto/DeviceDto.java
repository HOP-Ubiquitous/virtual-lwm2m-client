package eu.hopu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDto {

    private int batteryStatus;
    private int batteryLevel;

    public DeviceDto() {
    }

    public DeviceDto(int batteryStatus, int batteryLevel) {
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

    @Override
    public String toString() {
        return "DeviceDto{" +
                "batteryStatus=" + batteryStatus +
                ", batteryLevel=" + batteryLevel +
                '}';
    }

}
