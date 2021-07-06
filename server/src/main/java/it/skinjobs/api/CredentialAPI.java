package it.skinjobs.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import it.skinjobs.dto.CredentialDTO;
import it.skinjobs.model.Credential;
import it.skinjobs.model.Session;
import it.skinjobs.repository.Credentials;
import it.skinjobs.repository.Sessions;
import it.skinjobs.utils.CredentialsProperties;

/**
 * @Author Jessica Vecchia This class contains all the methods related to the
 *         session creation and end.
 */
@RestController
public class CredentialAPI {
    @Autowired
    private Credentials credentials;

    @Autowired
    private Sessions sessions;

    private Credential adminCredential;

    public Credential getAdminCredential() {
        return adminCredential;
    }

    /**
     * @param credentials
     * @param credentialsProperties
     */
    @Autowired
    public CredentialAPI(Credentials credentials, CredentialsProperties credentialsProperties) {
        String name = credentialsProperties.getName();
        try {
            if (credentials.findByName(name).size() == 0) {
                String password = credentialsProperties.getPassword();
                Credential credential = new Credential();
                credential.setName(name);
                credential.setPassword(password);
                this.adminCredential = credentials.save(credential);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * @param credentialDTO
     * @return Response Entity<Session> The login Api is a Post method because it
     *         creates a session and saves on DB. First of all, credentials are
     *         found by input name. Then the password for those credentials is
     *         checked and if everything is ok, the session is created and saved on
     *         DB (it lasts 30 minutes).
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<Session> login(@RequestBody CredentialDTO credentialDTO) {
        List<Credential> credentialList = this.credentials.findByName(credentialDTO.getName());
        if (credentialList.size() > 0) {
            Credential credential = credentialList.get(0);
            if (credential.getPassword().equals(credentialDTO.getPassword())) {
                Session session = new Session();
                session.setCredential(credential);
                return new ResponseEntity<>(this.sessions.save(session), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     *
     * @param session
     * @return Session this method renews the sessions letting it last 30 minutes
     *         more.
     */
    public Session renewSession(Session session) {
        session.renewSession();
        return this.sessions.save(session);
    }

    /**
     *
     * @param token
     * @return Boolean This method checks if a session is valid and if yes, it
     *         renews it. (This check is done each time the admin has to perform an
     *         action on DB and it means that for each admin operation the session
     *         is renewed and lasts 30 minutes more.
     */
    public Boolean sessionIsValid(String token) {
        List<Session> sessionList = this.sessions.findByToken(token);
        Boolean result = false;
        if (sessionList.size() > 0) {
            Session session = sessionList.get(0);
            if (!session.isExpired()) {
                result = true;
                this.renewSession(session);
            }
        }
        return result;
    }

    /**
     *
     * @param headers
     * @return ResponseEntity<Session> This Api first finds a session by token and
     *         then checks the its validity. If the session is valid, then the
     *         expiration date is modified and set to 1 second before now.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestHeader Map<String, String> headers) {
        String token = headers.get("token");
        List<Session> sessionList = this.sessions.findByToken(token);
        if (sessionList.size() == 0 || sessionList.get(0).isExpired()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        Session result = sessionList.get(0);

        result.setNowExpired();
        sessions.save(result);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
