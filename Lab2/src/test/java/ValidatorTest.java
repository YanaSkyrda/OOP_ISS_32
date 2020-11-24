import com.university.validator.Validator;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorTest {

    @Test
    public void validate_SUCCESS(){
        Assert.assertTrue(Validator.validateDocument("Gem.xml", "Gem.xsd"));
    }

    @Test
    public void validate_FAILURE(){
        Assert.assertFalse(Validator.validateDocument("Gem1.xml", "aaa.xsd"));
        Assert.assertFalse(Validator.validateDocument("aaa.xml", "Gem1.xsd"));
        Assert.assertFalse(Validator.validateDocument("GemWrong.xml", "GemWrong.xsd"));
    }
}
