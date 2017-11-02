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
            case 0:
                System.out.println("Reading ID");
                return ReadResponse.success(resourceid, getId());
            case 1:
                System.out.println("Reading Name");
                return ReadResponse.success(resourceid, getName());
            case 2:
                System.out.println("Reading Url");
                return ReadResponse.success(resourceid, getUrl());
            case 3:
                System.out.println("Reading Status");
                return ReadResponse.success(resourceid, getStatus());
            default:
                return super.read(resourceid);
        }
    }

    @Override
    public ExecuteResponse execute(int resourceid, String params) {
        if(resourceid == 4){
            id = "service id";
            name = "service name";
            url = "service url";
            status = 0;
            return ExecuteResponse.success();
        }
        return ExecuteResponse.badRequest("Resource not found");
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        switch (resourceid) {
            case 0:
                System.out.println("VALUE: " + value);
                id = value.getValue().toString();
                return WriteResponse.success();
            case 1:
                System.out.println("VALUE: " + value);
                name = value.getValue().toString();
                return WriteResponse.success();
            case 2:
                System.out.println("VALUE: " + value);
                url = value.getValue().toString();
                return WriteResponse.success();
            case 3:
                System.out.println("VALUE: " + value);
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



