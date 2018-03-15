package eu.hopu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetadataDto {

    private String place;
    private String image;

    public MetadataDto() {
    }

    public MetadataDto(String place, String image) {
        this.place = place;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "MetadataDto{" +
                "place='" + place + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

}
