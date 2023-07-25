package org.example;

public class Location {

    private String restro_id;
    private int latitude;
    private int longitude;

    public void setRestro_id(String restro_id) {
        this.restro_id = restro_id;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public String getRestro_id() {
        return restro_id;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Location{" +
                "restro_id='" + restro_id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}
