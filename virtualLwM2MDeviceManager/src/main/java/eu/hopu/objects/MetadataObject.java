package eu.hopu.objects;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

public class MetadataObject extends BaseInstanceEnabler {

    public static final int ID = 32970;

    private final String name;
    private final String place;
    private final String image;

    public MetadataObject() {
        this.name = "";
        this.place = "";
        this.image = "";
    }

    public MetadataObject(String name, String place, String image) {
        this.name = name;
        this.place = place;
        this.image = image;
    }

    @Override
    public ReadResponse read(int resourceid) {
        switch (resourceid) {
            case 0:
                return ReadResponse.success(resourceid, getName());
            case 1:
                return ReadResponse.success(resourceid, getPlace());
            case 2:
                return ReadResponse.success(resourceid, getImage());
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
        return WriteResponse.success();
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getImage() {
        return image;
    }
}
