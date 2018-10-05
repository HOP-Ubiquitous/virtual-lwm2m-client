package eu.hopu.objects;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APNConnectionObject extends BaseInstanceEnabler {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceObject.class);

    public static final int ID = 11;
    public static final String PATH = "11.xml";

    private String name;
    private int apn;
    private boolean enable;
    private String authentication;
    private String username;
    private String secret;
    private String pin;

    public APNConnectionObject(){
        this("apn_connection",3,false,"0","username","password","1234");
    }

    public APNConnectionObject(String name, int apn, boolean enable, String authentication, String username, String secret, String pin) {
        this.name = name;
        this.apn = apn;
        this.enable = enable;
        this.authentication = authentication;
        this.username = username;
        this.secret = secret;
        this.pin = pin;
    }

    @Override
    public ReadResponse read(int resourceid) {
        LOG.info("Read on SmartSpot Resource " + resourceid);
        System.out.println("Read operation received with resourceid " + resourceid+ " in object "+ID);

        switch (resourceid) {
            case 0:
                return ReadResponse.success(resourceid, 0);
            case 1:
                return ReadResponse.success(resourceid,getName());
            case 3:
                return ReadResponse.success(resourceid,isEnable());
            case 4:
                return ReadResponse.success(resourceid,getApn());
            case 5:
                return ReadResponse.success(resourceid,getAuthentication());
            case 6:
                return ReadResponse.success(resourceid,getUsername());
            case 26:
                return ReadResponse.success(resourceid,getSecret());
            case 27:
                return ReadResponse.success(resourceid,getPin());
            default:
                return super.read(resourceid);
        }
    }

    public String getName() {
        return name;
    }

    public int getApn() {
        return apn;
    }

    public boolean isEnable() {
        return enable;
    }

    public String getAuthentication() {
        return authentication;
    }

    public String getUsername() {
        return username;
    }

    public String getSecret() {
        return secret;
    }

    public String getPin() {
        return pin;
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
