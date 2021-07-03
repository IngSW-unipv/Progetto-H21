package it.skinjobs.model;



import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest
public class CredentialTest {

    @Test
    public void checkProperties() {
        Credential credential = new Credential();
        credential.setId(0);
        credential.setName("Paolone");
        credential.setPassword("ThisTimeForAfrika");

        assertEquals(0, credential.getId());
        assertEquals("Paolone", credential.getName());
        assertEquals("ThisTimeForAfrika", credential.getPassword());
    }
    
}
