import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;


public class Account {
    private String fullName;
    private Long creditLimit;
    private List<Credit> availableCredits;

    private static final Logger log = Logger.getLogger(Account.class.getName());

    public Account(String fullName, Long creditLimit, List<Credit> availableCredits) {
        this.fullName = fullName;
        this.creditLimit = creditLimit;
        this.availableCredits = availableCredits;
    }

    public void setAvailableCredits(List<Credit> availableCredits){
        this.availableCredits = availableCredits;
    }

    public boolean addCredit(Credit credit){
        return this.availableCredits.add(credit);
    }

    public boolean enlargeCreditLimit(Long newCreditLimit){
        if(newCreditLimit > this.creditLimit){
            this.creditLimit = newCreditLimit;
            return true;
        }

        log.info("You entered wrong value!");
        return false;
    }

    public List<Credit> findCredit(String creditName){
        List<Credit> foundCredits = new Vector<>();
        for (Credit availableCredit : this.availableCredits) {
            if (availableCredit.getCreditPurpose().equals(creditName)) {
                foundCredits.add(availableCredit);
            }
        }

        return foundCredits;
    }

    public List<Credit> findCredit(String creditName, String bankName){
        List<Credit> foundCredits = new Vector<>();
        for (Credit availableCredit : this.availableCredits) {
            if (availableCredit.getCreditPurpose().equals(creditName) && availableCredit.getBankName().equals(bankName)) {
                foundCredits.add(availableCredit);
            }
        }

        return foundCredits;
    }
}
