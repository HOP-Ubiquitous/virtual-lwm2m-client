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


    @Override
    public ReadResponse read(int resourceid) {
        return super.read(resourceid);
    }

    @Override
    public ExecuteResponse execute(int resourceid, String params) {
        return ExecuteResponse.success();
    }


    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        return super.write(resourceid, value);
    }
}
