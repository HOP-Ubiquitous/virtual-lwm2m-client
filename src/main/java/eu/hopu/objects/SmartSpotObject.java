package eu.hopu.objects;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmartSpotObject extends BaseInstanceEnabler {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceObject.class);
    private String url;

    public SmartSpotObject(String url) {
        this.url = url;
    }

    @Override
    public ReadResponse read(int resourceid) {
        LOG.info("Read on SmartSpot Resource " + resourceid);
        System.out.println("Read operation received with resourceid " + resourceid);

        switch (resourceid) {
            case 0:
                return ReadResponse.success(resourceid, getPhysicalWebUrl());
            case 1:
                return ReadResponse.success(resourceid, getAvailability());
            case 2:
                return ReadResponse.success(resourceid, getSignalStrength());
            case 3:
                return ReadResponse.success(resourceid, getArea());
            case 4:
                return ReadResponse.success(resourceid, getAdvertisementInterval());
            case 5:
                return ReadResponse.success(resourceid, getDefaultChannel());
            default:
                return super.read(resourceid);
        }
    }

    @Override
    public ExecuteResponse execute(int resourceid, String params) {
        LOG.info("Execute on SmartSpot resource " + resourceid);
        return ExecuteResponse.success();
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        LOG.info("Write on SmartSpot Resource " + resourceid + " value " + value);
        return super.write(resourceid, value);
    }

    private String getPhysicalWebUrl() {
        return url;
    }

    // Not used
    private String getAvailability() {
        return "available";
    }

    private String getSignalStrength() {
        return "-30dB";
    }

    private long getArea() {
        return 1;
    }

    private long getAdvertisementInterval() {
        return 30;
    }

    private String getDefaultChannel() {
        return "0";
    }

}
