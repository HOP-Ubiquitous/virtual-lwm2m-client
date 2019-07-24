package eu.hopu.devices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import eu.hopu.dto.DeviceDto;
import eu.hopu.dto.LocationDto;
import eu.hopu.dto.RelayDto;
import eu.hopu.dto.SecurityMode;
import eu.hopu.objects.RelayObject;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.ObjectModel;

import java.util.List;

import static org.eclipse.leshan.LwM2mId.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RelayControllerDevice extends DeviceBase {

    private final static Gson gson = new Gson();
    private List<RelayDto> relays;

    public RelayControllerDevice() {
        super();
    }

    public RelayControllerDevice(String name, String serverUrl, String serverPort, int lifetime, DeviceDto device,
                                 LocationDto location, Boolean isBootstrap, List<RelayDto> relays, SecurityMode securityMode) {
        super(name, serverUrl, serverPort, lifetime, device, location, isBootstrap, securityMode);
        this.relays = relays;
    }

    public RelayControllerDevice(JsonObject jsonDevice) {
        this(
                jsonDevice.get("name").getAsString(), jsonDevice.get("serverUrl").getAsString(),
                jsonDevice.get("serverPort").getAsString(),
                jsonDevice.get("lifetime").getAsInt(),
                gson.fromJson(jsonDevice.get("device"), DeviceDto.class),
                gson.fromJson(jsonDevice.get("location"), LocationDto.class),
                jsonDevice.get("isBootstrap").getAsBoolean(),
                (List<RelayDto>) gson.fromJson(
                        jsonDevice.get("relays"),
                        new TypeToken<List<RelayDto>>() {
                        }.getType()
                ),
                gson.fromJson(jsonDevice.get("security"), SecurityMode.class)
        );
    }

    public ObjectsInitializer getObjectInitializer(List<ObjectModel> models) {
        ObjectsInitializer initializer = super.getObjectInitializer(models);

        List<RelayDto> relays = getRelays();
        if (relays != null) {
            RelayObject[] relayObject = new RelayObject[relays.size()];
            int index = 0;
            for (RelayDto relay : relays) {
                relayObject[index++] = new RelayObject(
                        relay.getState(),
                        relay.isAutoOffEnabled(),
                        relay.getAutoOffTimeout()
                );
            }
            initializer.setInstancesForObject(RelayObject.ID, relayObject);
        }

        return initializer;
    }

    @Override
    List<LwM2mObjectEnabler> getDeviceEnabledObjects(ObjectsInitializer objectsInitializer) {
        return objectsInitializer.create(SECURITY, SERVER, DEVICE, RelayObject.ID, LOCATION);
    }

    public List<RelayDto> getRelays() {
        return relays;
    }

    public void setRelays(List<RelayDto> relays) {
        this.relays = relays;
    }
}
