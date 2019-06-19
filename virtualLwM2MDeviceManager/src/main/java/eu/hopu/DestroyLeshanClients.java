package eu.hopu;

import eu.hopu.observerPool.ObserverUpdater;
import org.eclipse.leshan.client.californium.LeshanClient;

import java.util.List;

public class DestroyLeshanClients {

    private List<LeshanClient> leshanClients;
    private boolean deregister;

    public DestroyLeshanClients(List<LeshanClient> leshanClients, boolean deregister) {
        this.leshanClients = leshanClients;
        this.deregister = deregister;
    }


    public void execute() {
        ObserverUpdater.INSTANCE.cancel();
        for (LeshanClient leshanClient : leshanClients) {
            leshanClient.destroy(deregister);
        }
    }
}
