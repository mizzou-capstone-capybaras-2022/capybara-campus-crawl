package com.capybara.CapybaraCampusCrawlBackend.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "\"Room\"")
public class Room {

	@Id
	@Column(name="\"RoomID\"")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long roomId;
	
	@Column(name="\"Name\"")
	private String name;
	
	@Column(name="\"RoomNumber\"")
	private int roomNumber;
	
	@OneToOne
	@JoinColumn(name="\"NodeID\"", nullable=false)
	private GraphNode node;
	
	@OneToOne
	@JoinColumn(name="\"BuildingID\"", nullable=false)
	private Building building;
	
	public Room() {
		super();
	}
	
	public Room(String name, int roomNumber, GraphNode node, Building building) {
		this.name = name;
		this.roomNumber = roomNumber;
		this.node = node;
		this.building = building;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public GraphNode getNode() {
		return node;
	}

	public void setNode(GraphNode node) {
		this.node = node;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public Long getRoomId() {
		return roomId;
	}
	
	@Override
	public String toString() {
	  return String.format(
	      "Room[id=%d, name='%s', roomNumber=%d, buildingId=%d, nodeId=%d]",
	      roomId, name, roomNumber, building.getBuildingId(), node.getNodeID());
	}
	
}
