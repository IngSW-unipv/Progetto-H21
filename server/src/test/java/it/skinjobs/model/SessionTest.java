package it.skinjobs.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest
public class SessionTest {
   
   @Test
   public void checkProperties() {
      Session session = new Session();
      Credential credential = new Credential();
      credential.setId(0);
      credential.setName("admin");
      credential.setPassword("admin");
      session.setCredential(credential);
      String token = UUID.randomUUID().toString();
      session.setToken(token);
      session.setId(10);
      Date date = new Date();
      session.setExpireDate(date);

      assertEquals(credential, session.getCredential()); 
      assertEquals(token, session.getToken());
      assertEquals(10, session.getId());
      assertEquals(date, session.getExpireDate());
   }

   @Test
   public void checkEspire1() {
      Session session = new Session();
      Instant time = Instant.now();
      Instant timePast = time.minus(30, ChronoUnit.MINUTES);
      session.setExpireDate(Date.from(timePast));
      assertEquals(true, session.isExpired());
   }

   @Test
   public void checkEspire2() {
      Session session = new Session();
      Instant time = Instant.now();
      Instant timeFuture = time.plus(30, ChronoUnit.MINUTES);
      session.setExpireDate(Date.from(timeFuture));
      assertEquals(false, session.isExpired());
   }

   @Test
   public void checkEspire3() {
      Session session = new Session();
      session.setNowExpired();
      assertEquals(true, session.isExpired());
   }

   @Test
   public void checkSessionDuration() {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(Date.from(Instant.now()));
      calendar.add(Calendar.MINUTE, 29);
      calendar.add(Calendar.SECOND, 59);
      Date end1 = calendar.getTime();
      calendar = Calendar.getInstance();
      calendar.setTime(Date.from(Instant.now()));
      calendar.add(Calendar.MINUTE, 30);
      calendar.add(Calendar.SECOND, 1);
      Date end2 = calendar.getTime();
      System.out.println("end1: " + format.format(end1));
      Session session = new Session();      
      System.out.println("session end: " + session.getExpireDate());
      boolean assertion1 = session.getExpireDate().after(end1);
      boolean assertion2 = session.getExpireDate().before(end2);
      assertTrue(assertion1);
      assertTrue(assertion2);
   }

   @Test
   public void checkRenewSession() throws Exception {
      Session session = new Session();
      System.out.println("session end: " + session.getExpireDate());
      Thread.sleep(3000);
      session.renewSession();
      System.out.println("session end: " + session.getExpireDate());
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(Date.from(Instant.now()));
      calendar.add(Calendar.MINUTE, 29);
      calendar.add(Calendar.SECOND, 59);
      Date end1 = calendar.getTime();
      calendar = Calendar.getInstance();
      calendar.setTime(Date.from(Instant.now()));
      calendar.add(Calendar.MINUTE, 30);
      calendar.add(Calendar.SECOND, 1);
      Date end2 = calendar.getTime();
      System.out.println("end to check: " + format.format(end1));            
      boolean assertion1 = session.getExpireDate().after(end1);
      boolean assertion2 = session.getExpireDate().before(end2);
      assertTrue(assertion1);
      assertTrue(assertion2);
   }
}
