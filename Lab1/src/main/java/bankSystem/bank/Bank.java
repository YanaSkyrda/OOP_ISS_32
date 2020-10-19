package bankSystem.bank;

import bankSystem.credit.Credit;

import java.util.List;
import java.util.Vector;

public class Bank {

    private final String name;
    private List<Credit> availableCredits;

    public Bank(String name) {
        this.name = name;
        this.availableCredits = new Vector<>();
    }

    public String getName() {
        return name;
    }

    public List<Credit> getAvailableCredits() {
        return availableCredits;
    }

    public void setAvailableCredits(List<Credit> availableCredits){
        this.availableCredits = availableCredits;
    }
}

