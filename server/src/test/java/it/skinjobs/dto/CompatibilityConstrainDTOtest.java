package it.skinjobs.dto;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest
public class CompatibilityConstrainDTOtest {
    @Test
    public void checkProperties(){
        CompatibilityConstraintDTO compatibilityConstraintDTO = new CompatibilityConstraintDTO();
        compatibilityConstraintDTO.setComponentFamilyId1(1);
        compatibilityConstraintDTO.setComponentFamilyId2(2);
        
        assertEquals(1,  compatibilityConstraintDTO.getComponentFamilyId1());
        assertEquals(2,  compatibilityConstraintDTO.getComponentFamilyId2());
    }
}
