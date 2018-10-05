package eu.hopu.observerPool;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;

public class ResourcesToObserveDto {

    private BaseInstanceEnabler enabler;
    private int[] resources;

    public ResourcesToObserveDto(BaseInstanceEnabler enabler, int[] resources) {
        this.enabler = enabler;
        this.resources = resources;
    }

    public BaseInstanceEnabler getEnabler() {
        return enabler;
    }

    public int[] getResources() {
        return resources;
    }

    public void setEnabler(BaseInstanceEnabler enabler) {
        this.enabler = enabler;
    }

    public void setResources(int[] resources) {
        this.resources = resources;
    }
}
