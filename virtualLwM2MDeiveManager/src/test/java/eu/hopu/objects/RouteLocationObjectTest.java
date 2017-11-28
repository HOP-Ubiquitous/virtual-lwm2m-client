package eu.hopu.objects;

import com.google.gson.Gson;
import eu.hopu.dto.LocationDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class RouteLocationObjectTest {

    private static final int LONGITUDE = 1;
    private static final int LATITUDE = 0;

    private static final String LOCATION_WITH_ROUTE = "" +
            "{\n" +
            "       \"latitude\": 19.42422,\n" +
            "       \"longitude\": -99.13322,\n" +
            "       \"altitude\": 1,\n" +
            "       \"route\": [{\n" +
            "           \"latitude\": 19.36,\n" +
            "           \"longitude\": -99.143,\n" +
            "           \"altitude\": 1\n" +
            "       }]\n" +
            "}";


    private LocationDto locationDto;
    private UtilsGetLwM2MValues utilsGetLwM2MValues;

    @Before
    public void setUp() {
        Gson gson = new Gson();
        locationDto = gson.fromJson(LOCATION_WITH_ROUTE, LocationDto.class);
        utilsGetLwM2MValues = new UtilsGetLwM2MValues();
    }

    @Test
    public void given_a_route_dto_create_a_RouteObject() throws Exception {
        LocationObject routeLocationObject = new RouteLocationObject(
                locationDto.getLatitude(),
                locationDto.getLongitude(),
                locationDto.getAltitude(),
                locationDto.getRoute().get(0).getLatitude(),
                locationDto.getRoute().get(0).getLongitude());

        double latitude = (double) utilsGetLwM2MValues.getResourceValue(routeLocationObject, LATITUDE);
        double longitude = (double) utilsGetLwM2MValues.getResourceValue(routeLocationObject, LATITUDE);
        Thread.sleep(5000);
        double newLatitude = (double) utilsGetLwM2MValues.getResourceValue(routeLocationObject, LATITUDE);
        double newLongitude = (double) utilsGetLwM2MValues.getResourceValue(routeLocationObject, LATITUDE);

        assertNotEquals(latitude, newLatitude);
        assertNotEquals(longitude, newLongitude);
    }


}
