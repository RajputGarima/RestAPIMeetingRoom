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

import com.adobe.prj.service.CustomRoomSerializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@Table(name="roomlayout")
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "layoutId")
public class RoomLayout {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "layout_id")
	private int layoutId;
	
	@Column(unique = true)
	@NotNull(message = "Room Layout Name cannot be NULL")
	private String title;
	
	private String imageUrl;
	                     
	@ManyToMany(targetEntity = Room.class,fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinTable(
			name = "room_rlayout",
			inverseJoinColumns = { @JoinColumn(name = "room_id",nullable = false,updatable = false)},
			joinColumns = { @JoinColumn(name = "layout_id",nullable = false,updatable = false)},
					foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
			        inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
			)
//	@JsonManagedReference
//	@JsonBackReference
	@JsonSerialize(using = CustomRoomSerializer.class)
	List<Room> rooms = new ArrayList<>();
	
	public int getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(int layoutId) {
		this.layoutId = layoutId;
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
 