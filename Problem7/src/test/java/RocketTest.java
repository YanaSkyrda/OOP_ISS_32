import com.university.Detail;
import com.university.Rocket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class RocketTest {

    Rocket rocket;

    @Before
    public void setUp(){
        rocket = new Rocket(null);
    }

    @Test
    public void details_TEST() throws InterruptedException {
        rocket.launchRocket();

        ArrayList<Detail> details = rocket.getDetails();
        Assert.assertEquals(5, details.size());

        String[] names = {"Engine", "Wings", "Propeller", "Jet", "Turbine"};
        for(int i = 0; i < 5; i++){
            Assert.assertEquals(details.get(i).getName(), names[i]);
        }
    }


}
