package pl.piomin.services.trip.model;

public class TripInput {

    private String destination;
    private Integer locationX;
    private Integer locationY;
    private String username;

    public TripInput() {
    }

    public TripInput(String destination, Integer locationX, Integer locationY, String username) {
        this.destination = destination;
        this.locationX = locationX;
        this.locationY = locationY;
        this.username = username;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getLocationX() {
        return locationX;
    }

    public void setLocationX(Integer locationX) {
        this.locationX = locationX;
    }

    public Integer getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
