package it.skinjobs.dto;

import java.util.Set;

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

public class ReadySetupDTO extends NamedDTO {

   private Set<Integer> componentList;

   public Set<Integer> getComponentList() {
      return componentList;
   }

   public void setComponentList(Set<Integer> componentList) {
      this.componentList = componentList;
   }

   private String usage;

   public String getUsage() {
      return usage;
   }

   public void setUsage(String usage) {
      this.usage = usage;
   }

   private String imagePath;

   public String getImagePath() {
      return imagePath;
   }

   public void setImagePath(String imagePath) {
      this.imagePath = imagePath;
   }

   private Float totalPrice;

   public Float getTotalPrice() {
      return totalPrice;
   }

   public void setTotalPrice(Float totalPrice) {
      this.totalPrice = totalPrice;
   }
   

}
