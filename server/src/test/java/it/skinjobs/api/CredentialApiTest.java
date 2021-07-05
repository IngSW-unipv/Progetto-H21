package it.skinjobs.api;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import it.skinjobs.ConfiguratorApplicationAPI;
import it.skinjobs.config.H2TestProfileConfig;
import it.skinjobs.dto.CredentialDTO;
import it.skinjobs.model.Credential;
import it.skinjobs.model.Session;
import it.skinjobs.repository.Credentials;
import it.skinjobs.repository.Sessions;
import it.skinjobs.utils.CredentialsProperties;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.AdditionalAnswers.*;
import static org.mockito.Mockito.doAnswer;
import static org.hamcrest.Matchers.*;

import org.mockito.Mockito;

@SpringBootTest(classes = { ConfiguratorApplicationAPI.class, H2TestProfileConfig.class })
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CredentialApiTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    CredentialsProperties properties;

    @MockBean
    private Credentials credentials;

    @MockBean
    Sessions sessions;

    @Autowired
    CredentialAPI credentialAPI;

    private List<Credential> credentialList;
    private Credential saved;
    private CredentialDTO credentialDTO;
    private List<Session> sessionList;

    @Before
    public void init() {
        
    }

    @Test
    public void testConstructor() {
        CredentialsProperties properties = new CredentialsProperties();
        properties.setName("admin");
        properties.setPassword("admin");
        Mockito.when(credentials.findByName(anyString())).thenReturn(new ArrayList<>());
        doAnswer(returnsFirstArg()).when(credentials).save(Mockito.any(Credential.class));
        CredentialAPI credentialAPI = new CredentialAPI(this.credentials, this.properties);
        assertEquals("admin", credentialAPI.getAdminCredential().getName());
        assertEquals("admin", credentialAPI.getAdminCredential().getPassword());
    }

    @Test
    public void testLoginSuccess() throws Exception {
        this.credentialDTO = new CredentialDTO();
        this.credentialDTO.setName("admin");
        this.credentialDTO.setPassword("admin");
        this.credentialList = new ArrayList<>();
        this.credentialList.add(new Credential());
        this.credentialList.get(0).setName("admin");
        this.credentialList.get(0).setPassword("admin");

        Mockito.when(credentials.findByName(anyString())).thenReturn(credentialList);
        doAnswer(returnsFirstArg()).when(sessions).save(Mockito.any(Session.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/login")
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .content(this.mapper.writeValueAsString(this.credentialDTO));

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.credential.name", is("admin")));
    }


    @Test
    public void testSessionIsNotValid() {
        Session session = new Session();
        session.setId(1);
        session.setCredential(this.saved);
        session.setNowExpired();
        session.setToken("123");
        this.sessionList = new ArrayList<>();
        this.sessionList.add(session);
        Mockito.when(sessions.findByToken(anyString())).thenReturn(sessionList);
        assertEquals(true, session.isExpired());
        Mockito.when(credentialAPI.renewSession(session)).thenReturn(null);

    }
}
