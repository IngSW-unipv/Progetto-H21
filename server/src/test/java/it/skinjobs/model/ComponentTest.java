package it.skinjobs.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest
public class ComponentTest {
    
    @Test
    public void checkProperties(){
        Component component = new Component();
        component.setId(0);
        component.setName("African Motherboard Paolina");
        component.setPower(75.6f);
        component.setPrice(4530.89f);
        ComponentFamily family = new ComponentFamily();
        component.setComponentFamily(family);

        assertEquals(0, component.getId());
        assertEquals("African Motherboard Paolina", component.getName());
        assertEquals(75.6f, component.getPower());
        assertEquals(4530.89f, component.getPrice());
        assertEquals(family, component.getComponentFamily());
    }
}
