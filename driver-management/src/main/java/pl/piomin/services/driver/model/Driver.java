package pl.piomin.services.driver.model;

public class Driver {

    private Long id;
    private String name;
    private int balance;
    private String carModel;
    private String carRegistrationNo;
    private int currentLocationX;
    private int currentLocationY;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarRegistrationNo() {
        return carRegistrationNo;
    }

    public void setCarRegistrationNo(String carRegistrationNo) {
        this.carRegistrationNo = carRegistrationNo;
    }

    public int getCurrentLocationX() {
        return currentLocationX;
    }

    public void setCurrentLocationX(int currentLocationX) {
        this.currentLocationX = currentLocationX;
    }

    public int getCurrentLocationY() {
        return currentLocationY;
    }

    public void setCurrentLocationY(int currentLocationY) {
        this.currentLocationY = currentLocationY;
    }
}
