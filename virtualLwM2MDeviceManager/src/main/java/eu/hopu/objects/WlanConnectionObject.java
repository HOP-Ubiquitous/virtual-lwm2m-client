package eu.hopu.objects;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WlanConnectionObject extends BaseInstanceEnabler {


    private static final Logger LOG = LoggerFactory.getLogger(DeviceObject.class);

    public static final int ID = 12;
    public static final String PATH = "12.xml";

    private boolean enable;
    private String ssid;
    private int mode;
    private int channel;
    private int standard;
    private int authentication;
    private int encryption;
    private String wpaSharedKey;

    public WlanConnectionObject() {
        this(true,"defaultSSAP", 1, 2, 1, 0, 0);
    }

    public WlanConnectionObject(boolean enable, String ssid, int mode, int channel, int standard, int authentication, int encryption) {
        this.enable = enable;
        this.ssid = ssid;
        this.mode = mode;
        this.channel = channel;
        this.standard = standard;
        this.authentication = authentication;
        this.encryption = encryption;
    }

    @Override
    public ReadResponse read(int resourceid) {
        LOG.info("Read on SmartSpot Resource " + resourceid);
        System.out.println("Read operation received with resourceid " + resourceid+ " in object "+ID);

        switch (resourceid) {
            case 0:
                return ReadResponse.success(resourceid,0);
            case 1:
                return ReadResponse.success(resourceid,isEnable());
            case 5:
                return ReadResponse.success(resourceid,getSsid());
            case 8:
                return ReadResponse.success(resourceid,getMode());
            case 9:
                return ReadResponse.success(resourceid,getChannel());
            case 14:
                return ReadResponse.success(resourceid,getStandard());
            case 15:
                return ReadResponse.success(resourceid,getAuthentication());
            case 16:
                return ReadResponse.success(resourceid,getEncryption());
            default:
                return super.read(resourceid);
        }
    }

    public boolean isEnable() {
        return enable;
    }

    public String getSsid() {
        return ssid;
    }

    public int getMode() {
        return mode;
    }

    public int getChannel() {
        return channel;
    }

    public int getStandard() {
        return standard;
    }

    public int getAuthentication() {
        return authentication;
    }

    public int getEncryption() {
        return encryption;
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
}
