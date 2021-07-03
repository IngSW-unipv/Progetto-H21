package it.skinjobs.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import it.skinjobs.config.H2TestProfileConfig;
import it.skinjobs.model.CompatibilityConstraint;
import it.skinjobs.model.ComponentFamily;
import it.skinjobs.model.ComponentType;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest(classes = { H2TestProfileConfig.class })
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional

public class CompatibilityConstraintsTest {

    @Resource 
    CompatibilityConstraints constraints; 

    @Resource
    ComponentFamilies families;

    @Resource
    ComponentTypes types;

    ComponentType typeSaved1;
    ComponentType typeSaved2;
    ComponentType typeSaved3;

    @Before
    public void init(){
        ComponentType type1 = new ComponentType();
        type1.setId(0);
        type1.setName("CPU");
        type1.setSortOrder(2);
        this.typeSaved1 = this.types.save(type1);

        ComponentType type2 = new ComponentType();
        type2.setId(3);
        type2.setName("MOTHERBOARD");
        type2.setSortOrder(3);
        this.typeSaved2 = this.types.save(type2);

        ComponentType type3 = new ComponentType();
        type3.setId(3);
        type3.setName("MOTHERBOARD");
        type3.setSortOrder(3);
        this.typeSaved3 = this.types.save(type3);
    }

    @Test
    public void testTwoFamilies() {
        ComponentFamily family1 = new ComponentFamily();
        family1.setId(1);
        family1.setName("PaoloneAfricano");
        family1.setType(typeSaved1);
        ComponentFamily familySaved1 = this.families.save(family1);

        ComponentFamily family2 = new ComponentFamily();
        family2.setId(2);
        family2.setName("Paolo");
        family2.setType(typeSaved2);
        ComponentFamily familySaved2 = this.families.save(family2); 

        CompatibilityConstraint constraint = new CompatibilityConstraint();
        constraint.setComponentFamily1(familySaved1);
        constraint.setComponentFamily2(familySaved2);

        this.constraints.save(constraint);
        List<CompatibilityConstraint> constraints = this.constraints.findByFamilyId(familySaved1.getId());
        assertEquals(familySaved2, constraints.get(0).getComponentFamily2());

        this.constraints.deleteAll();
        this.families.deleteAll();
     }

    @Test
    public void testThreeFamilies() {
        ComponentFamily family1 = new ComponentFamily();
        family1.setId(1);
        family1.setName("PaoloneAfricano");
        family1.setType(typeSaved1);
        ComponentFamily familySaved1 = this.families.save(family1);

        ComponentFamily family2 = new ComponentFamily();
        family2.setId(2);
        family2.setName("Paolo");
        family2.setType(typeSaved2);
        ComponentFamily familySaved2 = this.families.save(family2); 

        ComponentFamily family3 = new ComponentFamily();
        family3.setId(3);
        family3.setName("Paolone");
        family3.setType(typeSaved3);
        ComponentFamily familySaved3 = this.families.save(family3); 

        CompatibilityConstraint constraint = new CompatibilityConstraint();
        constraint.setComponentFamily1(familySaved1);
        constraint.setComponentFamily2(familySaved3);
        this.constraints.save(constraint);

        CompatibilityConstraint constraint2 = new CompatibilityConstraint();
        constraint2.setComponentFamily1(familySaved2);
        constraint2.setComponentFamily2(familySaved3);
        this.constraints.save(constraint2);
        
        List<CompatibilityConstraint> constraints = this.constraints.findByFamilyId(familySaved1.getId());
        List<CompatibilityConstraint> constraints2 = this.constraints.findByFamilyId(familySaved2.getId());

        assertEquals(familySaved3, constraints.get(0).getComponentFamily2());
        assertEquals(familySaved3, constraints2.get(0).getComponentFamily2());

        this.constraints.deleteAll();
        this.families.deleteAll();
    }

    @Test
    public void testNoFamilies() {
        ComponentFamily family1 = new ComponentFamily();
        family1.setId(1);
        family1.setName("PaoloneAfricano");
        family1.setType(typeSaved1);
        ComponentFamily familySaved1 = this.families.save(family1);

        ComponentFamily family2 = new ComponentFamily();
        family2.setId(2);
        family2.setName("Paolo");
        family2.setType(typeSaved2);
        ComponentFamily familySaved2 = this.families.save(family2); 

        ComponentFamily family3 = new ComponentFamily();
        family3.setId(3);
        family3.setName("Paolone");
        family3.setType(typeSaved3);
        ComponentFamily familySaved3 = this.families.save(family3); 

        CompatibilityConstraint constraint = new CompatibilityConstraint();
        constraint.setComponentFamily1(familySaved1);
        constraint.setComponentFamily2(familySaved3);
        this.constraints.save(constraint);

        CompatibilityConstraint constraint2 = new CompatibilityConstraint();
        constraint2.setComponentFamily1(familySaved2);
        constraint2.setComponentFamily2(familySaved3);
        this.constraints.save(constraint2);
        
        List<CompatibilityConstraint> constraints = this.constraints.findByFamilyId(familySaved3.getId());
      
        assertEquals(0, constraints.size());
        
        this.constraints.deleteAll();
        this.families.deleteAll();
    }


    
}
