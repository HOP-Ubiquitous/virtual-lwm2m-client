package eu.hopu.objects;

import eu.hopu.observerPool.ObserverUpdater;
import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NearWifiDevicesObject extends BaseInstanceEnabler {

    private static final Random RANDOM = new Random();

    public static final int ID = 10001;
    public static final String PATH = "10001.xml";

    public NearWifiDevicesObject() {
        ObserverUpdater.INSTANCE.addObjectWithResourcesToObserve(this, 1,2,3);

//        Timer timer = new Timer("Near wifi devices");
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                fireResourcesChange(1,2,3);
//            }
//        }, 5000, 30000);
    }

    @Override
    public ReadResponse read(int resourceid) {
        switch (resourceid) {
            case 1:
                return ReadResponse.success(resourceid, getDevicesInLastMinute());
            case 2:
                return ReadResponse.success(resourceid, getDevicesInsLast10Minutes());
            case 3:
                return ReadResponse.success(resourceid, getDevicesInLastHour());
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
        return super.write(resourceid, value);
    }

    private long getDevicesInLastMinute() {
        return RANDOM.nextInt(10);
    }

    private long getDevicesInsLast10Minutes() {
        return RANDOM.nextInt(70);
    }

    private long getDevicesInLastHour() {
        return RANDOM.nextInt(220);
    }

}
