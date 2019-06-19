package eu.hopu.observerPool;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;

import java.util.*;

public class ObserverUpdater {


    public static ObserverUpdater INSTANCE = new ObserverUpdater();

    private Timer timer;
    private Map<Integer, List<ResourcesToObserveDto>> resourcesByPeriod;

    public ObserverUpdater() {
        timer = new Timer();
        resourcesByPeriod = new HashMap<>();
    }

    public void addObjectWithResourcesToObserve(int period, BaseInstanceEnabler enabler, int... resources) {
        if (!resourcesByPeriod.containsKey(period))
            resourcesByPeriod.put(period, new LinkedList<>());
        List<ResourcesToObserveDto> resourcesToObserve = resourcesByPeriod.get(period);
        resourcesToObserve.add(new ResourcesToObserveDto(enabler, resources));
    }

    public void addObjectWithResourcesToObserve(BaseInstanceEnabler enabler, int... resources) {
        addObjectWithResourcesToObserve(5000, enabler, resources);
    }

    public void initializeObserveUpdater() {

        for (Integer period : resourcesByPeriod.keySet()) {
            List<ResourcesToObserveDto> resourcesToObserve = resourcesByPeriod.get(period);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    for (ResourcesToObserveDto element : resourcesToObserve) {
                        element.getEnabler().fireResourcesChange(element.getResources());
                    }
                }
            }, 5000, period);
        }

    }

    public void cancel() {
        timer.cancel();
    }
}
