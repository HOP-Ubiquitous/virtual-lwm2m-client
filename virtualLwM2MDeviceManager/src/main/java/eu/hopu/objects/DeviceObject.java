package eu.hopu.objects;

import eu.hopu.observerPool.ObserverUpdater;
import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

import java.text.SimpleDateFormat;
import java.util.*;

public class DeviceObject extends BaseInstanceEnabler {

    public static final String JSON_NAME = "device";

    private final String name;
    private final int batteryStatus;
    private String type;
    private int batteryLevel;

    public DeviceObject() {
        this.name = "HOP100000000000";
        this.batteryStatus = 100;
        this.batteryLevel = 99;
        this.type = "Smart Cities";
    }

    public DeviceObject(String name, String type, int batteryStatus, int batteryLevel) {
        this.name = name;
        this.type = type;
        this.batteryStatus = batteryStatus;
        this.batteryLevel = batteryLevel;

        ObserverUpdater.INSTANCE.addObjectWithResourcesToObserve(this, 9);
//        Timer timer = new Timer("DeviceObject Observer Timer");
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                fireResourcesChange(9);
//            }
//        }, 10000, 30000);
    }

    @Override
    public ReadResponse read(int resourceid) {
        switch (resourceid) {
            case 0:
                return ReadResponse.success(resourceid, getManufacturer());
            case 1:
                return ReadResponse.success(resourceid, getModelNumber());
            case 2:
                return ReadResponse.success(resourceid, getSerialNumber());
            case 3:
                return ReadResponse.success(resourceid, getFirmwareVersion());
            case 9:
                return ReadResponse.success(resourceid, getBatteryLevel());
            case 10:
                return ReadResponse.success(resourceid, getMemoryFree());
            case 11:
                Map<Integer, Long> errorCodes = new HashMap<>();
                errorCodes.put(0, getErrorCode());
                return ReadResponse.success(resourceid, errorCodes, ResourceModel.Type.INTEGER);
            case 13:
                return ReadResponse.success(resourceid, getCurrentTime());
            case 14:
                return ReadResponse.success(resourceid, getUtcOffset());
            case 15:
                return ReadResponse.success(resourceid, getTimezone());
            case 16:
                return ReadResponse.success(resourceid, getSupportedBinding());
            case 17:
                return ReadResponse.success(resourceid, getDeviceType());
            case 18:
                return ReadResponse.success(resourceid, getHardwareVersion());
            case 19:
                return ReadResponse.success(resourceid, getSoftwareVersion());
            case 20:
                return ReadResponse.success(resourceid, getBatteryStatus());
            case 21:
                return ReadResponse.success(resourceid, getMemoryTotal());
            default:
                return super.read(resourceid);
        }
    }

    @Override
    public ExecuteResponse execute(int resourceid, String params) {
        return ExecuteResponse.success();
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        return WriteResponse.success();
    }

    private int getBatteryLevel() {
        if ((batteryStatus == 0 || batteryStatus == 4) && batteryLevel > 10)
            batteryLevel--;
        else if (batteryStatus == 1 && batteryLevel < 95)
            batteryLevel++;
        else if (batteryStatus == 2)
            batteryLevel = 100;
        else if (batteryStatus == 3 || batteryStatus == 5 || batteryStatus == 6)
            batteryLevel = -1;
        return batteryLevel;
    }

    private int getBatteryStatus() {
        return batteryStatus;
    }

    // Not Used
    private String getManufacturer() {
        return this.name;
    }

    private String getModelNumber() {
        return "Model 500";
    }

    private String getSerialNumber() {
        return "LT-500-000-0001";
    }

    private String getFirmwareVersion() {
        return "1.0.0";
    }

    private long getErrorCode() {
        return 0;
    }

    private long getMemoryFree() {
        return Runtime.getRuntime().freeMemory() / 1024;
    }

    private Date getCurrentTime() {
        return new Date();
    }

    private String utcOffset = new SimpleDateFormat("X").format(Calendar.getInstance().getTime());

    private String getUtcOffset() {
        return utcOffset;
    }

    private String timeZone = TimeZone.getDefault().getID();

    private String getTimezone() {
        return timeZone;
    }

    private String getSupportedBinding() {
        return "U";
    }

    private String getDeviceType() {
        return type;
    }

    private String getHardwareVersion() {
        return "1.0.1";
    }

    private String getSoftwareVersion() {
        return "1.0.2";
    }

    private long getMemoryTotal() {
        return Runtime.getRuntime().totalMemory() / 1024;
    }

    public String getName() {
        return name;
    }

}
