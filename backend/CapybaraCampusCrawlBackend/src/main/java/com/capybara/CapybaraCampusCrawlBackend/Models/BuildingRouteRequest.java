package com.capybara.CapybaraCampusCrawlBackend.Models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * BuildingRouteRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-18T22:02:47.023914Z[Etc/UTC]")
public class BuildingRouteRequest   {

  @JsonProperty("fromBuilding")
  private BuildingLocation fromBuilding;

  @JsonProperty("toBuilding")
  private BuildingLocation toBuilding;

  public BuildingRouteRequest fromBuilding(BuildingLocation fromBuilding) {
    this.fromBuilding = fromBuilding;
    return this;
  }

  /**
   * Get fromBuilding
   * @return fromBuilding
  */
  @Valid 
  @Schema(name = "fromBuilding", required = false)
  public BuildingLocation getFromBuilding() {
    return fromBuilding;
  }

  public void setFromBuilding(BuildingLocation fromBuilding) {
    this.fromBuilding = fromBuilding;
  }

  public BuildingRouteRequest toBuilding(BuildingLocation toBuilding) {
    this.toBuilding = toBuilding;
    return this;
  }

  /**
   * Get toBuilding
   * @return toBuilding
  */
  @Valid 
  @Schema(name = "toBuilding", required = false)
  public BuildingLocation getToBuilding() {
    return toBuilding;
  }

  public void setToBuilding(BuildingLocation toBuilding) {
    this.toBuilding = toBuilding;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BuildingRouteRequest buildingRouteRequest = (BuildingRouteRequest) o;
    return Objects.equals(this.fromBuilding, buildingRouteRequest.fromBuilding) &&
        Objects.equals(this.toBuilding, buildingRouteRequest.toBuilding);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromBuilding, toBuilding);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BuildingRouteRequest {\n");
    sb.append("    fromBuilding: ").append(toIndentedString(fromBuilding)).append("\n");
    sb.append("    toBuilding: ").append(toIndentedString(toBuilding)).append("\n");
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

