package com.adobe.prj.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.adobe.RequestResponseTuple;
import com.adobe.prj.entity.AdminDto;
import com.adobe.prj.entity.Equipment;
import com.adobe.prj.model.AuthenticationRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.ValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Transactional
public class EquipmentControllerTest {

  static ObjectMapper mapper = new ObjectMapper();

  static {
    mapper.findAndRegisterModules();
  }

  @Autowired private MockMvc mockMvc;

   static final String email="test@email.com";
  static final String pass="pass";

  @Before
  public void setup() throws Exception {
     registerUser();
  }

  @Test
  public void testCreateEquipment_validRequest_Success() throws Exception {

    List<RequestResponseTuple<Equipment, Equipment>> tuples =
        mapper.readValue(
            new File("src/test/resources/equipment/equipment_create_request_response.json"),
            new TypeReference<List<RequestResponseTuple<Equipment, Equipment>>>() {});


    String jwt=getJwtToken();

    for (RequestResponseTuple<Equipment, Equipment> tuple : tuples) {

      String response =
          mockMvc
              .perform(
                  post("/api/equipments")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(mapper.writeValueAsString(tuple.getRequest()))
                      .header("Authorization", "Bearer " + jwt))
              .andExpect(status().is2xxSuccessful())
              .andReturn()
              .getResponse()
              .getContentAsString();

      JSONAssert.assertEquals(
          mapper.writeValueAsString(tuple.getResponse()),
          response,
          new CustomComparator(
              JSONCompareMode.STRICT,
              Customization.customization(
                  "id",
                  new ValueMatcher() {
                	  @Override
                	  public boolean equal(Object o1, Object o2) {
                      return true;
                    }
                  })));
    }
  }


  @Test
  public void testGetEquipments_validRequest_success() throws Exception {

    testCreateEquipment_validRequest_Success();
    String jwt= getJwtToken();

   String response= mockMvc
            .perform(
                    get("/api/equipments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + jwt))
            .andExpect(status().is2xxSuccessful())
            .andReturn()
            .getResponse()
            .getContentAsString();

    List<Equipment> equipments=mapper.readValue(response, new TypeReference<List<Equipment>>() {
    });

    Assert.assertEquals(1,equipments.size());

    for (Equipment equipment : equipments) {

      String responseEquipment= mockMvc
              .perform(
                      get("/api/equipments/"+equipment.getId())
                              .contentType(MediaType.APPLICATION_JSON)
                              .header("Authorization", "Bearer " + jwt))
              .andExpect(status().is2xxSuccessful())
              .andReturn()
              .getResponse()
              .getContentAsString();

      Assert.assertNotNull(mapper.readValue(responseEquipment, Equipment.class));


    }
  }


  @Test
  public void testDeleteEquipments_validRequest_success() throws Exception {

    testCreateEquipment_validRequest_Success();
    String jwt= getJwtToken();

    String response= mockMvc
            .perform(
                    get("/api/equipments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + jwt))
            .andExpect(status().is2xxSuccessful())
            .andReturn()
            .getResponse()
            .getContentAsString();

    List<Equipment> equipments=mapper.readValue(response, new TypeReference<List<Equipment>>() {
    });


      mockMvc
              .perform(
                      delete("/api/equipments/"+equipments.get(0).getId())
                              .contentType(MediaType.APPLICATION_JSON)
                              .header("Authorization", "Bearer " + jwt))
              .andExpect(status().is2xxSuccessful())
              .andReturn()
              .getResponse()
              .getContentAsString();

  }

  @Test
  public void testUpdateEquipment_validRequest_success() throws Exception {

    testCreateEquipment_validRequest_Success();
    String jwt= getJwtToken();

    String response= mockMvc
            .perform(
                    get("/api/equipments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + jwt))
            .andExpect(status().is2xxSuccessful())
            .andReturn()
            .getResponse()
            .getContentAsString();

    List<Equipment> equipments=mapper.readValue(response, new TypeReference<List<Equipment>>() {
    });


    equipments.get(0).setPrice(111111);
    String putResponseString=mockMvc
            .perform(
                    put("/api/equipments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(equipments.get(0)))
                            .header("Authorization", "Bearer " + jwt)
            )
            .andExpect(status().is2xxSuccessful())
            .andReturn()
            .getResponse()
            .getContentAsString();

    Equipment putResponse=mapper.readValue(putResponseString,Equipment.class);
    Assert.assertEquals(111111,putResponse.getPrice(),0);

  }


  private void registerUser() throws Exception {
    AdminDto adminDto = new AdminDto();
    adminDto.setEmail(email);
    adminDto.setPassword(pass);
    String registerRes =
        mockMvc
            .perform(
                post("/api/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(adminDto)))
            .andExpect(status().is2xxSuccessful())
            .andReturn()
            .getResponse()
            .getContentAsString();

  }

  private String getJwtToken() throws Exception {
    AuthenticationRequest authenticationRequest = new AuthenticationRequest();
    authenticationRequest.setUsername("test@email.com");
    authenticationRequest.setPassword("pass");

    String res =
        mockMvc
            .perform(
                post("/api/authenticate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(authenticationRequest)))
            .andExpect(status().is2xxSuccessful())
            .andReturn()
            .getResponse()
            .getContentAsString();

    return mapper.readValue(res, new TypeReference<Map<String, String>>() {}).get("jwt");
  }
}
