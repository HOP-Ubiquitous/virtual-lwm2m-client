package eu.hopu.observerPool;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ObserverUpdater {


    public static ObserverUpdater INSTANCE = new ObserverUpdater();

    private Timer timer;
    private List<ResourcesToObserveDto> resourcesToObserve;

    public ObserverUpdater() {
        timer = new Timer();
        resourcesToObserve = new LinkedList<>();
    }

    public void addObjectWithResourcesToObserve(BaseInstanceEnabler enabler, int... resources) {
        resourcesToObserve.add(new ResourcesToObserveDto(enabler, resources));
    }

    public void initializeObserveUpdater() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (ResourcesToObserveDto element : resourcesToObserve) {
                    element.getEnabler().fireResourcesChange(element.getResources());
                }
            }
        }, 10000, 30000);
    }
}
