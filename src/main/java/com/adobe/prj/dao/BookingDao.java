package com.adobe.prj.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adobe.prj.entity.Booking;

public interface BookingDao extends JpaRepository<Booking, Integer>{
	
	@Query(value = "select * from booking where user_fk= :pr", nativeQuery = true) 
	public List<Booking> getByUserId(@Param("pr") int id);
	
	@Query(value = "select * from booking where room_fk= :pr", nativeQuery = true)
	public List<Booking> getByRoomId(@Param("pr") int id);
	
	@Query(value = "select * from booking order by id desc limit 3", nativeQuery = true)
	public List<Booking> getLatestBookings();

	@Query(value = "select * from booking where booked_for = :pr", nativeQuery = true)
	public List<Booking> getBookingByDate(@Param("pr") String date);

	@Query(value = "select count(*) from booking where booked_for = :pr", nativeQuery = true)
	public long getBookingsCountByDate(@Param("pr") LocalDate date);

	@Query(value = "select count(*) from booking where booked_on = :pr", nativeQuery = true)
	public long getBookingsCountMadeToday(@Param("pr") LocalDate now);
	
	@Query(value = "select time_slots from booking where room_fk = :id and booked_for = :date and status<>2 and status<>3", nativeQuery = true)
	List<String> getTimeSlotsById(@Param("id") int id, @Param("date") String date);

	@Query(value = "select count(*) from booking where room_fk = :id and booked_for >= :date and status<>2 and status<>3", nativeQuery = true)
	public Long getFutureBookingsCountByRoomId(@Param("id") int id, @Param("date") LocalDate date);
	
	@Query(value = "select * from booking where layout_fk= :pr", nativeQuery = true)
	public List<Booking> getByLayoutId(@Param("pr") int id);
	
	@Query(value="select * from booking where status<>3", nativeQuery = true)
	public List<Booking> findAll();
}
