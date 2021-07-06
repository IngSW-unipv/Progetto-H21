/**
@author Jessica Vecchia
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
import it.skinjobs.dto.ComponentFamilyDTO;
import it.skinjobs.model.ComponentFamily;
import it.skinjobs.model.ComponentType;
import it.skinjobs.repository.ComponentFamilies;
import it.skinjobs.repository.ComponentTypes;

@SpringBootTest(classes = { ConfiguratorApplicationAPI.class, H2TestProfileConfig.class })
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ComponentFamilyAPITest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    ComponentFamilyAPI componentFamilyAPI;

    @MockBean
    private ComponentFamilies families;

    private List<ComponentFamily> familyList;

    @MockBean
    private ComponentTypeAPI componentTypeAPI;

    @MockBean
    private CompatibilityConstraintsAPI compatibilityConstraintsAPI;

    @MockBean
    private ComponentAPI componentAPI;

    @MockBean
    private CredentialAPI credentialAPI;

    private ComponentFamilyDTO dto;
    private ComponentFamily saved;

    @MockBean
    private ComponentTypes types;

    @Before
    public void init() {
        ComponentType type1 = new ComponentType();
        type1.setName("Pluto");
        type1.setSortOrder(1);
        ComponentFamily family1 = new ComponentFamily();
        family1.setName("Pippo");
        family1.setType(type1);
        ComponentFamily family2 = new ComponentFamily();
        family2.setName("Pluto");
        family2.setType(type1);
        this.familyList = new ArrayList<>();
        this.familyList.add(family1);
        this.familyList.add(family2);
        Mockito.when(families.findAll()).thenReturn(familyList);

    }

    @Test
    public void getAllTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/componentFamilies")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[1].name", is("Pluto")));
    }

    @Test
    public void getByIdTestOk() throws Exception {
        ComponentType type = new ComponentType();
        type.setId(5);
        type.setName("type1");
        type.setSortOrder(2);
        ComponentFamily family = new ComponentFamily();
        family.setId(1);
        family.setName("family4");
        family.setType(type);
        Mockito.when(families.findById(anyInt())).thenReturn(Optional.ofNullable(family));
        mockMvc.perform(MockMvcRequestBuilders.get("/componentFamilies/" + 1)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("family4"))).andExpect(jsonPath("$.type.id", is(5)));

    }

    @Test
    public void insertOne() throws Exception {
        ComponentType type = new ComponentType();
        type.setId(4);
        type.setName("Paolo");
        type.setSortOrder(3);
        this.dto = new ComponentFamilyDTO();
        this.dto.setName("Pippo");
        this.dto.setTypeId(4);
        this.saved = new ComponentFamily();
        this.saved.setName("Pippo");
        this.saved.setType(type);
        this.saved.setId(12);
        Mockito.when(types.findById(anyInt())).thenReturn(Optional.ofNullable(type));
        Mockito.when(families.save(anyObject())).thenReturn(this.saved);

        Mockito.when(credentialAPI.sessionIsValid(anyString())).thenReturn(true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/componentFamily")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header("token", "12345")
                .content(this.mapper.writeValueAsString(this.dto));

        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(12))).andExpect(jsonPath("$.name", is("Pippo")))
                .andExpect(jsonPath("$.type.id", is(4)));
    }

    @Test
    public void updateOne() throws Exception {
        ComponentType type = new ComponentType();
        type.setId(4);
        type.setName("Paolo");
        type.setSortOrder(3);
        this.dto = new ComponentFamilyDTO();
        this.dto.setName("Ombra");
        this.dto.setTypeId(4);
        this.saved = new ComponentFamily();
        this.saved.setName("Pippo");
        this.saved.setType(type);
        this.saved.setId(12);
        Mockito.when(types.findById(anyInt())).thenReturn(Optional.ofNullable(type));
        Mockito.when(families.findById(anyInt())).thenReturn(Optional.ofNullable(this.saved));
        Mockito.when(families.save(anyObject())).thenReturn(this.saved);
        doAnswer(returnsFirstArg()).when(families).save(Mockito.any(ComponentFamily.class));

        Mockito.when(credentialAPI.sessionIsValid(anyString())).thenReturn(true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/componentFamily/" + 12)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("token", "12345")
                .content(this.mapper.writeValueAsString(this.dto));

        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(12)))
                .andExpect(jsonPath("$.name", is("Ombra")))
                .andExpect(jsonPath("$.type.id", is(4)));

    }

    @Test
    public void deleteOneTest() throws Exception {
        Mockito.when(credentialAPI.sessionIsValid(anyString())).thenReturn(true);
        Optional<ComponentFamily> family = Optional.ofNullable(new ComponentFamily());
        Mockito.when(families.findById(anyInt())).thenReturn(family);
        doNothing().when(compatibilityConstraintsAPI).deleteCascade(anyInt());
        Mockito.when(componentAPI.deleteCascade(anyInt())).thenReturn(true);
        doNothing().when(families).deleteById(anyInt());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/componentFamily/" +10)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("token", "12345");
        mockMvc.perform(request).andExpect(status().isOk());

    }

    @Test
    public void deleteEntityTest() {
        Optional<ComponentFamily> family = Optional.ofNullable(new ComponentFamily());
        Mockito.when(families.findById(anyInt())).thenReturn(family);
        doNothing().when(compatibilityConstraintsAPI).deleteCascade(anyInt());
        Mockito.when(componentAPI.deleteCascade(anyInt())).thenReturn(true);
        doNothing().when(families).deleteById(anyInt());
        assertEquals(true, this.componentFamilyAPI.deleteEntity(0));
    }

    @Test
    public void deleteEntityTest2() {
        Optional<ComponentFamily> family = Optional.ofNullable(null);
        Mockito.when(families.findById(anyInt())).thenReturn(family);
        doNothing().when(compatibilityConstraintsAPI).deleteCascade(anyInt());
        Mockito.when(componentAPI.deleteCascade(anyInt())).thenReturn(true);
        doNothing().when(families).deleteById(anyInt());
        assertEquals(false, this.componentFamilyAPI.deleteEntity(0));
    }



}
