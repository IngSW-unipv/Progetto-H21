package it.skinjobs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Jessica Vecchia
 */

/**
 * The decorator entity tells Hibernate that this object will be a database entity
 */
@Entity
public class Component {

   /**
   * This decorator generates automatically the primary key
   */
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
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

   private Float price;

   public Float getPrice() {
      return price;
   }

   public void setPrice(Float price) {
      this.price = price;
   }

   /**
     * This decorator allows us to realize a many to one relationship between the ComponentFamily's primary key(id) and
     * each table referencing it with a foreign key, in this case family_id is a foreign key for ComponentFamily's id
     */
   @ManyToOne
   @JoinColumn(name = "family_id", updatable = true)
   private ComponentFamily componentFamily;

   public ComponentFamily getComponentFamily() {
      return componentFamily;
   }

   public void setComponentFamily(ComponentFamily componentFamily) {
      this.componentFamily = componentFamily;
   }

   private Float power;


   public Float getPower() {
      return power;
   }

   public void setPower(Float power) {
      this.power = power;
   }

   // @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "componentList")
   // private Set<ReadySetup> readySetups;

   // public Set<ReadySetup> getReadySetups() {
   //    return readySetups;
   // }

   // public void setReadySetups(Set<ReadySetup> readySetups) {
   //    this.readySetups = readySetups;
   // }
   
}
