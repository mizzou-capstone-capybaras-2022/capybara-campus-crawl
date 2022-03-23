package com.capybara.CapybaraCampusCrawlBackend.Models;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class OneOfBuildingLocationPoint {
	  @JsonProperty("latitude")
	  private Double latitude;

	  @JsonProperty("longitude")
	  private Double longitude;

	  @JsonProperty("buildingId")
	  private BigDecimal buildingId;

	  public OneOfBuildingLocationPoint buildingId(BigDecimal buildingId) {
	    this.buildingId = buildingId;
	    return this;
	  }

	  public BuildingLocation asBuildingLocation() {
		  return (new BuildingLocation())
				  	.buildingId(buildingId);
	  }
	  
	  public Point asPoint() {
		  return (new Point())
				  .latitude(latitude)
				  .longitude(longitude);
	  }
	  
	  /**
	   * Get buildingId
	   * @return buildingId
	  */
	  @Valid 
	  @Schema(name = "buildingId", required = false)
	  public BigDecimal getBuildingId() {
	    return buildingId;
	  }

	  public void setBuildingId(BigDecimal buildingId) {
	    this.buildingId = buildingId;
	  }
	  
	  /**
	   * Get latitude
	   * @return latitude
	  */
	  
	  @Schema(name = "latitude", required = false)
	  public Double getLatitude() {
	    return latitude;
	  }

	  public void setLatitude(Double latitude) {
	    this.latitude = latitude;
	  }

	  public OneOfBuildingLocationPoint latitude(Double latitude) {
		    this.latitude = latitude;
		    return this;
	  }
	  
	  public OneOfBuildingLocationPoint longitude(Double longitude) {
	    this.longitude = longitude;
	    return this;
	  }

	  /**
	   * Get longitude
	   * @return longitude
	  */
	  
	  @Schema(name = "longitude", required = false)
	  public Double getLongitude() {
	    return longitude;
	  }

	  public void setLongitude(Double longitude) {
	    this.longitude = longitude;
	  }

	  @Override
	  public boolean equals(Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    OneOfBuildingLocationPoint buildingLocationOrPoint = (OneOfBuildingLocationPoint) o;
	    return Objects.equals(this.latitude, buildingLocationOrPoint.latitude) &&
	        Objects.equals(this.longitude, buildingLocationOrPoint.longitude) &&
	        Objects.equals(this.buildingId, buildingLocationOrPoint.buildingId);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(latitude, longitude, buildingId);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class OneOfBuildingLocationPoint {\n");
	    sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
	    sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
	    sb.append("    buildingId: ").append(toIndentedString(buildingId)).append("\n");
	    sb.append("}");
	    return sb.toString();
	  }

	  /**
	   * Convert the given object to string with each line indented by 4 spaces
	   * (except the first line).
	   */
	  private String toIndentedString(Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	  }
}
