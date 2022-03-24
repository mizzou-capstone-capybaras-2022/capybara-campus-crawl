package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.model.Building;
import org.openapitools.model.GraphNode;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Door
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-09T11:18:48.083513-06:00[America/Chicago]")
public class Door   {

  @JsonProperty("doorId")
  private Long doorId;

  @JsonProperty("node")
  private GraphNode node;

  @JsonProperty("building")
  private Building building;

  public Door doorId(Long doorId) {
    this.doorId = doorId;
    return this;
  }

  /**
   * Get doorId
   * @return doorId
  */
  
  @Schema(name = "doorId", required = false)
  public Long getDoorId() {
    return doorId;
  }

  public void setDoorId(Long doorId) {
    this.doorId = doorId;
  }

  public Door node(GraphNode node) {
    this.node = node;
    return this;
  }

  /**
   * Get node
   * @return node
  */
  @Valid 
  @Schema(name = "node", required = false)
  public GraphNode getNode() {
    return node;
  }

  public void setNode(GraphNode node) {
    this.node = node;
  }

  public Door building(Building building) {
    this.building = building;
    return this;
  }

  /**
   * Get building
   * @return building
  */
  @Valid 
  @Schema(name = "building", required = false)
  public Building getBuilding() {
    return building;
  }

  public void setBuilding(Building building) {
    this.building = building;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Door door = (Door) o;
    return Objects.equals(this.doorId, door.doorId) &&
        Objects.equals(this.node, door.node) &&
        Objects.equals(this.building, door.building);
  }

  @Override
  public int hashCode() {
    return Objects.hash(doorId, node, building);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Door {\n");
    sb.append("    doorId: ").append(toIndentedString(doorId)).append("\n");
    sb.append("    node: ").append(toIndentedString(node)).append("\n");
    sb.append("    building: ").append(toIndentedString(building)).append("\n");
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

