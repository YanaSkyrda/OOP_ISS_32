import java.util.List;
import java.util.logging.Logger;

public class Utils {

    public Utils() {
    }

    private static final Logger log = Logger.getLogger(Account.class.getName());

    public static void logCreditList(List<Credit> list){
        for (Credit credit : list) {
            //log.info(credit.toString());
            System.out.println(credit.toString());
        }
    }
}
