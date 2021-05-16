package model.ticket;

public enum Status {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
