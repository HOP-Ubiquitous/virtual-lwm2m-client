package eu.hopu;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.hopu.devices.DeviceBase;
import eu.hopu.objects.DeviceObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class GetDevicesFromJsonObjects {

    private final static Logger LOGGER = Logger.getLogger(GetDevicesFromJsonObjects.class.getName());

    private JsonArray jsonDevices;

    public GetDevicesFromJsonObjects(JsonArray jsonDevices) {
        this.jsonDevices = jsonDevices;
    }

    public ArrayList<DeviceBase> execute() {

        ArrayList<DeviceBase> devices = new ArrayList<>();

        for (JsonElement jsonElement : jsonDevices)
            devices.add(createDevice(jsonElement.getAsJsonObject()));

        return devices;
    }


    private DeviceBase createDevice(JsonObject jsonDevice) {
        try {
            return (DeviceBase) getDeviceConstructor(jsonDevice).newInstance(jsonDevice);
        } catch (NullPointerException e) {
            LOGGER.warning("DEVICE MODEL NOT FOUND");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DeviceBase.NULL;
    }

    private Constructor<?> getDeviceConstructor(JsonObject jsonDevice) throws NoSuchMethodException, ClassNotFoundException {
        String model = getDeviceModel(jsonDevice);
        Class<?> deviceClass = Class.forName("eu.hopu.devices." + model);
        return deviceClass.getConstructor(JsonObject.class);
    }

    private String getDeviceModel(JsonObject jsonDevice) {
        JsonObject jsonDeviceObject = jsonDevice.getAsJsonObject(DeviceObject.JSON_NAME);
        return jsonDeviceObject.get("model").getAsString();
    }
}
