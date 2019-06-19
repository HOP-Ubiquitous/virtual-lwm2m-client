package eu.hopu;

import eu.hopu.observerPool.ObserverUpdater;
import org.eclipse.leshan.client.californium.LeshanClient;

import java.util.List;

import static java.lang.Thread.sleep;

public class StartLeshanClients {

    private List<LeshanClient> leshanClients;

    public StartLeshanClients(List<LeshanClient> leshanClients) {
        this.leshanClients = leshanClients;
    }


    public void execute() {
        for (LeshanClient client : leshanClients) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            client.start();

        }

        ObserverUpdater.INSTANCE.initializeObserveUpdater();

    }
}
