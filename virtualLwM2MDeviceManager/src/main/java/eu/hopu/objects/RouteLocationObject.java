package eu.hopu.objects;

import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;

import java.util.Timer;
import java.util.TimerTask;

public class RouteLocationObject extends LocationObject {

    public static final int IS_IN_ROUTE = 7;
    public static final int TOGGLE_ROUTE_STATUS = 8;

    private double stepSizeLatitude;
    private double stepSizeLongitude;

    private int isInRoute;
    private boolean isDeviceGoing;

    private final Timer timer;
    private MyTimerTask myTimerTask;

    private float startLatitude;
    private float startLongitude;
    private float endLatitude;
    private float endLongitude;


    public RouteLocationObject(float startLatitude, float startLongitude, float altitude, float endLatitude, float endLongitude) {
        super(startLatitude, startLongitude, altitude);

        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;

        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;

        stepSizeLatitude = (endLatitude - getLatitude()) / 1000;
        stepSizeLongitude = (endLongitude - getLongitude()) / 1000;

        isInRoute = 0;
        isDeviceGoing = true;

        timer = new Timer("Route Timer");
        myTimerTask = new MyTimerTask(this);

        toggleRouteStatus();
    }


    @Override
    public ReadResponse read(int resourceid) {
        ReadResponse readResponse = super.read(resourceid);

        if (readResponse.equals(ReadResponse.notFound())) {
            switch (resourceid) {
                case IS_IN_ROUTE:
                    return ReadResponse.success(resourceid, isInRoute);
            }
        }
        return readResponse;
    }

    private void toggleRouteStatus() {
        if (isInRoute == 1) {
            isInRoute = 0;
            timer.cancel();
        } else {
            isInRoute = 1;
            timer.schedule(myTimerTask, 1000, 1500);
        }
    }

    private class MyTimerTask extends TimerTask {

        LocationObject locationObject;

        MyTimerTask(LocationObject locationObject) {
            this.locationObject = locationObject;
        }

        @Override
        public void run() {
            moveDevice();
            locationObject.fireResourcesChange(0, 1);
        }

        private void moveDevice() {
            if (isDeviceGoing)
                moveForward();
            else
                moveBackward();
        }

        private void moveForward() {
            locationObject.setLatitude((float) (locationObject.getLatitude() + stepSizeLatitude));
            locationObject.setLongitude((float) (locationObject.getLongitude() + stepSizeLongitude));
            if (locationObject.getLatitude() <= endLatitude && locationObject.getLongitude() <= endLongitude)
                isDeviceGoing = !isDeviceGoing;
        }

        private void moveBackward() {
            locationObject.setLatitude((float) (locationObject.getLatitude() - stepSizeLatitude));
            locationObject.setLongitude((float) (locationObject.getLongitude() - stepSizeLongitude));
            if (locationObject.getLatitude() >= startLatitude && locationObject.getLongitude() >= startLongitude)
                isDeviceGoing = !isDeviceGoing;
        }
    }
}
