package com.adobe.prj.api;



import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import com.adobe.prj.entity.Booking;
import com.adobe.prj.entity.Equipment;
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
public class BookingControllerTest {

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
    public void addBookingTest() throws Exception {
    	
    	String jwt=getJwtToken();
    	
      	 List<RequestResponseTuple<RoomLayout, RoomLayout>> tuplesL =
 		        mapper.readValue(
 		            new File("src/test/resources/layout/layout_create_request_response.json"),
 		            new TypeReference<List<RequestResponseTuple<RoomLayout, RoomLayout>>>() {});
 	
 	 
 	    for (RequestResponseTuple<RoomLayout, RoomLayout> tupleL : tuplesL) {

 	        String responseL =
 	            mockMvc
 	                .perform(
 	                    post("/api/roomlayouts")
 	                        .contentType(MediaType.APPLICATION_JSON)
 	                        .content(mapper.writeValueAsString(tupleL.getRequest()))
 	                        .header("Authorization", "Bearer " + jwt))
 	                .andExpect(status().is2xxSuccessful())
 	                .andReturn()
 	                .getResponse()
 	                .getContentAsString();
     	
 	    }
    	
      	 List<RequestResponseTuple<Room, Room>> tuplesR =
 		        mapper.readValue(
 		            new File("src/test/resources/room/room_for_booking.json"),
 		            new TypeReference<List<RequestResponseTuple<Room, Room>>>() {});
 	
 	 
 	 
 	    for (RequestResponseTuple<Room, Room> tupleR : tuplesR) {

 	        String responseR =
 	            mockMvc
 	                .perform(
 	                    post("/api/rooms")
 	                        .contentType(MediaType.APPLICATION_JSON)
 	                        .content(mapper.writeValueAsString(tupleR.getRequest()))
 	                        .header("Authorization", "Bearer " + jwt))
 	                .andExpect(status().is2xxSuccessful())
 	                .andReturn()
 	                .getResponse()
 	                .getContentAsString();
 	    }
 	    
    	 List<RequestResponseTuple<Booking, Booking>> tuples =
    		        mapper.readValue(
    		            new File("src/test/resources/booking/booking_create_request_response.json"),
    		            new TypeReference<List<RequestResponseTuple<Booking, Booking>>>() {});
    	 
    	    for (RequestResponseTuple<Booking, Booking> tuple : tuples) {

    	        String response =
    	            mockMvc
    	                .perform(
    	                    post("/api/bookings")
    	                        .contentType(MediaType.APPLICATION_JSON)
    	                        .content(mapper.writeValueAsString(tuple.getRequest()))
    	                        .header("Authorization", "Bearer " + jwt))
    	                .andExpect(status().is2xxSuccessful())
    	                .andExpect(jsonPath("$.id",is(1)))
    	                .andReturn()
    	                .getResponse()
    	                .getContentAsString();

    	      }
    }
    
    
    @Test
    public void getBookingsTest() throws Exception {

      String jwt= getJwtToken();

     String response= mockMvc
              .perform(
                      get("/api/bookings")
                              .contentType(MediaType.APPLICATION_JSON)
                              .header("Authorization", "Bearer " + jwt))
              .andExpect(status().is2xxSuccessful())
              .andReturn()
              .getResponse()
              .getContentAsString();

      List<Booking> bookings=mapper.readValue(response, new TypeReference<List<Booking>>() {
      });


      for (Booking booking : bookings) {
    	  int id = booking.getId();
    	  
        String responseBooking= mockMvc
                .perform(
                        get("/api/bookings/"+id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + jwt))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assert.assertNotNull(mapper.readValue(responseBooking, Booking.class));


      }
    }
    
//    @Test
//    public void deleteBookingTest() throws Exception {
//    	addBookingTest();
//    String jwt= getJwtToken();
//
//      String response= mockMvc
//              .perform(
//                      get("/api/bookings")
//                              .contentType(MediaType.APPLICATION_JSON)
//                              .header("Authorization", "Bearer " + jwt))
//              .andExpect(status().is2xxSuccessful())
//              .andReturn()
//              .getResponse()
//              .getContentAsString();
//
//      List<Booking> bookings=mapper.readValue(response, new TypeReference<List<Booking>>() {
//      });
//      for(Booking b:bookings) {
//    	  System.out.println(b.getId());
//    	  System.out.println(b.getRoom().getId());
//    	  System.out.println(b.getRoomLayout().getId());
//    	  System.out.println(b.getRoomLayout().getTitle());
//      }
//
//        mockMvc
//                .perform(
//                        delete("/api/bookings/"+bookings.get(0).getId())
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .header("Authorization", "Bearer " + jwt))
//                .andExpect(status().is2xxSuccessful())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//    }
//    

    
    
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