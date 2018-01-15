package eu.hopu;

import org.eclipse.leshan.client.californium.LeshanClient;

import java.util.List;

public class ShutdownHookDestroyLeshanClients {

    private List<LeshanClient> leshanClients;
    private boolean deregister;

    public ShutdownHookDestroyLeshanClients(List<LeshanClient> leshanClients, boolean deregister) {
        this.leshanClients = leshanClients;
        this.deregister = deregister;
    }


    public void execute() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                new DestroyLeshanClients(leshanClients, deregister).execute();
            }
        });
    }
}
