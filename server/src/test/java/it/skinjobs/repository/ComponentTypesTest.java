package it.skinjobs.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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
import it.skinjobs.model.ComponentType;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest(classes = { H2TestProfileConfig.class })
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
public class ComponentTypesTest {

   @Resource
   ComponentTypes componentTypes;

   @Before
   public void init() {
      ArrayList <Integer> availableSortOrders = new ArrayList<>();
      for(int i = 0; i < 7; i++){
         availableSortOrders.add(i);
      }
      
      for(int i = 0; i < 7; i++){
         ComponentType type = new ComponentType();
         type.setName("Type" +i);
         int randomOrder = (int)(Math.random() * (availableSortOrders.size()-1));
         int value = availableSortOrders.get(randomOrder);
         System.out.println("Random order generated: " + value);
         type.setSortOrder(value);
         availableSortOrders.remove(randomOrder);
         this.componentTypes.save(type);
         
      } 
   }

   @Test
   public void getAll() {
      List<ComponentType> list = componentTypes.findAllSorted();      
      boolean check = true;
      for (int i = 1; i < list.size(); i++) {
         check &= list.get(i).getSortOrder() > list.get(i-1).getSortOrder();
      }
      assertEquals(true, check);
   }
   
}
