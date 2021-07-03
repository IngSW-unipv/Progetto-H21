package it.skinjobs.dto;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest
public class ComponentFamilyDTOtest {
    @Test
    public void checkProperties() {
        ComponentFamilyDTO componentFamilyDTO = new ComponentFamilyDTO();
        componentFamilyDTO.setName("family_test");
        componentFamilyDTO.setTypeId(0);

        assertEquals("family_test", componentFamilyDTO.getName());
        assertEquals(0, componentFamilyDTO.getTypeId());

    }
    
}
