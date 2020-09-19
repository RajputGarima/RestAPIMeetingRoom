package com.adobe.prj.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.adobe.prj.dao.AdminDao;
import com.adobe.prj.dao.RoomLayoutDao;
import com.adobe.prj.entity.Room;
import com.adobe.prj.entity.RoomLayout;
import com.adobe.prj.service.AdminService;
import com.adobe.prj.service.RoomLayoutService;
import com.adobe.prj.service.RoomService;
import com.adobe.prj.util.JwtUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(RoomController.class)
public class RoomControllerTest {
	
	@MockBean
	private RoomService service;
	
	@MockBean
	private RoomLayoutService layoutService;
	
	@MockBean
	private AdminService adminService;
	
	@MockBean
    private JwtUtil jwtUtil;
	

    @Autowired
    private MockMvc mockMvc;
    
    
    @Test
    public void getRoomsTest() throws Exception {
    	
    	List<RoomLayout> roomLayoutsDefault= 
    			Arrays.asList(new RoomLayout(1,"Classroom","api/image")
    					);
    	
    	List<Room> r= 
      			 Arrays.asList(new Room(1,"Small Conference Room",5000,10,1000,5,"api/image",true,roomLayoutsDefault));
    	
    	//new RoomLayout(id, title, imageUrl, rooms)
    	List<RoomLayout> roomLayouts= 
    			Arrays.asList(new RoomLayout(1,"Classroom","api/image"),
    					new RoomLayout(2,"Round","api/image1",r));
    	   	
    	//new Room(id, title, pricePerDay, capacity, pricePerHour, bookings, imageUrl, status, roomLayouts)
        List<Room> rooms = 
                Arrays.asList(new Room(1,"Small Conference Room",5000,10,1000,5,"api/image",true,roomLayouts),
                		new Room(2,"Medium Conference Room",8000,10,2000,15,"api/image",false,roomLayoutsDefault));
//                		new Room(3,"Large Conference Room",5000,10,1000,20,"api/image",true,roomlayouts));
        // mocking
        when(service.getRooms()).thenReturn(rooms);

        // @formatter:off
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Small Conference Room")))
                .andExpect(jsonPath("$[0].pricePerDay", is(5000.0)))
                .andExpect(jsonPath("$[0].capacity", is(10)))
                .andExpect(jsonPath("$[0].pricePerHour", is(1000.0)))
                .andExpect(jsonPath("$[0].bookings", is(5)))
                .andExpect(jsonPath("$[0].imageUrl", is("api/image")))
                .andExpect(jsonPath("$[0].status", is(true)))
                .andExpect(jsonPath("$[0].roomLayouts[0].id", is(1)))
                .andExpect(jsonPath("$[0].roomLayouts[0].title", is("Classroom")))
                .andExpect(jsonPath("$[0].roomLayouts[0].imageUrl", is("api/image")))
                .andExpect(jsonPath("$[0].roomLayouts[1].id", is(2)))
                .andExpect(jsonPath("$[0].roomLayouts[1].title", is("Round")))
                .andExpect(jsonPath("$[0].roomLayouts[1].imageUrl", is("api/image1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Medium Conference Room")))
        		.andExpect(jsonPath("$[1].pricePerDay", is(8000.0)))
        		.andExpect(jsonPath("$[1].capacity", is(10)))
        		.andExpect(jsonPath("$[1].pricePerHour", is(2000.0)))
        		.andExpect(jsonPath("$[1].bookings", is(15)))
        		.andExpect(jsonPath("$[1].imageUrl", is("api/image")))
        		.andExpect(jsonPath("$[1].status", is(false)))
        		.andExpect(jsonPath("$[1].roomLayouts[0].id", is(1)))
        		.andExpect(jsonPath("$[1].roomLayouts[0].title", is("Classroom")))
        		.andExpect(jsonPath("$[1].roomLayouts[0].imageUrl", is("api/image")));

        
        // @formatter:on
        verify(service, times(1)).getRooms();
        verifyNoMoreInteractions(service);
    }
	
	

}
