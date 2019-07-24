package eu.hopu.objects;

import eu.hopu.observerPool.ObserverUpdater;
import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

import static java.lang.Thread.sleep;

public class RelayObject extends BaseInstanceEnabler {

    public static final int ID = 32983;

    private String state;
    private boolean autoOffEnabled;
    private long autoOffTimeout;

    public RelayObject(String state, boolean autoOffEnabled, long autoOffTimeout) {
        this.state = state;
        this.autoOffEnabled = autoOffEnabled;
        this.autoOffTimeout = autoOffTimeout;
        ObserverUpdater.INSTANCE.addObjectWithResourcesToObserve(this, 0);
    }

    @Override
    public ReadResponse read(int resourceid) {
        switch (resourceid) {
            case 0:
                return ReadResponse.success(resourceid, getState());
            case 1:
                return ReadResponse.success(resourceid, isAutoOffEnabled());
            case 2:
                return ReadResponse.success(resourceid, getAutoOffTimeout());
            default:
                return ReadResponse.methodNotAllowed();
        }
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        switch (resourceid) {
            case 1:
                this.autoOffEnabled = (boolean) value.getValue();
                return WriteResponse.success();
            case 2:
                this.autoOffTimeout = (int) value.getValue();
                return WriteResponse.success();
            default:
                return super.write(resourceid, value);
        }
    }

    @Override
    public ExecuteResponse execute(int resourceid, String params) {
        if (resourceid == 3) {
            setState("on");
            fireResourcesChange(0);
            if (isAutoOffEnabled()) {
                new Thread(
                        () -> {
                            try {
                                sleep(getAutoOffTimeout());
                                setState("off");
                                fireResourcesChange(0);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).run();
            }
            return ExecuteResponse.success();
        } else if (resourceid == 4) {
            setState("off");
            fireResourcesChange(0);
            return ExecuteResponse.success();
        }
        return ExecuteResponse.methodNotAllowed();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
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

    public void setAutoOffTimeout(int autoOffTimeout) {
        this.autoOffTimeout = autoOffTimeout;
    }
}
