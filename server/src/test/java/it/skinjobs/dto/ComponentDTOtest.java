package it.skinjobs.dto;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest
public class ComponentDTOtest {
    @Test
    public void checkProperties(){
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName("test_name");
        componentDTO.setFamilyId(7);
        componentDTO.setPower(100.00f);
        componentDTO.setPrice(30.00f);

        assertEquals("test_name", componentDTO.getName());
        assertEquals(7, componentDTO.getFamilyId());
        assertEquals(100.00f, componentDTO.getPower());
        assertEquals(30.00f, componentDTO.getPrice());
        
    

    }
}
