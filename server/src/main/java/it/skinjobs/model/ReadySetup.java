package it.skinjobs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import java.util.Set;

/**
 * @author Jessica Vecchia
 */

/**
 * The decorator entity tells Hibernate that this object will be a database entity
 */
@Entity
public class ReadySetup { 
  
   /**
    * @Author Jessica Vecchia
     * This decorator generates automatically the primary key
     */
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)

   private Integer id;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   private String name;


   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @ManyToMany
   @JoinTable(name = "Ready_set_up_components", joinColumns = @JoinColumn(name = "readySetup_id"), 
   inverseJoinColumns = @JoinColumn(name = "component_id"))
   private Set<Component> componentList;

   public Set<Component> getComponentList() {
      return componentList;
   }

   public void setComponentList(Set<Component> componentList) {
      this.componentList = componentList;
   }

   private String typeUse;
   public String getUsage() {
       return typeUse;
   }

   public void setUsage(String usage) {
       this.typeUse = usage;
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
