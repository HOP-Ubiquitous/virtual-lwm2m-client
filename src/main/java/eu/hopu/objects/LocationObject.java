package eu.hopu.objects;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.response.ReadResponse;

import java.util.Date;

public class LocationObject extends BaseInstanceEnabler {

    private float latitude;
    private float longitude;
    private float altitude;
    private Date timestamp;

    public LocationObject(float latitude, float longitude, float altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        timestamp = new Date();
    }

    @Override
    public ReadResponse read(int resourceid) {
        switch (resourceid) {
            case 0:
                return ReadResponse.success(resourceid, getLatitude());
            case 1:
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

    private float getLatitude() {
        return latitude;
    }

    private float getLongitude() {
        return longitude;
    }

    private Date getTimestamp() {
        return timestamp;
    }

    private float getAltitude() {
        return altitude;
    }

    private float getSpeed() {
        return 0;
    }
}
