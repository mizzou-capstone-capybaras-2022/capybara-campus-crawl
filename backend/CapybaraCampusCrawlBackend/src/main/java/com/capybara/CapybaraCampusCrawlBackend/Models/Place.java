package com.capybara.CapybaraCampusCrawlBackend.Models;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "\"Place\"")
public class Place {

	@Id
	@Column(name="\"PlaceID\"")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long placeID;
	
	@OneToOne
	@JoinColumn(name="\"NodeID\"", nullable=false)
	private GraphNode node;
	
    @Column(name="\"PlaceType\"")
    @Convert(converter = PlaceTypeConverter.class)
    private PlaceType placeType;
	
	public Place(){
		super();
	}
	
	public Place(GraphNode node, String name, PlaceType placeType) {
		this.node = node;
		this.placeType = placeType;
	}

	public GraphNode getNode() {
		return node;
	}

	public void setNode(GraphNode node) {
		this.node = node;
	}

	public PlaceType getPlaceType() {
		return placeType;
	}

	public void setPlaceType(PlaceType placeType) {
		this.placeType = placeType;
	}

	public Long getPlaceID() {
		return placeID;
	}
	
	@Override
	public String toString() {
	  return String.format(
	      "Place[id=%d, placeType=%s, nodeId=%d]",
	      placeID, placeType.toString(), node.getNodeID());
	}
	
}
