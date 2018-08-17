package pl.piomin.services.passenger.model;

import java.util.Objects;

public class Passenger {

    private Long id;
    private String name;
    private String login;
    private String phoneNo;
    private int balance;
    private int homeLocationX;
    private int homeLocationY;
    private int discount;

    public Passenger() {

    }

    public Passenger(String name, String login, String phoneNo, int balance, int homeLocationX, int homeLocationY) {
        this.name = name;
        this.login = login;
        this.phoneNo = phoneNo;
        this.balance = balance;
        this.homeLocationX = homeLocationX;
        this.homeLocationY = homeLocationY;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getHomeLocationX() {
        return homeLocationX;
    }

    public void setHomeLocationX(int homeLocationX) {
        this.homeLocationX = homeLocationX;
    }

    public int getHomeLocationY() {
        return homeLocationY;
    }

    public void setHomeLocationY(int homeLocationY) {
        this.homeLocationY = homeLocationY;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(id, passenger.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
