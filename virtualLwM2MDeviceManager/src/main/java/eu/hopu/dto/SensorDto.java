package eu.hopu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorDto {

    private double maxValue;
    private double minValue;
    private double sensorValue;

    public SensorDto() {
    }

    public SensorDto(double maxValue, double minValue, double sensorValue) {
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.sensorValue = sensorValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(double sensorValue) {
        this.sensorValue = sensorValue;
    }

    @Override
    public String toString() {
        return "SensorDto{" +
                "maxValue=" + maxValue +
                ", minValue=" + minValue +
                ", sensorValue=" + sensorValue +
                '}';
    }
}
