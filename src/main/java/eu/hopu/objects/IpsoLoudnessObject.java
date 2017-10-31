package eu.hopu.objects;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class IpsoLoudnessObject extends BaseInstanceEnabler {

    private static final Random RANDOM = new Random();
    private final double maxValue;
    private double minValue;
    private double sensorValue;

    public IpsoLoudnessObject(double maxValue, double minValue, double sensorValue) {
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.sensorValue = sensorValue;

        Timer timer = new Timer("Sensor Value");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                fireResourcesChange(5700);
            }
        }, 13000, 30000);
    }

    @Override
    public ReadResponse read(int resourceid) {
        return ReadResponse.success(resourceid, getSensorValue());
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
        if (RANDOM.nextInt(11) > 8)
            return 60 + RANDOM.nextInt(20);
        else {
            double v = Math.abs(RANDOM.nextDouble());
            if (sensorValue < minValue)
                sensorValue += v;
            else if (sensorValue > maxValue)
                sensorValue -= v;
            else
                sensorValue += v / 2;
            return sensorValue;
        }
    }
}
