package eu.hopu.objects;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.response.ReadResponse;

import java.util.Date;

public class LocationObject extends BaseInstanceEnabler {

    public static final int LATITUDE = 0;
    public static final int LONGITUDE = 1;
    private float latitude;
    private float longitude;
    private float altitude;
    private Date timestamp;

    public LocationObject(float latitude, float longitude, float altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.timestamp = new Date();
    }


    @Override
    public ReadResponse read(int resourceid) {
        System.out.println("Res: " + resourceid);
        switch (resourceid) {
            case LATITUDE:
                return ReadResponse.success(resourceid, getLatitude());
            case LONGITUDE:
                return ReadResponse.success(resourceid, getLongitude());
            case 2:
                return ReadResponse.success(resourceid, getAltitude());
            case 5:
                return ReadResponse.success(resourceid, getTimestamp());
            case 6:
                return ReadResponse.success(resourceid, getSpeed());
            default:
                return super.read(resourceid);
        }
    }


    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getSpeed() {
        return 0;
    }
}
