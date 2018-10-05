package eu.hopu.objects;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

public class ConnectivityMonitoringObject extends BaseInstanceEnabler {

    public static final int ID = 4;
    private Integer mcc;
    private Integer mnc;
    private String ipAddress;

    public ConnectivityMonitoringObject(Integer mcc, Integer mnc, String ipAddress) {
        this.mcc = mcc;
        this.mnc = mnc;
        this.ipAddress = ipAddress;
    }

    @Override
    public ReadResponse read(int resourceid) {
        switch (resourceid) {
            case 4:
                return ReadResponse.success(resourceid, getIpAddress());
            case 9:
                return ReadResponse.success(resourceid, getMnc());
            case 10:
                return ReadResponse.success(resourceid, getMcc());
            default:
                return super.read(resourceid);
        }
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        return super.write(resourceid, value);
    }

    @Override
    public ExecuteResponse execute(int resourceid, String params) {
        return super.execute(resourceid, params);
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
}
