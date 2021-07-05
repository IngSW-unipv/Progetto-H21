package it.skinjobs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Andrei Blindu
 * @author Filippo Maria Rognoni
 * @author Jessica Vecchia
 */

/**
 * The decorator entity tells Hibernate that this object will be a database entity
 */
@Entity
public class ComponentType{
   
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

   private Integer sortOrder;

   public Integer getSortOrder() {
      return sortOrder;
   }

   public void setSortOrder(Integer order) {
      this.sortOrder = order;
   }

}
/*
1. Occorre una TABELLA in più che gestisce la sequenza dei types
2. Questa tabella conterrà un ID, una coppia di colonne TypeIdBefore, TypeIdAfter (oppure un TypeId e un SortOrder)
3. Sarà poi possibile ricavare un array di Type facendo N query tanti quanti sono i Type
4. Se si vuole un db normalizzato con quindi un nome ed altri attributi per uno specifico ordinamento, occorre una seconda tabella 

5. Prima debolezza: per avere un ordinamento di 7 type bisogna fare 8 query (n+1)
6. Seconda debolezza: se si cambia un ordinamento, bisogna cancellarli tutti e rifarli, con parecchie query
7. Terza debolezza: se si elimina o si aggiunge un type, bisogna riscrivere tutti gli ordinamenti 
8. C'è poi il problema del point of failure: un errato setup è difficile da sistemare




*/