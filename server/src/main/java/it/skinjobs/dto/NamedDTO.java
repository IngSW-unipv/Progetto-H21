package it.skinjobs.dto;

/**
 
 
 * @author Jessica Vecchia
   @author Filippo Maria Rognoni 
   @author Andrei Blindu
 */

/**
 * DTO--> DATA TRANSFER OBJECT(POJO class in Maven Framework). This object transfers data from one layer to another
 * From the client's point of view this is a read-only object having just getters and setters and no behaviour
 * This is aimed at API stability making the business logic independent from the client
 */

public class NamedDTO {
   private String name;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
