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
import it.skinjobs.dto.ComponentTypeDTO;
import it.skinjobs.model.ComponentType;
import it.skinjobs.repository.ComponentTypes;

/**
 * @author Jessica Vecchia
 */

@SpringBootTest(classes = { ConfiguratorApplicationAPI.class, H2TestProfileConfig.class })
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ComponentTypeAPITest {

   @Autowired
   MockMvc mockMvc;

   @Autowired
   ObjectMapper mapper;

   @Autowired
   ComponentTypeAPI componentTypeAPI;

   @MockBean
   private ComponentTypes componentTypes;

   @MockBean
   private CredentialAPI credentialAPI;

   
   @MockBean
   private ComponentFamilyAPI componentFamilyAPI;

   private List<ComponentType> types;

   private ComponentType saved;
   private ComponentTypeDTO dto;

   @Before
   public void init() {
      ComponentType type1 = new ComponentType();
      type1.setName("Type1");
      type1.setSortOrder(0);
      ComponentType type2 = new ComponentType();
      type2.setName("Type2");
      type2.setSortOrder(1);
      types = new ArrayList<>();
      types.add(type1);
      types.add(type2);
      Mockito.when(componentTypes.findAllSorted()).thenReturn(types);   
   }

   @Test
   public void getAll() throws Exception {      
      mockMvc.perform(MockMvcRequestBuilders
         .get("/componentTypes"))
         .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
         .andExpect(jsonPath("$[1].name", is("Type2")));
   }
   
   @Test
   public void getByIdTestOk() throws Exception {    
      ComponentType type = new ComponentType(); 
      type.setId(1);
      type.setName("Pippo");
      type.setSortOrder(0);
      Mockito.when(componentTypes.findById(anyInt())).thenReturn(Optional.ofNullable(type));
      mockMvc.perform(MockMvcRequestBuilders
         .get("/componentTypes/" + 1))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.name", is("Pippo")))
         .andExpect(jsonPath("$.sortOrder", is(0)));
   }

   @Test
   public void insertOne() throws Exception {
      this.dto = new ComponentTypeDTO();
      this.dto.setName("Pippo");
      this.dto.setSortOrder(25);
      this.saved = new ComponentType();
      this.saved.setName("Pippo");
      this.saved.setSortOrder(25);
      this.saved.setId(12);  
      Mockito.when(componentTypes.save(anyObject())).thenReturn(this.saved);
      
      Mockito.when(credentialAPI.sessionIsValid(anyString())).thenReturn(true);
      MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/componentType")
         .contentType(MediaType.APPLICATION_JSON)
         .accept(MediaType.APPLICATION_JSON)
         .header("token", "12345")
         .content(this.mapper.writeValueAsString(this.dto));

      mockMvc.perform(request)
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", notNullValue()))
         .andExpect(jsonPath("$.id", is(12)))
         .andExpect(jsonPath("$.name", is("Pippo")))
         .andExpect(jsonPath("$.sortOrder", is(25)));
   }

   @Test
   public void updateOne() throws Exception {
      this.dto = new ComponentTypeDTO();
      this.dto.setName("Pluto");
      this.dto.setSortOrder(15);
      this.saved = new ComponentType();
      this.saved.setName("Pippo");
      this.saved.setSortOrder(25);
      this.saved.setId(12);  
      Mockito.when(componentTypes.findById(anyInt())).thenReturn(Optional.ofNullable(this.saved));
      doAnswer(returnsFirstArg()).when(componentTypes).save(Mockito.any(ComponentType.class));
      
      Mockito.when(credentialAPI.sessionIsValid(anyString())).thenReturn(true);
      MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/componentType")
         .contentType(MediaType.APPLICATION_JSON)
         .accept(MediaType.APPLICATION_JSON)
         .header("token", "12345")
         .content(this.mapper.writeValueAsString(this.dto));

      mockMvc.perform(request)
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", notNullValue()))
         .andExpect(jsonPath("$.name", is("Pluto")))
         .andExpect(jsonPath("$.sortOrder", is(15)));
   }

   @Test
   public void deleteOneTest() throws Exception{
      Mockito.when(credentialAPI.sessionIsValid(anyString())).thenReturn(true);
      Optional<ComponentType> type = Optional.ofNullable(new ComponentType());
      Mockito.when(componentTypes.findById(anyInt())).thenReturn(type);
      Mockito.when(componentFamilyAPI.deleteCascade(anyInt())).thenReturn(true);
      doNothing().when(componentTypes).deleteById(anyInt());
      MockHttpServletRequestBuilder request = MockMvcRequestBuilders
         .delete("/componentType/10")
         .contentType(MediaType.APPLICATION_JSON)
         .accept(MediaType.APPLICATION_JSON)
         .header("token", "12345");
      mockMvc.perform(request).andExpect(status().isOk());
        
   }

   @Test
   public void deleteAllTest() throws Exception{
      Mockito.when(credentialAPI.sessionIsValid(anyString())).thenReturn(true);
      ArrayList<ComponentType> list = new ArrayList<>();
      list.add(new ComponentType());
      list.add(new ComponentType());
      list.add(new ComponentType());
      Optional<ComponentType> type = Optional.ofNullable(new ComponentType());
      Mockito.when(componentTypes.findAll()).thenReturn(list);
      Mockito.when(componentTypes.findById(anyInt())).thenReturn(type);
      Mockito.when(componentFamilyAPI.deleteCascade(anyInt())).thenReturn(true);
      doNothing().when(componentTypes).deleteById(anyInt());
      MockHttpServletRequestBuilder request = MockMvcRequestBuilders
         .delete("/deleteAll")
         .contentType(MediaType.APPLICATION_JSON)
         .accept(MediaType.APPLICATION_JSON)
         .header("token", "12345");
      mockMvc.perform(request).andExpect(status().isOk());
        
   }


   @Test
   public void deleteEntityTest() {
      Optional<ComponentType> type = Optional.ofNullable(new ComponentType());
      Mockito.when(componentTypes.findById(anyInt())).thenReturn(type);
      Mockito.when(componentFamilyAPI.deleteCascade(anyInt())).thenReturn(true);
      doNothing().when(componentTypes).deleteById(anyInt());
      assertEquals(true, this.componentTypeAPI.deleteEntity(0));
   }

   @Test
   public void deleteEntityTest2() throws Exception {
      Optional<ComponentType> type = Optional.ofNullable(null);
      Mockito.when(componentTypes.findById(anyInt())).thenReturn(type);
      assertEquals(false, this.componentTypeAPI.deleteEntity(0));
   }


}
