package com.adobe.prj.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.adobe.prj.service.CustomRoomSerializer;


@Table(name="roomlayout")
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class RoomLayout {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "layout_id")
	private int id;
	
	@Column(unique = true)
	@NotNull(message = "Room Layout Name cannot be NULL")
	private String title;
	
	private String imageUrl;
                     
	@ManyToMany(targetEntity = Room.class,
			fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinTable(
			name = "room_rlayout",
			inverseJoinColumns = { @JoinColumn(name = "room_id",nullable = false,updatable = false)},
			joinColumns = { @JoinColumn(name = "layout_id",nullable = false,updatable = false)},
			foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
			inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
			)
	@JsonSerialize(using = CustomRoomSerializer.class)
	List<Room> rooms = new ArrayList<>();

	public RoomLayout(int id, @NotNull(message = "Room Layout Name cannot be NULL") String title, String imageUrl,
			List<Room> rooms) {
		this.id = id;
		this.title = title;
		this.imageUrl = imageUrl;
		this.rooms = rooms;
	}
		
	public RoomLayout(int id, @NotNull(message = "Room Layout Name cannot be NULL") String title, String imageUrl) {
		super();
		this.id = id;
		this.title = title;
		this.imageUrl = imageUrl;
	}

	public RoomLayout() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

}
 