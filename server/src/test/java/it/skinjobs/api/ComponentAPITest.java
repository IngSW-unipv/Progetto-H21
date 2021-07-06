/**
@author Jessica Vecchia
@author Andrei Blindu
 */
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

import it.skinjobs.ConfiguratorApplicationAPI;
import it.skinjobs.config.H2TestProfileConfig;
import it.skinjobs.dto.ComponentDTO;
import it.skinjobs.model.Component;
import it.skinjobs.model.ComponentFamily;
import it.skinjobs.model.ComponentType;
import it.skinjobs.repository.ComponentFamilies;
import it.skinjobs.repository.Components;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.AdditionalAnswers.*;

import org.mockito.Mockito;

@SpringBootTest(classes = { ConfiguratorApplicationAPI.class, H2TestProfileConfig.class })
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ComponentAPITest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    ComponentAPI componentAPI;

    @MockBean
    private Components components;

    private List<Component> componentList;

    @MockBean
    private ReadySetupAPI readySetupAPI;

    @MockBean
    private CredentialAPI credentialAPI;

    private ComponentDTO dto;
    private Component saved;

    @MockBean
    private ComponentFamilies families;

    @Before
    public void init() {
        ComponentType type1 = new ComponentType();
        type1.setName("Pluto");
        type1.setSortOrder(1);
        ComponentFamily family1 = new ComponentFamily();
        family1.setName("PlutoF");
        family1.setType(type1);
        Component component1 = new Component();
        component1.setName("Pluto Component");
        component1.setPower(100.00f);
        component1.setPrice(1000.00f);
        component1.setComponentFamily(family1);

        ComponentType type2 = new ComponentType();
        type2.setName("Pippo");
        type2.setSortOrder(2);
        ComponentFamily family2 = new ComponentFamily();
        family1.setName("PippoF");
        family1.setType(type2);
        Component component2 = new Component();
        component2.setName("Pippo Component");
        component2.setPower(200.00f);
        component2.setPrice(2000.00f);
        component2.setComponentFamily(family2);

        this.componentList = new ArrayList<>();
        this.componentList.add(component1);
        this.componentList.add(component2);
        Mockito.when(components.findAll()).thenReturn(componentList);
    }

    @Test
    public void getAllTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/components")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[1].name", is("Pippo Component")));
    }

    @Test
    public void getByIdTestOk() throws Exception {
        ComponentType type = new ComponentType();
        type.setId(1);
        type.setName("PlutoTYpe");
        type.setSortOrder(0);

        ComponentFamily componentFamily = new ComponentFamily();
        componentFamily.setId(10);
        componentFamily.setName("PlutoFamily");
        componentFamily.setType(type);

        Component component = new Component();
        component.setId(5);
        component.setName("component1");
        component.setPower(10.0f);
        component.setPrice(40.0f);
        component.setComponentFamily(componentFamily);

        Mockito.when(components.findById(anyInt())).thenReturn(Optional.ofNullable(component));
        mockMvc.perform(MockMvcRequestBuilders.get("/components/" + 5)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("component1"))).andExpect(jsonPath("$.componentFamily.id", is(10)));
    }

    @Test
    public void insertOne() throws Exception {
        ComponentType type = new ComponentType();
        type.setId(1);
        type.setName("type");
        type.setSortOrder(2);
        ComponentFamily family = new ComponentFamily();
        family.setId(10);
        family.setName("Pluto");
        family.setType(type);
        this.dto = new ComponentDTO();
        this.dto.setName("Pippo");
        this.dto.setPower(10.0f);
        this.dto.setPrice(20.f);
        this.dto.setFamilyId(10);
        this.saved = new Component();
        this.saved.setName("Pippo");
        this.saved.setPower(10.0f);
        this.saved.setPrice(20.0f);
        this.saved.setComponentFamily(family);
        this.saved.setId(12);
        Mockito.when(families.findById(anyInt())).thenReturn(Optional.ofNullable(family));
        Mockito.when(components.save(anyObject())).thenReturn(this.saved);

        Mockito.when(credentialAPI.sessionIsValid(anyString())).thenReturn(true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/component")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header("token", "12345")
                .content(this.mapper.writeValueAsString(this.dto));

        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(12))).andExpect(jsonPath("$.name", is("Pippo")))
                .andExpect(jsonPath("$.componentFamily.id", is(10)));
    }

    @Test
    public void updateOne() throws Exception {
        ComponentType type = new ComponentType();
        type.setId(1);
        type.setName("type");
        type.setSortOrder(2);
        ComponentFamily family = new ComponentFamily();
        family.setId(10);
        family.setName("Pluto");
        family.setType(type);
        this.dto = new ComponentDTO();
        this.dto.setName("Pluto");
        this.dto.setPower(10.0f);
        this.dto.setPrice(20.f);
        this.dto.setFamilyId(10);
        this.saved = new Component();
        this.saved.setName("Pippo");
        this.saved.setPower(10.0f);
        this.saved.setPrice(20.0f);
        this.saved.setComponentFamily(family);
        this.saved.setId(12);

        Mockito.when(families.findById(anyInt())).thenReturn(Optional.ofNullable(family));
        Mockito.when(components.findById(anyInt())).thenReturn(Optional.ofNullable(this.saved));
        Mockito.when(components.save(anyObject())).thenReturn(this.saved);
        doAnswer(returnsFirstArg()).when(components).save(Mockito.any(Component.class));

        Mockito.when(credentialAPI.sessionIsValid(anyString())).thenReturn(true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/component/" + 12)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header("token", "12345")
                .content(this.mapper.writeValueAsString(this.dto));

        mockMvc.perform(request)
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", notNullValue()))
         .andExpect(jsonPath("$.name", is("Pluto")))
         .andExpect(jsonPath("$.componentFamily.id", is(10)));
    }

    @Test
    public void deleteOneTest() throws Exception{
       Mockito.when(credentialAPI.sessionIsValid(anyString())).thenReturn(true);
       Optional<Component> component = Optional.ofNullable(new Component());
       Mockito.when(components.findById(anyInt())).thenReturn(component);
       doNothing().when(readySetupAPI).deleteCascade(anyInt());
       doNothing().when(components).deleteById(anyInt());
       MockHttpServletRequestBuilder request = MockMvcRequestBuilders
          .delete("/component/" +10)
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
          .header("token", "12345");
       mockMvc.perform(request).andExpect(status().isOk());
         
    }

    @Test
   public void deleteEntityTest() {
      Optional<Component> component = Optional.ofNullable(new Component());
      Mockito.when(components.findById(anyInt())).thenReturn(component);
      doNothing().when(readySetupAPI).deleteCascade(anyInt());
      doNothing().when(components).deleteById(anyInt());
      assertEquals(true, this.componentAPI.deleteEntity(0));
   }

   @Test
   public void deleteEntityTest2() throws Exception {
      Optional<Component> component = Optional.ofNullable(null);
      Mockito.when(components.findById(anyInt())).thenReturn(component);
      assertEquals(false, this.componentAPI.deleteEntity(0));
   }

   @Test
   public void getAllByTypeTest() throws Exception {
    ComponentType type = new ComponentType();
    type.setId(1);
    type.setName("PlutoTYpe");
    type.setSortOrder(0);

    ComponentType type2 = new ComponentType();
    type2.setId(2);
    type2.setName("Plutone");
    type2.setSortOrder(2);


    ComponentFamily componentFamily = new ComponentFamily();
    componentFamily.setId(10);
    componentFamily.setName("PlutoFamily");
    componentFamily.setType(type);

    ComponentFamily componentFamily2 = new ComponentFamily();
    componentFamily2.setId(17);
    componentFamily2.setName("PlutoFamily2");
    componentFamily2.setType(type2);

    Component component = new Component();
    component.setId(5);
    component.setName("component1");
    component.setPower(10.0f);
    component.setPrice(40.0f);
    component.setComponentFamily(componentFamily);

    Component component2 = new Component();
    component2.setId(7);
    component2.setName("component2");
    component2.setPower(11.0f);
    component2.setPrice(41.0f);
    component2.setComponentFamily(componentFamily2);

    List<Component> componentList1 = new ArrayList<>();
    List<Component> componentList2 = new ArrayList<>();

    componentList1.add(component);
    componentList2.add(component2);
    
    Mockito.when(components.findByTypeId(anyInt())).thenReturn(componentList1);

    mockMvc.perform(MockMvcRequestBuilders.get("/component/" +1))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$", hasSize(1)))
    .andExpect(jsonPath("$[0].name", is("component1")));
   }






}
