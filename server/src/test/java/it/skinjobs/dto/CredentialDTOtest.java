package it.skinjobs.dto;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest
public class CredentialDTOtest {
    @Test
    public void checkProperties(){
        CredentialDTO credentialDTO = new CredentialDTO();
        credentialDTO.setName("admin");
        credentialDTO.setPassword("Ombra123");

        assertEquals("admin", credentialDTO.getName());
        assertEquals("Ombra123", credentialDTO.getPassword());
    }
}
