package it.skinjobs.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Jessica Vecchia
 * This class represents a DB Entity in which each authenticated session is saved. A session
 * needs an id, token, expireDate and credentialId.
 */
@Entity
public class Session {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Integer id;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   private String token;

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   @ManyToOne
   @JoinColumn(name = "credential_id", updatable = false)
   private Credential credential;

   public Credential getCredential() {
      return credential;
   }

   public void setCredential(Credential credential) {
      this.credential = credential;
   }

   private Date expireDate;

   public Date getExpireDate() {
      return expireDate;
   }

   public void setExpireDate(Date expireDate) {
      this.expireDate = expireDate;
   }

   /**
     *
     * @return Boolean
     * This method checks if a session is expired or not and has to be called within the
     * credentialApi class to check if a session is valid)
     */
   public Boolean isExpired() {
      Date date = new Date();
      return date.after(this.expireDate);
   }

   /**
     * Each session token is created as a unique and random value each time a session object is created
     */
   public Session() {
      this.setToken(UUID.randomUUID().toString());
      this.renewSession();
   }

   /**
     * this method has the single responsibility to renew a session letting it last 30 minutes more
     */
   public void renewSession() {
      Instant time = Instant.now();
      Instant timeFuture = time.plus(30, ChronoUnit.MINUTES);
      this.expireDate = Date.from(timeFuture); // it takes timefuture(Instant) and converts into a precise Date.
   }

   /**
     * this method set the expiration date on the session row within the DB one second before
     * now (aimed at the logout APi).
     */
   public void setNowExpired() {
      Instant time = Instant.now();
      Instant timePast = time.minus(1, ChronoUnit.SECONDS);
      this.expireDate = Date.from(timePast);
   }

}
