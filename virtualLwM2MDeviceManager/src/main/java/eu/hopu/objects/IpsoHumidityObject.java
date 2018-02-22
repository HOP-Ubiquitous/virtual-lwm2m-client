package eu.hopu.objects;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class IpsoHumidityObject extends BaseInstanceEnabler {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceObject.class);

    public static final int ID = 3304;
    public static final String PATH = "3304.xml";

    private static final Random RANDOM = new Random();
    private final double maxValue;
    private double minValue;
    private double sensorValue;

    public IpsoHumidityObject(double maxValue, double minValue, double sensorValue) {
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.sensorValue = sensorValue;

        Timer timer = new Timer("SensorDto Value");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                fireResourcesChange(5700);
                // TODO Actualizar attibuto relativeHumidity con value = getSensorValue()
            }
        }, 11000, 30000);
    }

    @Override
    public ReadResponse read(int resourceid) {
        LOG.info("Read on SmartSpot Resource " + resourceid);
        switch (resourceid) {
            case 5700:
                return ReadResponse.success(resourceid, getSensorValue());
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
        return super.write(resourceid, value);
    }

    private double getSensorValue() {
        double v = RANDOM.nextDouble() * 10;
        if (sensorValue < minValue)
            sensorValue += v;
        else if (sensorValue > maxValue)
            sensorValue -= v;
        else
            sensorValue += v / 2;
        return sensorValue;
    }
}
