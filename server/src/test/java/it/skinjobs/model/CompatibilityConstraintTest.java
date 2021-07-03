package it.skinjobs.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest
public class CompatibilityConstraintTest {
    @Test
    public void checkProperties() {
        CompatibilityConstraint constraint = new CompatibilityConstraint();
        constraint.setId(0);
        ComponentFamily componentFamily1 = new ComponentFamily();
        ComponentFamily componentFamily2 = new ComponentFamily();
        constraint.setComponentFamily1(componentFamily1);
        constraint.setComponentFamily2(componentFamily2);

        assertEquals(0, constraint.getId());
        assertEquals(componentFamily1, constraint.getComponentFamily1());
        assertEquals(componentFamily2, constraint.getComponentFamily2());
        

    }
}
