package eu.hopu.objects;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FortikaServiceObject extends BaseInstanceEnabler {

    private static final Logger LOG = LoggerFactory.getLogger(FortikaServiceObject.class);

    private static final int ID = 0;
    private static final int NAME = 1;
    private static final int URL = 2;
    private static final int STATUS = 3;
    private static final int RESET = 4;

    private static final int STATUS_OFF = 0;
    private static final int STATUS_ON = 1;

    private String id;
    private String name;
    private String url;
    private int status;


    public FortikaServiceObject(String id, String name, String url, int status) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.status = status;
    }

    public FortikaServiceObject() {
    }


    @Override
    public ReadResponse read(int resourceid) {
        switch (resourceid) {

            case ID:
                System.out.println("Reading Id");
                return ReadResponse.success(resourceid, getId());
            case NAME:
                System.out.println("Reading Name");
                return ReadResponse.success(resourceid, getName());
            case URL:
                System.out.println("Reading Url");
                return ReadResponse.success(resourceid, getUrl());
            case STATUS:
                System.out.println("Reading Status");
                return ReadResponse.success(resourceid, getStatus());
            default:
                return super.read(resourceid);
        }
    }


    @Override
    public ExecuteResponse execute(int resourceid, String params) {
        if (resourceid == RESET) {
            new Thread(new RebootingService()).start();
            return ExecuteResponse.success();
        }
        return ExecuteResponse.badRequest("Resource not found");
    }

    public class RebootingService implements Runnable {

        public void run() {
            status = STATUS_OFF;
            fireResourcesChange(STATUS);
            System.out.println("Doing reboot task...");
            try {
                Thread.sleep(4000);
                System.out.println("Rebooted");
                status = STATUS_ON;
                fireResourcesChange(STATUS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        switch (resourceid) {
            case ID:
                System.out.println("Writing id value: " + value);
                id = value.getValue().toString();
                return WriteResponse.success();
            case NAME:
                System.out.println("Writing name value: " + value);
                name = value.getValue().toString();
                return WriteResponse.success();
            case URL:
                System.out.println("Writing url value: " + value);
                url = value.getValue().toString();
                return WriteResponse.success();
            case STATUS:
                System.out.println("Writing status value: " + value);
                status = Integer.parseInt(value.getValue().toString());
                return WriteResponse.success();
            default:
                return super.write(resourceid, value);
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}



