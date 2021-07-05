package it.skinjobs.dto;

/**
 
 * @author Jessica Vecchia
 * @author Andrei Blindu
 * @author Filippo Rognoni
 */

/**
 * DTO--> DATA TRANSFER OBJECT(POJO class in Maven Framework). This object transfers data from one layer to another
 * From the client's point of view this is a read-only object having just getters and setters and no behaviour
 * This is aimed at API stability making the business logic independent from the client
 */
public class ComponentDTO extends NamedDTO {
   
   private Integer familyId;

   public Integer getFamilyId() {
      return familyId;
   }

   public void setFamilyId(Integer familyId) {
      this.familyId = familyId;
   }

   private Float price;

   public Float getPrice() {
      return price;
   }

   public void setPrice(Float price) {
      this.price = price;
   }

   private Float power;

   public Float getPower() {
      return power;
   }

   public void setPower(Float power) {
      this.power = power;
   }

   
}
