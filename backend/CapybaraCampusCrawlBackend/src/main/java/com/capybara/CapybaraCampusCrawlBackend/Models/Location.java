package com.capybara.CapybaraCampusCrawlBackend.Models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * Location
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-18T22:02:47.023914Z[Etc/UTC]")
public class Location   {

  @JsonProperty("buildingId")
  private BigDecimal buildingId;

  @JsonProperty("latitude")
  private Double latitude;

  @JsonProperty("longitude")
  private Double longitude;

  public Location buildingId(BigDecimal buildingId) {
    this.buildingId = buildingId;
    return this;
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

  public Location latitude(Double latitude) {
    this.latitude = latitude;
    return this;
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

  public Location longitude(Double longitude) {
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
    Location location = (Location) o;
    return Objects.equals(this.buildingId, location.buildingId) &&
        Objects.equals(this.latitude, location.latitude) &&
        Objects.equals(this.longitude, location.longitude);
  }

  @Override
  public int hashCode() {
    return Objects.hash(buildingId, latitude, longitude);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Location {\n");
    sb.append("    buildingId: ").append(toIndentedString(buildingId)).append("\n");
    sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
    sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
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

