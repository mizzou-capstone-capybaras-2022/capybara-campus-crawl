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
@Table(name = "\"Door\"")
public class Door {
	
	@Id
	@Column(name="\"DoorID\"")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long doorId;
	
	@OneToOne
	@JoinColumn(name="\"NodeID\"", nullable=false)
	private GraphNode node;
	
	@OneToOne
	@JoinColumn(name="\"BuildingID\"", nullable=false)
	private Building building;
	
	Door(){
		super();
	}
	
	Door(GraphNode node, Building building){
		this.node = node;
		this.building = building;
	}
	
	@Override
	 public String toString() {
	   return String.format(
	       "Door[id=%d, NodeId='%d', BuildingId='%d']",
	       doorId, node.getNodeID(), building.getBuildingId());
	 }
	 
	
	
}
