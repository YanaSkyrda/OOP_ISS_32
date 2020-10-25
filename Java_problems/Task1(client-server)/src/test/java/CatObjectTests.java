import catObject.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CatObjectTests {
    Cat cat;

    @BeforeEach
    void init() {
        cat = new Cat("Amy", 3);
    }

    @Test
    void shouldBeEquals() {
        assertEquals(cat, new Cat("Amy", 3));
    }

    @Test
    void shouldNotBeEqualsBecauseOfAge() {
        assertNotEquals(cat, new Cat("Amy", 4));
    }

    @Test
    void shouldNotBeEqualsBecauseOfName() {
        assertNotEquals(cat, new Cat("Ammy", 3));
    }

    @Test
    void shouldConvertToString() {
        assertEquals("cat's name: Amy, cat's age: 3.", cat.toString());
    }
}
