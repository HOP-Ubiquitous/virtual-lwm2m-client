package eu.hopu;

import com.orange.ngsi2.client.Ngsi2Client;
import com.orange.ngsi2.model.Attribute;
import com.orange.ngsi2.model.Entity;
import org.springframework.web.client.AsyncRestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class OrionRegistration {

    private static final String ID = "_airQualityObserver";

    private static String ORION_URL = (System.getenv("ORION_URL") != null) ? System.getenv("ORION_URL") :  "http://192.168.1.250:1026";

    private String idEntity;

    private static String type;

    private String source;

    private String attributeIds[] = {
            "source", "dateObserved","address", "location", "temperature", "relativeHumidity",
            "NO2", "SO2", "O3", "H2S", "CO", "NO2_Level", "SO2_Level", "O3_Level", "H2S_Level", "CO_Level"
    };

    //valores de atributos a partir de temperature
    private Object attributesValues[] = {
            30,60,6,17,21,40,2,"good","good","good","good","good"
    };

    private  OrionRegistration() {
            idEntity = null;
            type = "AirQualityObserver";
    }

    public OrionRegistration(String idEntity, String source) {
        this();
        this.idEntity = idEntity;
        this.source = source;
        System.out.println(ORION_URL);
        for (String s: System.getenv().keySet()) {
            System.out.println(s + " → " + System.getenv(s));
        }
    }

    public void setIdEntity(String idEntity) {
        this.idEntity = idEntity;
    }

    public String getIdEntity() {
        return idEntity;
    }

    private HashMap<String ,Attribute> createAttributes() {
        HashMap<String,Attribute> attributes = new HashMap<>();

        Attribute attribute;
        HashMap<String,Object> subAttributes;
        int lengthIDs = attributeIds.length;
        int posValues = 0;
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,1);
        Date date1 = calendar.getTime();

        for(int i = 0; i < lengthIDs; i++) {
            attribute = new Attribute();

            if(attributeIds[i].equals("source")) {
                attribute = new Attribute();
                attribute.setValue(source);
                attributes.put("source",attribute);
            }
            else if(attributeIds[i].equals("dateObserved")) {

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK);
                String dateS = dateFormat.format(date);
                String date1S = dateFormat.format(date1);
                attribute.setValue(dateS+"/"+date1S);
                attributes.put("dateObserved",attribute);
            }
            else if(attributeIds[i].equals("address")) {
                subAttributes = new HashMap<>();
                subAttributes.put("addressCountry","MX");
                subAttributes.put("addressLocality","Ciudad de México");
                subAttributes.put("streetAddress","To be defined");
                attribute.setValue(subAttributes);
                attributes.put("address",attribute);
            }
            else if(attributeIds[i].equals("location")) {
                subAttributes = new HashMap<>();

                subAttributes.put("type","Point");

                double [] coordinates = {19.36, -99.143};
                subAttributes.put("coordinates",coordinates);

                attribute.setType(java.util.Optional.of("json:geo"));
                attribute.setValue(subAttributes);
                attributes.put("location",attribute);
            }
            else if(!attributeIds[i].equals("location")) {
                attribute.setValue(attributesValues[posValues]);
                attributes.put(attributeIds[i],attribute);
                posValues++;
            }
        }
        return attributes;
    }

    public void registration() {
        if(idEntity == null) {
            System.err.println("Entity ID must not be null");
            return;
        }
            AsyncRestTemplate template = new AsyncRestTemplate();

            Entity entity = new Entity(idEntity+ID,"AirQualityObserved", createAttributes());
            Ngsi2Client client = new Ngsi2Client(template,ORION_URL);

            client.addHttpHeader("fiware-service","SmartSpot");
            client.addHttpHeader("fiware-servicepath","/smartspot");
            client.setHttpHeaderJSON();

            client.addEntity(entity);
    }

    private static Attribute getAttribute(String idEntity, String idAttribute) throws ExecutionException, InterruptedException {
        Attribute attribute = null;

        AsyncRestTemplate template = new AsyncRestTemplate();

        Ngsi2Client client = new Ngsi2Client(template,ORION_URL);
        client.addHttpHeader("fiware-service","SmartSpot");
        client.addHttpHeader("fiware-servicepath","/smartspot");

        attribute = client.getAttribute(idEntity+ID,null,idAttribute).get();

        return attribute;
    }

    public static void updateAttribute(String idEntity,String idAttribute, String subAttribute,Object value) {
        AsyncRestTemplate template = new AsyncRestTemplate();

        Ngsi2Client client = new Ngsi2Client(template,ORION_URL);
        client.addHttpHeader("fiware-service","smartspot");
        client.addHttpHeader("fiware-servicepath","/smartspot");
        HashMap<String,Object> subAttributes;
        HashMap<String,Attribute> attributeChange = new HashMap<>();
        Attribute attribute;
        try {

            if(subAttribute != null) {
                attribute = getAttribute(idEntity,idAttribute);

                subAttributes = (HashMap<String, Object>) attribute.getValue();

                subAttributes.put(subAttribute,value);
                attribute.setValue(subAttributes);

                client.setHttpHeaderJSON();
                attributeChange.put(idAttribute,attribute);
            }
            else {
                client.setHttpHeaderJSON();
                attribute = new Attribute();
                attribute.setValue(value);
                attributeChange.put(idAttribute, attribute);
            }

            client.updateEntity(idEntity+ID,null,attributeChange,false);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
