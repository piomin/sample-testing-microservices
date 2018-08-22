package pl.piomin.services.driver.model;

import java.util.Objects;

public class Driver {

    private Long id;
    private String name;
    private int balance;
    private String carModel;
    private String carRegistrationNo;
    private int currentLocationX;
    private int currentLocationY;
    private DriverStatus status;

    public Driver() {
    }

    public Driver(Long id, String name, int currentLocationX, int currentLocationY, DriverStatus status) {
        this.id = id;
        this.name = name;
        this.currentLocationX = currentLocationX;
        this.currentLocationY = currentLocationY;
        this.status = status;
    }

    public Driver(Long id, int balance, DriverStatus status) {
        this.id = id;
        this.balance = balance;
        this.status = status;
    }

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

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(id, driver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
