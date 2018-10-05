package eu.hopu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.eclipse.leshan.client.resource.BaseInstanceEnabler;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectivityMonitoringDto {

    private Integer mcc;
    private Integer mnc;
    private String ipAddress;

    public ConnectivityMonitoringDto() {
    }

    public ConnectivityMonitoringDto(Integer mcc, Integer mnc, String ipAddress) {
        this.mcc = mcc;
        this.mnc = mnc;
        this.ipAddress = ipAddress;
    }

    public Integer getMcc() {
        return mcc;
    }

    public void setMcc(Integer mcc) {
        this.mcc = mcc;
    }

    public Integer getMnc() {
        return mnc;
    }

    public void setMnc(Integer mnc) {
        this.mnc = mnc;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "ConnectivityMonitoringDto{" +
                "mcc=" + mcc +
                ", mnc=" + mnc +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }

}
