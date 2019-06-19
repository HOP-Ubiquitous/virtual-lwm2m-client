package eu.hopu.objects;

import eu.hopu.observerPool.ObserverUpdater;
import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

public class RelayObject extends BaseInstanceEnabler {

    public static final int ID = 32983;

    private boolean state;
    private boolean autoOffEnabled;
    private long autoOffTimeout;

    public RelayObject(boolean state, boolean autoOffEnabled, long autoOffTimeout) {
        this.state = state;
        this.autoOffEnabled = autoOffEnabled;
        this.autoOffTimeout = autoOffTimeout;
        ObserverUpdater.INSTANCE.addObjectWithResourcesToObserve(this, 0);
        ObserverUpdater.INSTANCE.addObjectWithResourcesToObserve(this, 1);
        ObserverUpdater.INSTANCE.addObjectWithResourcesToObserve(this, 2);
    }

    @Override
    public ReadResponse read(int resourceid) {
        switch (resourceid) {
            case 0:
                return ReadResponse.success(resourceid, isState());
            case 1:
                return ReadResponse.success(resourceid, isAutoOffEnabled());
            case 2:
                return ReadResponse.success(resourceid, getAutoOffTimeout());
            default:
                return super.read(resourceid);
        }
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        switch (resourceid) {
            case 0:
                this.state = (boolean) value.getValue();
                return WriteResponse.success();
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
        return super.execute(resourceid, params);
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

    public void setAutoOffTimeout(int autoOffTimeout) {
        this.autoOffTimeout = autoOffTimeout;
    }
}
