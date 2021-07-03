package it.skinjobs.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jessica Vecchia
 * This class sets via application.properties file the default name and password of the
 * administratior as admin, admin,
 */
@ConfigurationProperties(prefix = "credentials") 
public class CredentialsProperties {

   private String name;
   private String password;

   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getPassword() {
      return password;
   }
   public void setPassword(String password) {
      this.password = password;
   }
   
}


