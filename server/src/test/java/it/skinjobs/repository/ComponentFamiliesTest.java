package it.skinjobs.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import it.skinjobs.model.ComponentFamily;
import it.skinjobs.model.ComponentType;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest(classes = { H2TestProfileConfig.class })
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
public class ComponentFamiliesTest {

    @Resource
    ComponentTypes componentTypes;

    @Resource
    ComponentFamilies componentFamilies;

    ComponentType typeSaved;

    @Before
    public void init() {
        ComponentType type = new ComponentType();
        type.setName("Type");
        type.setSortOrder(1);
        this.typeSaved = this.componentTypes.save(type);
        ComponentFamily family = new ComponentFamily();
        family.setName("Family1");
        family.setType(this.typeSaved);
        componentFamilies.save(family);
    }

    @Test
    public void testFindByType() {
        List<ComponentFamily> list = componentFamilies.findComponentFamilyByTypeId(this.typeSaved.getId());
        assertEquals(true, list.size() > 0);
    }

}
