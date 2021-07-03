package it.skinjobs.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest
public class ComponentTypeTest {
   
   @Test
   public void checkProperties() {
      ComponentType type = new ComponentType();
      type.setId(1);
      type.setName("fake");
      type.setSortOrder(0);
      assertEquals(1, type.getId());
      assertEquals("fake", type.getName());
      assertEquals(0, type.getSortOrder());
   }
}
