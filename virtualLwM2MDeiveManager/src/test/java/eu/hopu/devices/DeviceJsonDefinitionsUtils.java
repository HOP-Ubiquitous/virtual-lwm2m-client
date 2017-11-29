package eu.hopu.devices;

public class DeviceJsonDefinitionsUtils {


    public static String getSmartSpotDefinition() {
        return "" +
                "{\n" +
                "   \"name\": \"HOP010203040506\",\n" +
                "   \"serverUrl\": \"coap://glueandblue.com\",\n" +
                "   \"isBootstrap\": \"false\",\n" +
                "   \"serverPort\": \"5683\",\n" +
                "   \"lifetime\": 30,\n" +
                "   \"device\": {\n" +
                "       \"batteryStatus\": 0,\n" +
                "       \"batteryLevel\": 55,\n" +
                "       \"model\": \"SmartSpot\"\n" +
                "   },\n" +
                "   \"location\": {\n" +
                "       \"latitude\": 38.0770456,\n" +
                "       \"longitude\": -1.2712549000000308,\n" +
                "       \"altitude\": 1\n" +
                "   },\n" +
                "   \"temperatures\": [\n" +
                "       {\n" +
                "           \"maxValue\": 25,\n" +
                "           \"minValue\": 19,\n" +
                "            \"sensorValue\": 22\n" +
                "       },\n" +
                "       {\n" +
                "           \"maxValue\": 27,\n" +
                "           \"minValue\": 19,\n" +
                "           \"sensorValue\": 22\n" +
                "       }\n" +
                "   ],\n" +
                "   \"humidities\": [\n" +
                "       {\n" +
                "           \"maxValue\": 60,\n" +
                "           \"minValue\": 30,\n" +
                "           \"sensorValue\": 45\n" +
                "       },\n" +
                "       {\n" +
                "           \"maxValue\": 60,\n" +
                "           \"minValue\": 30,\n" +
                "           \"sensorValue\": 42\n" +
                "       }\n" +
                "   ],\n" +
                "   \"loudness\": {\n" +
                "       \"maxValue\": 20,\n" +
                "       \"minValue\": 10,\n" +
                "       \"sensorValue\": 15\n" +
                "   },\n" +
                "   \"gasses\": [\n" +
                "       {\n" +
                "           \"maxValue\": 19,\n" +
                "           \"minValue\": 0,\n" +
                "           \"sensorValue\": 6\n" +
                "      },\n" +
                "      {\n" +
                "           \"maxValue\": 52,\n" +
                "           \"minValue\": 0,\n" +
                "           \"sensorValue\": 17\n" +
                "      },\n" +
                "      {\n" +
                "           \"maxValue\": 56,\n" +
                "           \"minValue\": 0,\n" +
                "           \"sensorValue\": 21\n" +
                "      },\n" +
                "      {\n" +
                "           \"maxValue\": 100,\n" +
                "           \"minValue\": 0,\n" +
                "           \"sensorValue\": 40\n" +
                "      },\n" +
                "      {\n" +
                "           \"maxValue\": 10,\n" +
                "           \"minValue\": 0,\n" +
                "           \"sensorValue\": 2\n" +
                "      }\n" +
                "   ],\n" +
                "   \"physicalUrl\": \"http://laperalimonera.com\"," +
                "   \"crowdMonitoring\": true\n" +
                "}\n";
    }

}
