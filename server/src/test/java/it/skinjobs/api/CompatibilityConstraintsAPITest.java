package it.skinjobs.api;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.AdditionalAnswers.*;

import org.mockito.Mockito;

import it.skinjobs.ConfiguratorApplicationAPI;
import it.skinjobs.config.H2TestProfileConfig;
import it.skinjobs.model.CompatibilityConstraint;
import it.skinjobs.model.Component;
import it.skinjobs.model.ComponentFamily;
import it.skinjobs.repository.CompatibilityConstraints;
import it.skinjobs.repository.Components;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest(classes = { ConfiguratorApplicationAPI.class, H2TestProfileConfig.class })
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CompatibilityConstraintsAPITest {
    @Autowired
   MockMvc mockMvc;

   @Autowired
   ObjectMapper mapper;

   @Autowired
   CompatibilityConstraintsAPI compatibilityConstraintsAPI;

   @MockBean
   private Components components;

   @MockBean
   private CompatibilityConstraints compatibilityConstraints;

   @Test
   public void getCompatibleComponentsTest() throws Exception {
       ComponentFamily inputFamily = new ComponentFamily();
       inputFamily.setId(0);
       Component inputComponent = new Component();
       inputComponent.setComponentFamily(inputFamily);
       Mockito.when(components.findById(anyInt())).thenReturn(Optional.ofNullable(inputComponent));
       CompatibilityConstraint constraint1 = new CompatibilityConstraint();
       ComponentFamily family2 = new ComponentFamily();
       Component componentFamily2 = new Component();
       componentFamily2.setName("Family2Component");
       componentFamily2.setComponentFamily(family2);
       constraint1.setComponentFamily2(family2);
       CompatibilityConstraint constraint2 = new CompatibilityConstraint();
       ComponentFamily family3 = new ComponentFamily();
       Component componentFamily3 = new Component();
       componentFamily3.setName("Family2Component");
       componentFamily3.setComponentFamily(family3);
       constraint2.setComponentFamily2(family3);
       ArrayList<CompatibilityConstraint> constraints = new ArrayList<>();
       constraints.add(constraint1);
       constraints.add(constraint2);
       List<Component> list1 = new ArrayList<>();
       list1.add(componentFamily2);
       List<Component> list2 = new ArrayList<>();
       list2.add(componentFamily3);
       ArrayList<List<Component>> superList = new ArrayList<List<Component>>();
       superList.add(list1);
       superList.add(list2);
       int i = 0; 


       Mockito.when(compatibilityConstraints.findByFamilyId(anyInt())).thenReturn(constraints);
       Mockito.when(components.findComponentsByFamilyId(anyInt())).thenReturn(superList.get(i++));

       mockMvc.perform(MockMvcRequestBuilders
         .get("/compatibilityContraints/getByComponentId/1"))
         .andExpect(status().isOk());

   }
 


}
