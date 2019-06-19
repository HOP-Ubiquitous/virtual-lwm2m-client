package eu.hopu.dto;

public class RelayDto {

    private boolean state;
    private boolean autoOffEnabled;
    private long autoOffTimeout;

    public RelayDto() {
    }

    public RelayDto(boolean state, boolean autoOffEnabled, long autoOffTimeout) {
        this.state = state;
        this.autoOffEnabled = autoOffEnabled;
        this.autoOffTimeout = autoOffTimeout;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isAutoOffEnabled() {
        return autoOffEnabled;
    }

    public void setAutoOffEnabled(boolean autoOffEnabled) {
        this.autoOffEnabled = autoOffEnabled;
    }

    public long getAutoOffTimeout() {
        return autoOffTimeout;
    }

    public void setAutoOffTimeout(long autoOffTimeout) {
        this.autoOffTimeout = autoOffTimeout;
    }

    @Override
    public String toString() {
        return "RelayDto{" +
                "state=" + state +
                ", autoOffEnabled=" + autoOffEnabled +
                ", autoOffTimeout=" + autoOffTimeout +
                '}';
    }
}
