package com.adobe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adobe.dao.RoomDao;
import com.adobe.dao.RoomLayoutDao;
import com.adobe.entity.Room;
import com.adobe.entity.RoomLayout;

@SpringBootApplication
public class MeetingroomApplication implements CommandLineRunner{

	@Autowired
	RoomDao roomDao;
	
	@Autowired
	RoomLayoutDao roomLayoutDao;
	
	@Override
	public void run(String...args) throws Exception {
		
		Room room = new Room();
		room.setBookings(0);
		room.setCapacity(100);
		room.setImageUrl("large_conference_room_img");
		room.setPricePerDay(1000);
		room.setStatus(true);
		room.setTitle("large conference room");
		
		RoomLayout roomLayout = new RoomLayout();
		roomLayout.setImageUrl("theatre_img");
		roomLayout.setTitle("theatre");
		
		room.getRoomLayouts().add(roomLayout);
		roomLayout.getRooms().add(room);
		
		roomDao.save(room);
		roomLayoutDao.save(roomLayout);
		
	}
    public static void main(String[] args) {
    	SpringApplication.run(MeetingroomApplication.class, args);
    }
	
}
