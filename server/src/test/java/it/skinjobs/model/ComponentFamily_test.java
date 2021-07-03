package it.skinjobs.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest
public class ComponentFamily_test {

    @Test
    public void checkProperties(){
        ComponentFamily componentFamily = new ComponentFamily();
        ComponentType type = new ComponentType();
        componentFamily.setId(0);
        componentFamily.setName("PAOLO_CPU");
        componentFamily.setType(type);

        assertEquals(0, componentFamily.getId());
        assertEquals("PAOLO_CPU", componentFamily.getName());
        assertEquals(type, componentFamily.getType());
    }
    
}
