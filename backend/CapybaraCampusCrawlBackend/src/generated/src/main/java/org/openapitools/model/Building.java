package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.model.GraphNode;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Building
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-09T11:18:48.083513-06:00[America/Chicago]")
public class Building   {

  @JsonProperty("buildingId")
  private Long buildingId;

  @JsonProperty("name")
  private String name;

  @JsonProperty("geojson")
  private String geojson;

  @JsonProperty("graphNode")
  private GraphNode graphNode;

  @JsonProperty("nodeID")
  private Long nodeID;

  public Building buildingId(Long buildingId) {
    this.buildingId = buildingId;
    return this;
  }

  /**
   * Get buildingId
   * @return buildingId
  */
  
  @Schema(name = "buildingId", required = false)
  public Long getBuildingId() {
    return buildingId;
  }

  public void setBuildingId(Long buildingId) {
    this.buildingId = buildingId;
  }

  public Building name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  
  @Schema(name = "name", required = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Building geojson(String geojson) {
    this.geojson = geojson;
    return this;
  }

  /**
   * Get geojson
   * @return geojson
  */
  
  @Schema(name = "geojson", required = false)
  public String getGeojson() {
    return geojson;
  }

  public void setGeojson(String geojson) {
    this.geojson = geojson;
  }

  public Building graphNode(GraphNode graphNode) {
    this.graphNode = graphNode;
    return this;
  }

  /**
   * Get graphNode
   * @return graphNode
  */
  @Valid 
  @Schema(name = "graphNode", required = false)
  public GraphNode getGraphNode() {
    return graphNode;
  }

  public void setGraphNode(GraphNode graphNode) {
    this.graphNode = graphNode;
  }

  public Building nodeID(Long nodeID) {
    this.nodeID = nodeID;
    return this;
  }

  /**
   * Get nodeID
   * @return nodeID
  */
  
  @Schema(name = "nodeID", required = false)
  public Long getNodeID() {
    return nodeID;
  }

  public void setNodeID(Long nodeID) {
    this.nodeID = nodeID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Building building = (Building) o;
    return Objects.equals(this.buildingId, building.buildingId) &&
        Objects.equals(this.name, building.name) &&
        Objects.equals(this.geojson, building.geojson) &&
        Objects.equals(this.graphNode, building.graphNode) &&
        Objects.equals(this.nodeID, building.nodeID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(buildingId, name, geojson, graphNode, nodeID);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Building {\n");
    sb.append("    buildingId: ").append(toIndentedString(buildingId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    geojson: ").append(toIndentedString(geojson)).append("\n");
    sb.append("    graphNode: ").append(toIndentedString(graphNode)).append("\n");
    sb.append("    nodeID: ").append(toIndentedString(nodeID)).append("\n");
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

