package com.capybara.CapybaraCampusCrawlBackend.Models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity()
@Table(name = "\"PIMetrics\"")
public class PiMetric {
	@Id
	@Column(name="\"MetricsID\"")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long metricId;
	
	@OneToOne
	@JoinColumn(name="\"NodeID\"", nullable=false)
	private GraphNode node;

	@Column(name="\"Time\"")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime time;
	
	@Column(name="\"Intensity\"")
	private int intensity;
	
	public PiMetric() {
		super();
	}
	
	public PiMetric(GraphNode node, LocalDateTime time, int intensity) {
		this.node = node;
		this.time = time;
		this.intensity = intensity;
	}

	public Long getMetricId() {
		return metricId;
	}

	public void setMetricId(Long metricId) {
		this.metricId = metricId;
	}

	public GraphNode getNode() {
		return node;
	}

	public void setNode(GraphNode node) {
		this.node = node;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

	@Override
	public String toString() {
	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
		
	  return String.format(
	      "PiMetric[id=%d, timestamp='%s', intensity=%d, nodeId=%d]",
	      metricId, time, intensity, node.getNodeID());
	}

	
}
