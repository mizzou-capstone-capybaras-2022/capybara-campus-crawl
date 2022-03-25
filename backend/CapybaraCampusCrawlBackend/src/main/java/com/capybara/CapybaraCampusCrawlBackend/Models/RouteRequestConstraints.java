package com.capybara.CapybaraCampusCrawlBackend.Models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * RouteRequestConstraints
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-18T22:02:47.023914Z[Etc/UTC]")
public class RouteRequestConstraints   {

  @JsonProperty("preferIndoors")
  private Boolean preferIndoors;

  @JsonProperty("avoidCrowds")
  private Boolean avoidCrowds;

  @JsonProperty("pitstops")
  private PitstopConstraint pitstops;

  @JsonProperty("timeConstraint")
  private TimeConstraint timeConstraint;

  public RouteRequestConstraints preferIndoors(Boolean preferIndoors) {
    this.preferIndoors = preferIndoors;
    return this;
  }

  /**
   * Get preferIndoors
   * @return preferIndoors
  */
  
  @Schema(name = "preferIndoors", required = false)
  public Boolean getPreferIndoors() {
    return preferIndoors;
  }

  public void setPreferIndoors(Boolean preferIndoors) {
    this.preferIndoors = preferIndoors;
  }

  public RouteRequestConstraints avoidCrowds(Boolean avoidCrowds) {
    this.avoidCrowds = avoidCrowds;
    return this;
  }

  /**
   * Get avoidCrowds
   * @return avoidCrowds
  */
  
  @Schema(name = "avoidCrowds", required = false)
  public Boolean getAvoidCrowds() {
    return avoidCrowds;
  }

  public void setAvoidCrowds(Boolean avoidCrowds) {
    this.avoidCrowds = avoidCrowds;
  }

  public RouteRequestConstraints pitstops(PitstopConstraint pitstops) {
    this.pitstops = pitstops;
    return this;
  }

  /**
   * Get pitstops
   * @return pitstops
  */
  @Valid 
  @Schema(name = "pitstops", required = false)
  public PitstopConstraint getPitstops() {
    return pitstops;
  }

  public void setPitstops(PitstopConstraint pitstops) {
    this.pitstops = pitstops;
  }

  public RouteRequestConstraints timeConstraint(TimeConstraint timeConstraint) {
    this.timeConstraint = timeConstraint;
    return this;
  }

  /**
   * Get timeConstraint
   * @return timeConstraint
  */
  @Valid 
  @Schema(name = "timeConstraint", required = false)
  public TimeConstraint getTimeConstraint() {
    return timeConstraint;
  }

  public void setTimeConstraint(TimeConstraint timeConstraint) {
    this.timeConstraint = timeConstraint;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RouteRequestConstraints routeRequestConstraints = (RouteRequestConstraints) o;
    return Objects.equals(this.preferIndoors, routeRequestConstraints.preferIndoors) &&
        Objects.equals(this.avoidCrowds, routeRequestConstraints.avoidCrowds) &&
        Objects.equals(this.pitstops, routeRequestConstraints.pitstops) &&
        Objects.equals(this.timeConstraint, routeRequestConstraints.timeConstraint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(preferIndoors, avoidCrowds, pitstops, timeConstraint);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RouteRequestConstraints {\n");
    sb.append("    preferIndoors: ").append(toIndentedString(preferIndoors)).append("\n");
    sb.append("    avoidCrowds: ").append(toIndentedString(avoidCrowds)).append("\n");
    sb.append("    pitstops: ").append(toIndentedString(pitstops)).append("\n");
    sb.append("    timeConstraint: ").append(toIndentedString(timeConstraint)).append("\n");
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

