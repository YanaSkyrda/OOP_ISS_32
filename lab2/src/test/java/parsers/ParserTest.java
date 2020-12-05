package parsers;

import generated.classes.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import parsers.test.utils.ParserTestArgumentsProvider;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {
    private ObjectFactory objectFactory= new ObjectFactory();
    private Periodical periodical = objectFactory.createPeriodical();
    private Characteristics characteristics = objectFactory.createCharacteristics();

    Paper toCheck = objectFactory.createPaper();


    void reset(){
        periodical = objectFactory.createPeriodical();
        characteristics= objectFactory.createCharacteristics();
    }

    private ParserTest(){
        characteristics.setColorful(true);
        characteristics.setPageAmount(4);
        characteristics.setGlossy(true);
        periodical.setTitle("Kharkiv city tour");
        periodical.setType(Type.BOOKLET);
        periodical.setMonthly(false);
        periodical.setId("0");
        periodical.setCharacteristics(characteristics);
        toCheck.getPeriodicals().add(periodical);

        reset();

        characteristics.setColorful(true);
        characteristics.setPageAmount(30);
        characteristics.setGlossy(true);
        characteristics.setSubscribable(true);
        periodical.setTitle("Playboy");
        periodical.setType(Type.MAGAZINE);
        periodical.setMonthly(true);
        periodical.setId("1");
        periodical.setCharacteristics(characteristics);
        toCheck.getPeriodicals().add(periodical);

        reset();


        characteristics.setColorful(false);
        characteristics.setPageAmount(8);
        characteristics.setSubscribable(true);
        periodical.setTitle("New Your Times");
        periodical.setType(Type.NEWSPAPER);
        periodical.setMonthly(false);
        periodical.setId("2");
        periodical.setCharacteristics(characteristics);
        toCheck.getPeriodicals().add(periodical);

        toCheck.sort();
    }


    @ParameterizedTest(name="Checking {index} example")
    @DisplayName("Check equals")
    @ArgumentsSource(ParserTestArgumentsProvider.class)
    void checkEquals(Paper toCheck){
        assertTrue(this.toCheck.equals(toCheck));
    }
}
