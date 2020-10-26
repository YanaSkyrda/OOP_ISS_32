import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vegetables.CaloriesComparator;
import vegetables.Color;
import vegetables.Vegetable;
import vegetables.beans.Pea;
import vegetables.cabbages.Broccoli;
import vegetables.rootvegatables.Carrot;
import vegetables.rootvegatables.Radish;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaladTests {
    private Salad salad;
    private List<Vegetable> vegetables = new ArrayList<>(Arrays.asList(new Pea(Color.BROWN, 2),
            new Broccoli(Color.GREEN, 25.3f), new Broccoli(Color.GREEN, 22.7f),
            new Carrot(Color.ORANGE, 24.1f)));

    void createSimpleSalad() {
        this.salad = new Salad("Simple", vegetables);
    }

    @Test
    void shouldCreateSaladWithoutNameAndWithoutIngredients() {
        //Given
        //When
        salad = new Salad();
        //Then
        assertEquals("", salad.getName());
        assertTrue(salad.getVegetables().isEmpty());
    }

    @Test
    void shouldCreateSaladWithoutName() {
        //Given
        //When
        salad = new Salad(vegetables);
        //Then
        assertEquals("", salad.getName());
    }

    @Test
    void shouldCreateSaladWithNameWithoutIngredients() {
        //Given
        //When
        salad = new Salad("PimPam");
        //Then
        assertEquals("PimPam", salad.getName());
        assertTrue(salad.getVegetables().isEmpty());
    }

    @Test
    void shouldCreateSaladWithNameWithIngredients() {
        //Given
        //When
        createSimpleSalad();
        //Then
        assertEquals(vegetables, salad.getVegetables());
        assertEquals("Simple", salad.getName());
    }

    @Test
    void shouldRenameSaladFromSimpleToPimPam() {
        //Given
        createSimpleSalad();
        //When
        salad.renameSalad("PimPam");
        //Then
        assertEquals("PimPam", salad.getName());
    }

    @Test
    void shouldAddRadishToSalad() {
        //Given
        Vegetable radish = new Radish(Color.PINK, 13.1f);
        createSimpleSalad();
        //When
        salad.addVegetable(radish);
        //Then
        assertTrue(salad.getVegetables().contains(radish));
        assertEquals(5, salad.getVegetables().size());
    }

    @Test
    void shouldRemoveAllVegetablesOfGivenTypeFromSalad() {
        //Given
        createSimpleSalad();
        //When
        salad.remove("Broccoli");
        //Then
        assertFalse(salad.getVegetables().contains(new Broccoli(Color.GREEN, 25.3f)));
        assertFalse(salad.getVegetables().contains(new Broccoli(Color.GREEN, 22.7f)));
        assertEquals(2, salad.getVegetables().size());
    }

    @Test
    void shouldRemoveOnlyOneVegetableOfGivenTypeFromSalad() {
        //Given
        createSimpleSalad();
        //When
        salad.removeGivenAmount("Broccoli", 1);
        //Then
        assertFalse(salad.getVegetables().contains(new Broccoli(Color.GREEN, 25.3f))
                && salad.getVegetables().contains(new Broccoli(Color.GREEN, 22.7f)));
        assertTrue(salad.getVegetables().contains(new Broccoli(Color.GREEN, 25.3f))
                || salad.getVegetables().contains(new Broccoli(Color.GREEN, 22.7f)));
        assertEquals(3, salad.getVegetables().size());
    }

    @Test
    void shouldCalculateTotalCaloriesAndResultShouldBeEquals74And1() {
        //Given
        createSimpleSalad();
        //When
        float totalCalories = salad.calculateCalories();
        //Then
        assertEquals(74.1f, totalCalories);
    }

    @Test
    void shouldSortVegetablesFromLowerCaloriesToHigher() {
        //Given
        createSimpleSalad();
        //When
        salad.sortVegetables(new CaloriesComparator());
        //Then
        assertEquals(Arrays.asList(new Pea(Color.BROWN, 2), new Broccoli(Color.GREEN, 22.7f),
                new Carrot(Color.ORANGE, 24.1f), new Broccoli(Color.GREEN, 25.3f)),
                salad.getVegetables());
    }

    @Test
    void shouldPrintInfoAboutSalad() {
        //Given
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        createSimpleSalad();
        //When
        salad.eat();
        //Then
        assertTrue(outContent.toString().contains("Consumed"));
        assertTrue(outContent.toString().contains("calories from salad Simple"));
        assertTrue(salad.getVegetables().isEmpty());
        System.setOut(System.out);
    }

    @Test
    void shouldFindTwoVegetablesInInterval() {
        //Given
        createSimpleSalad();
        //When
        List<Vegetable> vegetablesInInterval = salad.findVegetablesInCaloriesInterval(22.7f, 25f);
        //Then
        assertEquals(Arrays.asList(new Broccoli(Color.GREEN, 22.7f), new Carrot(Color.ORANGE, 24.1f)),
                vegetablesInInterval);
    }
}
