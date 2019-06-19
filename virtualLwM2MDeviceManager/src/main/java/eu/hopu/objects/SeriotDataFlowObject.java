package eu.hopu.objects;

import eu.hopu.observerPool.ObserverUpdater;
import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeriotDataFlowObject extends BaseInstanceEnabler {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceObject.class);

    public static final int ID = 91000;
    private final char[] dataFlow;
    private final int period;
    private int index;

    public SeriotDataFlowObject(String dataFlow, int period) {
        this.dataFlow = dataFlow.toCharArray();
        this.period = period;
        this.index = 0;

        ObserverUpdater.INSTANCE.addObjectWithResourcesToObserve(period, this, 0);
    }

    @Override
    public ReadResponse read(int resourceid) {
        LOG.info("Read on SeriotDataFlowObject Resource " + resourceid);
        switch (resourceid) {
            case 0:
                return ReadResponse.success(resourceid, getDataFlow());
            case 1:
                return ReadResponse.success(resourceid, getPeriod());
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

    public String getDataFlow() {
        String value = String.valueOf(dataFlow[index]);
        index = (index + 1) % dataFlow.length;
        return value;
    }

    public int getPeriod() {
        return period;
    }
}
