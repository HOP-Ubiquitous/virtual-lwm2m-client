package eu.hopu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDto extends CoordinateDto {


    private ArrayList<CoordinateDto> route;

    public LocationDto() {
        super();
    }

    public LocationDto(float latitude, float longitude, float altitude, ArrayList<CoordinateDto> route) {
        super(latitude, longitude, altitude);
        this.route = route;
    }


    public ArrayList<CoordinateDto> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<CoordinateDto> route) {
        this.route = route;
    }


    public boolean hasRoute() {
        return route != null;
    }

    @Override
    public String toString() {
        return "LocationDto{" +
                "latitude=" + getLatitude() +
                ", longitude=" + getLongitude() +
                ", altitude=" + getAltitude() +
                ", route=" + route +
                '}';
    }
}
