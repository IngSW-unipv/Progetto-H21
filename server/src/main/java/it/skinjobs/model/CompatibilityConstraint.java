package it.skinjobs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Andrei Blindu
 * @author Jessica Vecchia
 */

/**
 * The decorator entity tells Hibernate that this object will be a database
 * entity
 */
@Entity
public class CompatibilityConstraint {

    /**
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

    /**
     * This decorator allows us to realize a many to one relationship between the ComponentFamily's primary key(id) and
     * each table referencing it with a foreign key, in this case family_id1 is a foreign key for ComponentFamily's id
     */
    @ManyToOne
    @JoinColumn(name = "family_id1", updatable = true)
    private ComponentFamily componentFamily1;

    public ComponentFamily getComponentFamily1() {
        return componentFamily1;
    }

    public void setComponentFamily1(ComponentFamily componentFamily1) {
        this.componentFamily1 = componentFamily1;
    }

    /**
     * This decorator allows us to realize a many to one relationship between the ComponentFamily's primary key(id) and
     * each table referencing it with a foreign key, in this case family_id2 is a foreign key for ComponentFamily's id
     */
    @ManyToOne
    @JoinColumn(name = "family_id2", updatable = true)
    private ComponentFamily componentFamily2;

    public ComponentFamily getComponentFamily2() {
        return componentFamily2;
    }

    public void setComponentFamily2(ComponentFamily componentFamily2) {
        this.componentFamily2 = componentFamily2;
    }

}
