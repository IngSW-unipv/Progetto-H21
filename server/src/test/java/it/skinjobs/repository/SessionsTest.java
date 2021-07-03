package it.skinjobs.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import it.skinjobs.config.H2TestProfileConfig;
import it.skinjobs.model.Session;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest(classes = { H2TestProfileConfig.class })
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
public class SessionsTest {
    @Resource
    Sessions sessions;

    @Before
    public void init() {
        Session session = new Session();
        session.setToken("12345");
        session.setExpireDate(new Date());
        this.sessions.save(session);
    }

    @Test
    public void testFindByToken() {
        List<Session> sessionsList = sessions.findByToken("12345");
        assertEquals(true, sessionsList.size()>0);
    }
}
