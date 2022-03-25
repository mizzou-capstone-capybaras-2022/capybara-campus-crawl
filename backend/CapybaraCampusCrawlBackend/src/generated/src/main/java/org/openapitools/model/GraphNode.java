package org.openapitools.model;

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
 * GraphNode
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-09T11:18:48.083513-06:00[America/Chicago]")
public class GraphNode   {

  @JsonProperty("latitude")
  private Double latitude;

  @JsonProperty("longitude")
  private Double longitude;

  @JsonProperty("description")
  private String description;

  @JsonProperty("nodeID")
  private Long nodeID;

  public GraphNode latitude(Double latitude) {
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

  public GraphNode longitude(Double longitude) {
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

  public GraphNode description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", required = false)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public GraphNode nodeID(Long nodeID) {
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
    GraphNode graphNode = (GraphNode) o;
    return Objects.equals(this.latitude, graphNode.latitude) &&
        Objects.equals(this.longitude, graphNode.longitude) &&
        Objects.equals(this.description, graphNode.description) &&
        Objects.equals(this.nodeID, graphNode.nodeID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(latitude, longitude, description, nodeID);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GraphNode {\n");
    sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
    sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

