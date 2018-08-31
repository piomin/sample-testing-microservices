package pl.piomin.services.trip.model;

public class DriverInput {

    private Long id;
    private DriverStatus status;
    private int amount;

    public DriverInput() {

    }

    public DriverInput(Long id, DriverStatus status) {
        this.id = id;
        this.status = status;
    }

    public DriverInput(Long id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public DriverInput(Long id, DriverStatus status, int amount) {
        this.id = id;
        this.status = status;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
