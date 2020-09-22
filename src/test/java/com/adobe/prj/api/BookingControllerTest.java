package com.adobe.prj.api;



import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.Console;
import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
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
import com.adobe.prj.util.BookingSchedule;
import com.adobe.prj.util.BookingType;
import com.adobe.prj.util.RoomBookingType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.bytebuddy.asm.Advice.Local;



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
    
    
    public void addingRooms() throws Exception{
       	
    	String jwt=getJwtToken();

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

	}
    

    @Before
    public void setup() throws Exception {
       registerUser();
       
    }
    
     
    @Test
    public void addBookingTest() throws Exception {
    	
    	String jwt=getJwtToken();
    	 	    
    	addingRooms();   	
 	    
    	 List<RequestResponseTuple<Booking, Booking>> tuples =
    		        mapper.readValue(
    		            new File("src/test/resources/booking/booking4_for_add.json"),
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
    	        
    	      System.out.println("printing bookings from add:***************************************");
  	      	  System.out.println(response);
    	      }
    	  	    
    }
    
    
    @Test
    public void getBookingsTest() throws Exception {
    	
        addingRooms();

      String jwt= getJwtToken();
      
      List<RequestResponseTuple<Booking, Booking>> tuples =
    		        mapper.readValue(
    		            new File("src/test/resources/booking/booking2_for_get.json"),
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
    	                .andReturn()
    	                .getResponse()
    	                .getContentAsString();

    	      }
      
      

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
      
      Assert.assertEquals(1,bookings.size());
	  
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
        
        
      	  System.out.println("printing bookings from get:**************************************");
      	  System.out.println(responseBooking);
        
        
        Assert.assertNotNull(mapper.readValue(responseBooking, Booking.class));

      }
      
      
    }
    
    @Test
    public void deleteBookingTest() throws Exception {
    	
    	   String jwt= getJwtToken();
    	   
    	   addingRooms();

    	    
    	    List<RequestResponseTuple<Booking, Booking>> tuples =
    		        mapper.readValue(
    		            new File("src/test/resources/booking/booking3_for_delete.json"),
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
    	                .andReturn()
    	                .getResponse()
    	                .getContentAsString();

    	      }
    	  
    
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
      for(Booking b:bookings) {
    	  System.out.println("printing bookings from delete:********************************************");
      	  System.out.println("booking id: "+b.getId());
      	  System.out.println("room id: "+b.getRoom().getId());
      	  System.out.println("roomlayout id: "+b.getRoomLayout().getId());
      	  System.out.println("roomlayout title: "+b.getRoomLayout().getTitle());
      }

        mockMvc
                .perform(
                        delete("/api/bookings/"+bookings.get(0).getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + jwt))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }
    
    @Test
    public void updateBookingTest() throws Exception{
    	String jwt=getJwtToken();
 	    
    	addingRooms();
    		    
    	 List<RequestResponseTuple<Booking, Booking>> tuples =
    		        mapper.readValue(
    		            new File("src/test/resources/booking/booking1_for_update_post.json"),
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
    	                .andReturn()
    	                .getResponse()
    	                .getContentAsString();
    	        
  	    	  System.out.println("Printing post response:**************************************************");
  	    	  System.out.println(response);

    	      }
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
    	      
    	      System.out.println("Printing get response:**************************************************");
  	    	  System.out.println(response);
  	    	  
 	      
  	    	  bookings.get(0).setAttendees(10);
  	    	  bookings.get(0).setTotalCost(20000);

    	      
//    	      List<RequestResponseTuple<Booking, Booking>> tuplesPut =
//      		        mapper.readValue(
//      		            new File("src/test/resources/booking/booking1_for_update_put.json"),
//      		            new TypeReference<List<RequestResponseTuple<Booking, Booking>>>() {});
    	      
    	      String putResponseString=mockMvc
    	              .perform(
    	                      put("/api/bookings")
    	                              .contentType(MediaType.APPLICATION_JSON)
    	                              .content(mapper.writeValueAsString(bookings.get(0)))
    	                              .header("Authorization", "Bearer " + jwt)
    	              )
    	              .andExpect(status().is2xxSuccessful())
    	              .andReturn()
    	              .getResponse()
    	              .getContentAsString();
    	    	  
    	    	  System.out.println("Printing put response:**************************************************");
    	    	  System.out.println(putResponseString);    	    	  
    	    	   
//    	    	  Booking putResponse=mapper.readValue(putResponseString,Booking.class);
//        	      Assert.assertEquals(10,putResponse.getAttendees());
//        	      Assert.assertEquals(20000.0,putResponse.getTotalCost(),0);
    	    	    	        	    	   	         	      
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