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
    	
    	List<RoomLayout> roomlayouts= 
    			Arrays.asList(
    					);
    	
        List<Room> rooms = 
                Arrays.asList(new Room(1,"Small Conference Room",5000,10,1000,5,"api/image",true,roomlayouts),
                		new Room(2,"Medium Conference Room",5000,10,1000,15,"api/image",true,roomlayouts),
                		new Room(3,"Large Conference Room",5000,10,1000,20,"api/image",true,roomlayouts));
        // mocking
        when(service.getRooms()).thenReturn(rooms);

        // @formatter:off
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Small Conference Room")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Medium Conference Room")));
        // @formatter:on
        verify(service, times(1)).getRooms();
        verifyNoMoreInteractions(service);
    }
	
	

}
