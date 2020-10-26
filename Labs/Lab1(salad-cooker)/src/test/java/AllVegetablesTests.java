import org.junit.jupiter.api.Test;
import vegetables.Color;
import vegetables.Vegetable;
import vegetables.beans.Pea;
import vegetables.cabbages.Broccoli;
import vegetables.cabbages.WhiteCabbage;
import vegetables.exceptions.NegativeCaloriesAmount;
import vegetables.rootvegatables.Carrot;
import vegetables.rootvegatables.Radish;
import vegetables.tomatoes.Eggplant;
import vegetables.tomatoes.Pepper;
import vegetables.tomatoes.Tomato;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AllVegetablesTests {

    @Test
    void shouldCreateBrown2CaloriesPea() {
        //Given
        //When
        Vegetable pea = new Pea(Color.BROWN, 2);
        //Then
        assertEquals("Pea", pea.getName());
        assertEquals(2, pea.getCalories());
        assertEquals(Color.BROWN, pea.getColor());
    }

    @Test
    void shouldCreateGreen15CaloriesBroccoli() {
        //Given
        //When
        Vegetable broccoli = new Broccoli(Color.GREEN, 15);
        //Then
        assertEquals("Broccoli", broccoli.getName());
        assertEquals(15, broccoli.getCalories());
        assertEquals(Color.GREEN, broccoli.getColor());
    }

    @Test
    void shouldCreateWhite25And7CaloriesWhiteCabbage() {
        //Given
        //When
        Vegetable cabbage = new WhiteCabbage(Color.WHITE, 25.7f);
        //Then
        assertEquals("White cabbage", cabbage.getName());
        assertEquals(25.7f, cabbage.getCalories());
        assertEquals(Color.WHITE, cabbage.getColor());
    }

    @Test
    void shouldCreateOrange43CaloriesCarrot() {
        //Given
        //When
        Vegetable carrot = new Carrot(Color.ORANGE, 43);
        //Then
        assertEquals("Carrot", carrot.getName());
        assertEquals(43, carrot.getCalories());
        assertEquals(Color.ORANGE, carrot.getColor());
    }

    @Test
    void shouldCreatePink27And2CaloriesRadish() {
        //Given
        //When
        Vegetable radish = new Radish(Color.PINK, 27.2f);
        //Then
        assertEquals("Radish", radish.getName());
        assertEquals(27.2f, radish.getCalories());
        assertEquals(Color.PINK, radish.getColor());
    }

    @Test
    void shouldCreatePurple135CaloriesEggplant() {
        //Given
        //When
        Vegetable eggplant = new Eggplant(Color.PURPLE, 135);
        //Then
        assertEquals("Eggplant", eggplant.getName());
        assertEquals(135, eggplant.getCalories());
        assertEquals(Color.PURPLE, eggplant.getColor());
    }

    @Test
    void shouldCreateYellow47CaloriesPepper() {
        //Given
        //When
        Vegetable pepper = new Pepper(Color.YELLOW, 47);
        //Then
        assertEquals("Pepper", pepper.getName());
        assertEquals(47, pepper.getCalories());
        assertEquals(Color.YELLOW, pepper.getColor());
    }

    @Test
    void shouldCreateRed68CaloriesTomato() {
        //Given
        //When
        Vegetable tomato = new Tomato(Color.RED, 68);
        //Then
        assertEquals("Tomato", tomato.getName());
        assertEquals(68, tomato.getCalories());
        assertEquals(Color.RED, tomato.getColor());
    }

    @Test
    void shouldThrowNegativeCaloriesAmountBecauseOfAttemptToChangeCaloriesToNegativeNumber() {
        //Given
        Vegetable vegetable = new Tomato(Color.RED, 76.5f);
        //When
        Throwable exception = assertThrows(NegativeCaloriesAmount.class, () -> vegetable.setCalories(-0.f));
        //Then
        assertEquals("Attempt to change calories amount to negative number." ,exception.getMessage());
    }
}
