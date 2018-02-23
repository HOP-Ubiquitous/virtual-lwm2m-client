package eu.hopu.objects;

import eu.hopu.OrionRegistration;
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

public class IpsoConcentrationObject extends BaseInstanceEnabler {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceObject.class);

    public static final int ID = 3325;
    public static final String PATH = "3325.xml";

    private static final Random RANDOM = new Random();
    private final double maxValue;
    private double minValue;
    private double sensorValue;

    public enum Gas {
        NO2, SO2, O3, H2S, CO;

        public static Gas getGasByNumber(int number)  {
            switch (number) {
                case 0:
                    return NO2;
                case 1:
                    return SO2;
                case 2:
                    return O3;
                case 3:
                    return H2S;
                case 4:
                    return CO;
                default:
                    return null;
            }
        }
        public static String getNameByGas(Gas gas) {
            switch (gas) {
                case NO2:
                    return "NO2";
                case SO2:
                    return "SO2";
                case O3:
                    return "O3";
                case H2S:
                    return "H2S";
                case CO:
                    return "CO";
                default:
                    return null;
            }
        }
    }

    public IpsoConcentrationObject(double maxValue, double minValue, double sensorValue,String name,Gas gas) {
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.sensorValue = sensorValue;

        Timer timer = new Timer("SensorDto Value");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                fireResourcesChange(5700);
                OrionRegistration.updateAttribute(name,Gas.getNameByGas(gas),null,getSensorValue());
            }
        }, 5000, 30000);
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
        double v = (RANDOM.nextDouble() * 10) % 3;
        if (sensorValue < minValue)
            sensorValue += v;
        else if (sensorValue > maxValue)
            sensorValue -= v;
        else
            sensorValue += v / 2;
        return sensorValue;
    }

}
