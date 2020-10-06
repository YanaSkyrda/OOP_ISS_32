import java.util.List;
import java.util.Vector;

public class Bank {

    private final String name;
    private List<Credit> availableCredits;

    public Bank(String name) {
        this.name = name;
        this.availableCredits = new Vector<>();
    }
    public Bank(String name, List<Credit> availableCredits) {
        this.name = name;
        this.availableCredits = availableCredits;
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

    public boolean addAvailableCredit(Credit credit){
        return this.availableCredits.add(credit);
    }


}

