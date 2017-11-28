package eu.hopu.devices;


import eu.hopu.dto.DeviceDto;
import eu.hopu.dto.LocationDto;
import eu.hopu.objects.DeviceObject;
import eu.hopu.objects.LocationObject;
import eu.hopu.objects.RouteLocationObject;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.request.BindingMode;

import java.util.ArrayList;
import java.util.List;

import static org.eclipse.leshan.LwM2mId.*;
import static org.eclipse.leshan.client.object.Security.noSec;
import static org.eclipse.leshan.client.object.Security.noSecBootstap;

public abstract class DeviceBase {


    public static final DeviceBase NULL = new DeviceBase() {
        @Override
        List<LwM2mObjectEnabler> getDeviceEnabledObjects(ObjectsInitializer objectsInitializer) {
            return new ArrayList<>();
        }
    };

    public static int DEVICE_PORT = 40000;

    private String name;
    private String serverUrl;
    private String serverPort;
    private int lifetime;
    private DeviceDto deviceDto;
    private static final String LOCAL_ADDRESS = "0.0.0.0";
    private int localPort;
    private LocationDto location;

    private boolean isBootstrap;


    public DeviceBase() {
    }

    public DeviceBase(String name, String serverUrl, String serverPort, int lifetime, DeviceDto device, LocationDto location, Boolean isBootstrap) {
        this.name = name;
        this.serverUrl = serverUrl;
        this.serverPort = serverPort;
        this.lifetime = lifetime;
        this.deviceDto = device;
        this.localPort = getFreePort();
        this.location = location;
        this.isBootstrap = isBootstrap;
    }


    private synchronized int getFreePort() {
        return DEVICE_PORT++;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public DeviceDto getDevice() {
        return deviceDto;
    }

    public void setDevice(DeviceDto deviceDto) {
        this.deviceDto = deviceDto;
    }

    public String getLocalAddress() {
        return LOCAL_ADDRESS;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public boolean isBootstrap() {
        return isBootstrap;
    }

    public void setBootstrap(boolean bootstrap) {
        isBootstrap = bootstrap;
    }

    public LeshanClient getLeshanClient(List<ObjectModel> models) {

        ObjectsInitializer objectsInitializer = getObjectInitializer(models);
        List<LwM2mObjectEnabler> enablers = getDeviceEnabledObjects(objectsInitializer);

        LeshanClientBuilder builder = new LeshanClientBuilder(getName());
        builder.setLocalAddress(getLocalAddress(), getLocalPort());
        builder.setObjects(enablers);

        return builder.build();
    }

    public ObjectsInitializer getObjectInitializer(List<ObjectModel> models) {
        ObjectsInitializer initializer = new ObjectsInitializer(new LwM2mModel(models));

        if (this.isBootstrap)
            initializer.setInstancesForObject(SECURITY, noSecBootstap(getServerUrl() + ":" + getServerPort()));
        else initializer.setInstancesForObject(SECURITY, noSec(getServerUrl() + ":" + getServerPort(), 123));

        initializer.setInstancesForObject(SERVER, new Server(123, getLifetime(), BindingMode.U, false));
        initializer.setInstancesForObject(DEVICE, new DeviceObject(name, deviceDto.getBatteryStatus(), deviceDto.getBatteryLevel()));

        LocationDto location = getLocation();
        if (location != null)
            initLocationObject(initializer, location);

        return initializer;
    }

    private void initLocationObject(ObjectsInitializer initializer, LocationDto location) {
        if (location.hasRoute() && location.getRoute().size() > 0)
            initializer.setInstancesForObject(LOCATION,
                    new RouteLocationObject(
                            location.getLatitude(),
                            location.getLongitude(),
                            location.getAltitude(),
                            location.getRoute().get(0).getLatitude(),
                            location.getRoute().get(0).getLongitude()
                    )
            );
        else
            initializer.setInstancesForObject(LOCATION, new LocationObject(location.getLatitude(), location.getLongitude(), location.getAltitude()));
    }

    abstract List<LwM2mObjectEnabler> getDeviceEnabledObjects(ObjectsInitializer objectsInitializer);

    public String getModel() {
        return this.getClass().getSimpleName();
    }
}
