package com.adobe.prj.api;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.adobe.RequestResponseTuple;
import com.adobe.prj.entity.AdminDto;
import com.adobe.prj.entity.Room;
import com.adobe.prj.entity.RoomLayout;
import com.adobe.prj.model.AuthenticationRequest;
import com.adobe.prj.service.RoomLayoutService;
import com.adobe.prj.service.RoomService;
import com.adobe.prj.util.RoomBookingType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Transactional
//@WebMvcTest(RoomController.class)
public class RoomControllerTest {
	
//	@MockBean
//	private RoomService service;
//	
//	@MockBean
//	private RoomLayoutService layoutService;
//	
//	@MockBean
//	private AdminService adminService;
//	
//	@MockBean
//    private JwtUtil jwtUtil;
	

    @Autowired
    private MockMvc mockMvc;
    
    static ObjectMapper mapper = new ObjectMapper();

    static {
      mapper.findAndRegisterModules();
    }
    
    static final String email="test@email.com";
    static final String pass="pass";

    @Before
    public void setup() throws Exception {
       registerUser();
    }
    
   
    @Test
    public void testCreateRoom_validRequest_Success() throws Exception {
    	
    	 List<RequestResponseTuple<Room, Room>> tuples =
    		        mapper.readValue(
    		            new File("src/test/resources/room/room_create_request_response.json"),
    		            new TypeReference<List<RequestResponseTuple<Room, Room>>>() {});
    	
    	 String jwt=getJwtToken();
    	 
    	    for (RequestResponseTuple<Room, Room> tuple : tuples) {

    	        String response =
    	            mockMvc
    	                .perform(
    	                    post("/api/rooms")
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
    public void testGetRooms_validRequest_success() throws Exception {

      testCreateRoom_validRequest_Success();
      String jwt= getJwtToken();

     String response= mockMvc
              .perform(
                      get("/api/rooms")
                              .contentType(MediaType.APPLICATION_JSON)
                              .header("Authorization", "Bearer " + jwt))
              .andExpect(status().is2xxSuccessful())
              .andReturn()
              .getResponse()
              .getContentAsString();

      List<Room> rooms=mapper.readValue(response, new TypeReference<List<Room>>() {
      });

      Assert.assertEquals(1,rooms.size());

      for (Room room : rooms) {

        String responseRoom= mockMvc
                .perform(
                        get("/api/rooms/"+room.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + jwt))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assert.assertNotNull(mapper.readValue(responseRoom, Room.class));


      }
    }
    
    @Test
    public void testDeleteRooms_validRequest_success() throws Exception {

      testCreateRoom_validRequest_Success();
      String jwt= getJwtToken();

      String response= mockMvc
              .perform(
                      get("/api/rooms")
                              .contentType(MediaType.APPLICATION_JSON)
                              .header("Authorization", "Bearer " + jwt))
              .andExpect(status().is2xxSuccessful())
              .andReturn()
              .getResponse()
              .getContentAsString();

      List<Room> rooms=mapper.readValue(response, new TypeReference<List<Room>>() {
      });


        mockMvc
                .perform(
                        delete("/api/rooms/delete/"+rooms.get(0).getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + jwt))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }
    
    @Test
    public void testUpdateRoom_validRequest_success() throws Exception {

      testCreateRoom_validRequest_Success();
      String jwt= getJwtToken();

      String response= mockMvc
              .perform(
                      get("/api/rooms")
                              .contentType(MediaType.APPLICATION_JSON)
                              .header("Authorization", "Bearer " + jwt))
              .andExpect(status().is2xxSuccessful())
              .andReturn()
              .getResponse()
              .getContentAsString();

      List<Room> rooms=mapper.readValue(response, new TypeReference<List<Room>>() {
      });


      rooms.get(0).setCapacity(15);
      rooms.get(0).setImageUrl("api/image1");
      
      String putResponseString=mockMvc
              .perform(
                      put("/api/rooms/"+rooms.get(0).getId())
                              .contentType(MediaType.APPLICATION_JSON)
                              .content(mapper.writeValueAsString(rooms.get(0)))
                              .header("Authorization", "Bearer " + jwt)
              )
              .andExpect(status().is2xxSuccessful())
              .andReturn()
              .getResponse()
              .getContentAsString();

      Room putResponse=mapper.readValue(putResponseString,Room.class);
      Assert.assertEquals(15,putResponse.getCapacity(),0);
      Assert.assertEquals("api/image1",putResponse.getImageUrl());

    }
    
    @Test
    public void testGetRoomsForUser_validRequest_success() throws Exception {

      testCreateRoom_validRequest_Success();
      String jwt= getJwtToken();

     String response= mockMvc
              .perform(
                      get("/api/rooms/user")
                              .contentType(MediaType.APPLICATION_JSON)
                              .header("Authorization", "Bearer " + jwt))
              .andExpect(status().is2xxSuccessful())
              .andReturn()
              .getResponse()
              .getContentAsString();

      List<Room> rooms=mapper.readValue(response, new TypeReference<List<Room>>() {
      });

      Assert.assertEquals(0,rooms.size());

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