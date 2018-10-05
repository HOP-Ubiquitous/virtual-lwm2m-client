package eu.hopu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APNConnectionDto {
    private String name;
    private int apn;
    private boolean enable;
    private String authentication;
    private String user;
    private String secret;
    private String pin;

    public APNConnectionDto() {
    }

    public APNConnectionDto(String name, int apn, boolean enable, String authentication, String user, String secret, String pin) {
        this.name = name;
        this.apn = apn;
        this.enable = enable;
        this.authentication = authentication;
        this.user = user;
        this.secret = secret;
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getApn() {
        return apn;
    }

    public void setApn(int apn) {
        this.apn = apn;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "APNConnectionDto{" +
                "name='" + name + '\'' +
                ", apn='" + apn + '\'' +
                ", enable=" + enable +
                ", authentication=" + authentication +
                ", user='" + user + '\'' +
                ", secret='" + secret + '\'' +
                ", pin='" + pin + '\'' +
                '}';
    }
}
