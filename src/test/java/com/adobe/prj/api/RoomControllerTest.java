package com.adobe.prj.api;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hamcrest.core.IsInstanceOf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.adobe.prj.dao.AdminDao;
import com.adobe.prj.dao.RoomLayoutDao;
import com.adobe.prj.entity.Room;
import com.adobe.prj.entity.RoomLayout;
import com.adobe.prj.service.AdminService;
import com.adobe.prj.service.RoomLayoutService;
import com.adobe.prj.service.RoomService;
import com.adobe.prj.util.JwtUtil;
import com.adobe.prj.util.RoomBookingType;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(RoomController.class)
public class RoomControllerTest {
	
	private static final Class RoomBookingType = null;

	@MockBean
	private RoomService service;
	
	@MockBean
	private AdminService adminService;
	
	@MockBean
	private AdminDao adminDao;
	
	@MockBean
	private RoomLayoutDao roomLayoutDao;
	
	@MockBean
	private RoomLayoutService roomLayoutService;
	
	@MockBean
	private JwtUtil jwtUtil;
//
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	private Room room;
	
	@Mock
	private RoomBookingType bookingType;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

//
	@Test
	public void getRoomsTest() throws Exception{
		List<Room> rooms = Arrays.asList(
				new Room("Small Conference Room",1000.00,25,100.00,0,
						"/images/small_conference_room.png",true,
						"Small conference room with maximum capacity of 25 people",
						new RoomBookingType(true,true,true),
						new ArrayList<RoomLayout>()));
//		MOCKING
		when(service.getRooms()).thenReturn(rooms);
		
		// @formatter:off
		mockMvc.perform(get("/api/rooms"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id",is(0)))
				.andExpect(jsonPath("$[0].title",is("Small Conference Room")))
				.andExpect(jsonPath("$[0].pricePerDay", is(1000.00)))
				.andExpect(jsonPath("$[0].capacity", is(25)))
				.andExpect(jsonPath("$[0].pricePerHour", is(100.00)))
				.andExpect(jsonPath("$[0].bookings", is(0)))
				.andExpect(jsonPath("$[0].imageUrl", is("/images/small_conference_room.png")))
				.andExpect(jsonPath("$[0].status", is(true)))
				.andExpect(jsonPath("$[0].description", is("Small conference room with maximum capacity of 25 people")))
				.andExpect(jsonPath("$[0].bookingType.hourly",is(true)))
				.andExpect(jsonPath("$[0].bookingType.halfDay",is(true)))
				.andExpect(jsonPath("$[0].bookingType.fullDay",is(true)));
				
		// @formatter:on
		verify(service, times(1)).getRooms();
		verifyNoMoreInteractions(service);
	}
	
	
	
	
}
