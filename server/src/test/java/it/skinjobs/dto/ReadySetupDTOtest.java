package it.skinjobs.dto;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Set;
import java.util.HashSet;
import it.skinjobs.model.Component;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest
public class ReadySetupDTOtest {
    @Test
    public void checkProperties(){
        ReadySetupDTO readySetupDTO = new ReadySetupDTO();
        readySetupDTO.setName("paolo_pc");
        readySetupDTO.setImagePath("paolo.txt");
        readySetupDTO.setTotalPrice(300.00f);
        readySetupDTO.setUsage("GAMING");
        Set<Integer> componentList = new HashSet<>();
        Component c1 = new Component();
        Component c2 = new Component();
        componentList.add(c1.getId());
        componentList.add(c2.getId());
        readySetupDTO.setComponentList(componentList);
        
        assertEquals("paolo_pc", readySetupDTO.getName());
        assertEquals("paolo.txt", readySetupDTO.getImagePath());
        assertEquals(300.00f, readySetupDTO.getTotalPrice());
        assertEquals("GAMING", readySetupDTO.getUsage());
        assertEquals(componentList, readySetupDTO.getComponentList());
    }
}
