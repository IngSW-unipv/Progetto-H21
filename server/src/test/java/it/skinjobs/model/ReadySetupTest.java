package it.skinjobs.model;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest
public class ReadySetupTest {
   
    @Test
    public void checkProperties(){
        ReadySetup readySetup = new ReadySetup();
        readySetup.setId(0);
        readySetup.setName("PaoloneAfricanoPc");
        readySetup.setImagePath("paolone.txt");
        readySetup.setTotalPrice(2000.00f);
        readySetup.setUsage("HOME");
        Set<Component> components = new HashSet<>();
        Component c1 = new Component();
        c1.setName("pippo");
        Component c2 = new Component();
        c2.setName("pluto");
        components.add(c1);
        components.add(c2);
        readySetup.setComponentList(components);

        assertEquals(0, readySetup.getId());
        assertEquals("PaoloneAfricanoPc", readySetup.getName());
        assertEquals("paolone.txt", readySetup.getImagePath());
        assertEquals(2000.00f, readySetup.getTotalPrice());
        assertEquals("HOME", readySetup.getUsage());
        assertEquals(components, readySetup.getComponentList());

    }
}
    
