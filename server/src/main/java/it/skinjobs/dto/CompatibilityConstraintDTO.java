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
public class CompatibilityConstraintDTO {
    
    private Integer componentFamilyId1;

    public Integer getComponentFamilyId1() {
        return componentFamilyId1;
    }

    public void setComponentFamilyId1(Integer componentFamilyId1) {
        this.componentFamilyId1 = componentFamilyId1;
    }

    private Integer componentFamilyId2;

    public Integer getComponentFamilyId2() {
        return componentFamilyId2;
    }

    public void setComponentFamilyId2(Integer componentFamilyId2) {
        this.componentFamilyId2 = componentFamilyId2;
    }

}
