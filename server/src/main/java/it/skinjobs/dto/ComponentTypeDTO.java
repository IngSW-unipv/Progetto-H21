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
public class ComponentTypeDTO extends NamedDTO {
    private Integer sortOrder;

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer order) {
        this.sortOrder = order;
    }

}
