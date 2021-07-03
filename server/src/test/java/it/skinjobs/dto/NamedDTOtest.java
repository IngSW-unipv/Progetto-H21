package it.skinjobs.dto;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest
public class NamedDTOtest {

    @Test
    public void checkProperties() {
        NamedDTO namedDTO = new NamedDTO();
        namedDTO.setName("testName");

        assertEquals("testName", namedDTO.getName());
    }
    
}
