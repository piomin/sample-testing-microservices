package pl.piomin.services.trip.model;

public class TripInput {

    private String destination;
    private int locationX;
    private int locationY;
    private String username;

    public TripInput() {

    }

    public TripInput(String destination, int locationX, int locationY, String username) {
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

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
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
