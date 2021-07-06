package it.skinjobs.dto;

/**
 
 * @author Jessica Vecchia
 */

/**
 * DTO--> DATA TRANSFER OBJECT(POJO class in Maven Framework). This object transfers data from one layer to another
 * From the client's point of view this is a read-only object having just getters and setters and no behaviour
 * This is aimed at API stability making the business logic independent from the client
 */
public class ComponentFamilyDTO extends NamedDTO {

   private Integer typeId;

   public Integer getTypeId() {
      return typeId;
   }

   public void setTypeId(Integer typeId) {
      this.typeId = typeId;
   }

}
