package it.skinjobs.dto;

import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest
public class ComponentTypeDTOtest {
    @Test
    public void checkProperties(){
        ComponentTypeDTO componentTypeDTO = new ComponentTypeDTO();
        componentTypeDTO.setName("test_name");
        componentTypeDTO.setSortOrder(0);

        assertEquals("test_name", componentTypeDTO.getName());
        assertEquals(0, componentTypeDTO.getSortOrder());

    }
}
