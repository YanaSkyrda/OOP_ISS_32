import model.Candy;
import model.Utils;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CandiesSortTest {
    @Test
    void sortTest() {
        Candy c1 = new Candy();
        c1.setEnergy(50);

        Candy c2 = new Candy();
        c2.setEnergy(10);

        List<Candy> candies = new ArrayList<>();
        candies.add(c1);
        candies.add(c2);


        Utils.sortCandies(candies);

        assertEquals(c2, candies.get(0));
        assertEquals(c1, candies.get(1));
    }
}
