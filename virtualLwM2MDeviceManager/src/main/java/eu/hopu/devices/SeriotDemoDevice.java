package eu.hopu.devices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import eu.hopu.dto.DeviceDto;
import eu.hopu.dto.LocationDto;
import eu.hopu.dto.SecurityMode;
import eu.hopu.dto.SeriotDataFlow;
import eu.hopu.objects.SeriotDataFlowObject;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.ObjectModel;

import java.util.List;

import static org.eclipse.leshan.LwM2mId.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeriotDemoDevice extends DeviceBase {

    private final static Gson gson = new Gson();
    private final SeriotDataFlow dataFlow;

    public SeriotDemoDevice() {
        super();
        dataFlow = null;
    }

    public SeriotDemoDevice(String name, String serverUrl, String serverPort, int lifetime, DeviceDto device,
                            LocationDto location, Boolean isBootstrap, SeriotDataFlow dataFlow, SecurityMode securityMode) {
        super(name, serverUrl, serverPort, lifetime, device, location, isBootstrap, securityMode);
        this.dataFlow = dataFlow;
    }

    public SeriotDemoDevice(JsonObject jsonDevice) {
        this(
                jsonDevice.get("name").getAsString(), jsonDevice.get("serverUrl").getAsString(),
                jsonDevice.get("serverPort").getAsString(),
                jsonDevice.get("lifetime").getAsInt(),
                gson.fromJson(jsonDevice.get("device"), DeviceDto.class),
                gson.fromJson(jsonDevice.get("location"), LocationDto.class),
                jsonDevice.get("isBootstrap").getAsBoolean(),
                gson.fromJson(jsonDevice.get("data"), SeriotDataFlow.class),
                gson.fromJson(jsonDevice.get("security"), SecurityMode.class)
        );
    }

    public ObjectsInitializer getObjectInitializer(List<ObjectModel> models) {
        ObjectsInitializer initializer = super.getObjectInitializer(models);

        SeriotDataFlowObject dataFlowObject = new SeriotDataFlowObject(dataFlow.getDataFlow(), dataFlow.getPeriod());

        initializer.setInstancesForObject(SeriotDataFlowObject.ID, dataFlowObject);

        return initializer;
    }

    @Override
    List<LwM2mObjectEnabler> getDeviceEnabledObjects(ObjectsInitializer objectsInitializer) {
        return objectsInitializer.create(SECURITY, SERVER, DEVICE, SeriotDataFlowObject.ID, LOCATION);
    }


}
