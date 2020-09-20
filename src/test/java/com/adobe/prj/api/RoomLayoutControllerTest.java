package com.adobe.prj.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.text.StyleContext.SmallAttributeSet;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.adobe.prj.dao.AdminDao;
import com.adobe.prj.dao.RoomLayoutDao;
import com.adobe.prj.entity.Room;
import com.adobe.prj.entity.RoomLayout;
import com.adobe.prj.service.AdminService;
import com.adobe.prj.service.RoomLayoutService;
import com.adobe.prj.service.RoomService;
import com.adobe.prj.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(RoomLayoutController.class)
public class RoomLayoutControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
    private FilterChainProxy springSecurityFilterChain;
	
	@MockBean
	private RoomLayoutService service;
	
	@MockBean
	private RoomService roomService;
	
	@MockBean
	private AdminService adminService;
	
	@MockBean
    private JwtUtil jwtUtil;
	

    @Autowired
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
          .addFilter(springSecurityFilterChain).build();
    }
    
    
    @Test
    public void getRoomLayoutsTest() throws Exception {
    	List<Room> rooms = new ArrayList<>();
    	
    	List<RoomLayout> roomLayouts = Arrays.asList(new RoomLayout(1,"Classroom","api/image"),
    										new RoomLayout(2,"Round","api/image1",rooms));
    
        // mocking
        when(service.getRoomLayouts()).thenReturn(roomLayouts);

        // @formatter:off
        mockMvc.perform(get("/api/roomlayouts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Classroom")))
                .andExpect(jsonPath("$[0].imageUrl", is("api/image")))
                .andExpect(jsonPath("$[0].rooms", hasSize(0)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Round")))
        		.andExpect(jsonPath("$[1].imageUrl", is("api/image1")))
        		.andExpect(jsonPath("$[1].rooms", hasSize(0))); 

        
        // @formatter:on
        verify(service, times(1)).getRoomLayouts();
        verifyNoMoreInteractions(service);
    }
    
	@Test
	public void addRoomLayoutTest() throws Exception {
		List<Room> rooms = new ArrayList<>();
		RoomLayout l1 = new RoomLayout(0,"Classroom","api/image",rooms);
		RoomLayout l2 = new RoomLayout(1,"Classroom","api/image",rooms);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(l1); // get JSON for Product p
		
		// mocking if Product type is passed to service he should return a PK 10
		when(service.addRoomLayout(Mockito.any(RoomLayout.class))).thenReturn(l2);
		
			mockMvc.perform(post("/api/roomlayouts")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
			verify(service, times(1)).addRoomLayout(Mockito.any(RoomLayout.class));
	}
}