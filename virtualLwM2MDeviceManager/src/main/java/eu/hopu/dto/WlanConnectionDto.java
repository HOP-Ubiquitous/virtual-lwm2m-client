package eu.hopu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WlanConnectionDto {
    private boolean enable;
    private String ssid;
    private int mode;
    private int channel;
    private int standard;
    private int authentication;
    private int encryption;
    private String wpaSharedKey;


    public WlanConnectionDto() {
    }

    public WlanConnectionDto(boolean enable, String ssid, int mode, int channel, int standard, int authentication, int encryption, String wpaSharedKey) {
        this.enable = enable;
        this.ssid = ssid;
        this.mode = mode;
        this.channel = channel;
        this.standard = standard;
        this.authentication = authentication;
        this.encryption = encryption;
        this.wpaSharedKey = wpaSharedKey;
    }


    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public int getAuthentication() {
        return authentication;
    }

    public void setAuthentication(int authentication) {
        this.authentication = authentication;
    }

    public int getEncryption() {
        return encryption;
    }

    public void setEncryption(int encryption) {
        this.encryption = encryption;
    }

    public String getWpaSharedKey() {
        return wpaSharedKey;
    }

    public void setWpaSharedKey(String wpaSharedKey) {
        this.wpaSharedKey = wpaSharedKey;
    }


    @Override
    public String toString() {
        return "WlanConnectionDto{" +
                "enable=" + enable +
                ", ssid='" + ssid + '\'' +
                ", mode=" + mode +
                ", channel=" + channel +
                ", standard=" + standard +
                ", authentication=" + authentication +
                ", encryption=" + encryption +
                ", wpaSharedKey='" + wpaSharedKey + '\'' +
                '}';
    }

}
