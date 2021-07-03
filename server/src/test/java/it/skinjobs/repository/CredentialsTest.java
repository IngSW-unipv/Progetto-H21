package it.skinjobs.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Before;
import org.junit.Test;

import it.skinjobs.config.H2TestProfileConfig;
import it.skinjobs.model.Credential;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest(classes = { H2TestProfileConfig.class })
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
public class CredentialsTest {
    @Resource
    Credentials credentials;

    @Before
    public void init() {
        Credential credential = new Credential();
        credential.setName("Pippo");
        credential.setPassword("Pluto");
        this.credentials.save(credential);
    }

    @Test
    public void testFindByName() {
        List <Credential> credentialsList = this.credentials.findByName("Pippo");
        assertEquals(true, credentialsList.size()>0);
        
    }

    
    
}
