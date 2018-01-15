package eu.hopu.dto;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LocationDtoTest {

    private String locationWithRoute = "" +
            "{\n" +
            "       \"latitude\": 38.0770456,\n" +
            "       \"longitude\": -1.2712549000000308,\n" +
            "       \"altitude\": 1,\n" +
            "       \"route\": [{\n" +
            "           \"latitude\": 38.0770456,\n" +
            "           \"longitude\": -1.2712549000000308,\n" +
            "           \"altitude\": 1\n" +
            "       }, {\n" +
            "           \"latitude\": 38.0770456,\n" +
            "           \"longitude\": -1.2712549000000308,\n" +
            "           \"altitude\": 1\n" +
            "       }]\n" +
            "}";

    private Gson gson;

    @Before
    public void setUp() {
        gson = new Gson();
    }

    @Test
    public void given_a_route_the_dto_return_true_to_has_route() throws Exception {

        LocationDto locationDto = gson.fromJson(locationWithRoute, LocationDto.class);

        assertEquals(38.0770456, locationDto.getLatitude(), 0.001);
        assertEquals(-1.2712549000000308, locationDto.getLongitude(), 0.001);
        assertEquals(1, locationDto.getAltitude(), 0.001);

        assertTrue(locationDto.hasRoute());
        assertEquals(2, locationDto.getRoute().size());
        assertEquals(38.0770456, locationDto.getRoute().get(0).getLatitude(), 0.001);
        assertEquals(-1.2712549000000308, locationDto.getRoute().get(0).getLongitude(), 0.001);
        assertEquals(1, locationDto.getRoute().get(0).getAltitude(), 0.001);
    }
}
