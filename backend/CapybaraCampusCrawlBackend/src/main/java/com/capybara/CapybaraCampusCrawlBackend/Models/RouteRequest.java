package com.capybara.CapybaraCampusCrawlBackend.Models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * RouteRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-23T06:29:46.400568Z[Etc/UTC]")
public class RouteRequest   {

  @JsonProperty("fromLocation")
  private Location fromLocation;

  @JsonProperty("toLocation")
  private Location toLocation;

  @JsonProperty("constraints")
  private RouteRequestConstraints constraints;

  public RouteRequest fromLocation(Location fromLocation) {
    this.fromLocation = fromLocation;
    return this;
  }

  /**
   * Get fromLocation
   * @return fromLocation
  */
  @Valid 
  @Schema(name = "fromLocation", required = false)
  public Location getFromLocation() {
    return fromLocation;
  }

  public void setFromLocation(Location fromLocation) {
    this.fromLocation = fromLocation;
  }

  public RouteRequest toLocation(Location toLocation) {
    this.toLocation = toLocation;
    return this;
  }

  /**
   * Get toLocation
   * @return toLocation
  */
  @Valid 
  @Schema(name = "toLocation", required = false)
  public Location getToLocation() {
    return toLocation;
  }

  public void setToLocation(Location toLocation) {
    this.toLocation = toLocation;
  }

  public RouteRequest constraints(RouteRequestConstraints constraints) {
    this.constraints = constraints;
    return this;
  }

  /**
   * Get constraints
   * @return constraints
  */
  @Valid 
  @Schema(name = "constraints", required = false)
  public RouteRequestConstraints getConstraints() {
    return constraints;
  }

  public void setConstraints(RouteRequestConstraints constraints) {
    this.constraints = constraints;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RouteRequest routeRequest = (RouteRequest) o;
    return Objects.equals(this.fromLocation, routeRequest.fromLocation) &&
        Objects.equals(this.toLocation, routeRequest.toLocation) &&
        Objects.equals(this.constraints, routeRequest.constraints);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromLocation, toLocation, constraints);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RouteRequest {\n");
    sb.append("    fromLocation: ").append(toIndentedString(fromLocation)).append("\n");
    sb.append("    toLocation: ").append(toIndentedString(toLocation)).append("\n");
    sb.append("    constraints: ").append(toIndentedString(constraints)).append("\n");
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

