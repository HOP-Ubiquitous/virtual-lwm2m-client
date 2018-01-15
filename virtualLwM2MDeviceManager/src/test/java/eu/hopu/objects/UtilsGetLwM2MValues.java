package eu.hopu.objects;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mNodeVisitor;
import org.eclipse.leshan.core.node.LwM2mObject;
import org.eclipse.leshan.core.node.LwM2mObjectInstance;
import org.eclipse.leshan.core.node.LwM2mResource;

public class UtilsGetLwM2MValues implements LwM2mNodeVisitor {

    private LwM2mObject lwM2mObject;
    private LwM2mObjectInstance lwM2mObjectInstance;
    private LwM2mResource lwM2mResource;

    @Override
    public void visit(LwM2mObject object) {
        lwM2mObject = object;
    }

    @Override
    public void visit(LwM2mObjectInstance instance) {
        lwM2mObjectInstance = instance;
    }

    @Override
    public void visit(LwM2mResource resource) {
        lwM2mResource = resource;
    }


    public Object getResourceValue(BaseInstanceEnabler instance, int resourceId) {
        instance.read(resourceId).getContent().accept(this);
        return lwM2mResource.getValue();
    }

}
