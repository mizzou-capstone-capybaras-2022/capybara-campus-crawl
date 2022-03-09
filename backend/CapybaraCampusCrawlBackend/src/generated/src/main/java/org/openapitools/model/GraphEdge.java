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
 * GraphEdge
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-09T11:18:48.083513-06:00[America/Chicago]")
public class GraphEdge   {

  @JsonProperty("edgeId")
  private Long edgeId;

  @JsonProperty("fromNode")
  private GraphNode fromNode;

  @JsonProperty("toNode")
  private GraphNode toNode;

  @JsonProperty("fromToAction")
  private String fromToAction;

  @JsonProperty("toFromAction")
  private String toFromAction;

  @JsonProperty("distance")
  private Double distance;

  @JsonProperty("pathshape")
  private String pathshape;

  @JsonProperty("bidirectional")
  private Boolean bidirectional;

  public GraphEdge edgeId(Long edgeId) {
    this.edgeId = edgeId;
    return this;
  }

  /**
   * Get edgeId
   * @return edgeId
  */
  
  @Schema(name = "edgeId", required = false)
  public Long getEdgeId() {
    return edgeId;
  }

  public void setEdgeId(Long edgeId) {
    this.edgeId = edgeId;
  }

  public GraphEdge fromNode(GraphNode fromNode) {
    this.fromNode = fromNode;
    return this;
  }

  /**
   * Get fromNode
   * @return fromNode
  */
  @Valid 
  @Schema(name = "fromNode", required = false)
  public GraphNode getFromNode() {
    return fromNode;
  }

  public void setFromNode(GraphNode fromNode) {
    this.fromNode = fromNode;
  }

  public GraphEdge toNode(GraphNode toNode) {
    this.toNode = toNode;
    return this;
  }

  /**
   * Get toNode
   * @return toNode
  */
  @Valid 
  @Schema(name = "toNode", required = false)
  public GraphNode getToNode() {
    return toNode;
  }

  public void setToNode(GraphNode toNode) {
    this.toNode = toNode;
  }

  public GraphEdge fromToAction(String fromToAction) {
    this.fromToAction = fromToAction;
    return this;
  }

  /**
   * Get fromToAction
   * @return fromToAction
  */
  
  @Schema(name = "fromToAction", required = false)
  public String getFromToAction() {
    return fromToAction;
  }

  public void setFromToAction(String fromToAction) {
    this.fromToAction = fromToAction;
  }

  public GraphEdge toFromAction(String toFromAction) {
    this.toFromAction = toFromAction;
    return this;
  }

  /**
   * Get toFromAction
   * @return toFromAction
  */
  
  @Schema(name = "toFromAction", required = false)
  public String getToFromAction() {
    return toFromAction;
  }

  public void setToFromAction(String toFromAction) {
    this.toFromAction = toFromAction;
  }

  public GraphEdge distance(Double distance) {
    this.distance = distance;
    return this;
  }

  /**
   * Get distance
   * @return distance
  */
  
  @Schema(name = "distance", required = false)
  public Double getDistance() {
    return distance;
  }

  public void setDistance(Double distance) {
    this.distance = distance;
  }

  public GraphEdge pathshape(String pathshape) {
    this.pathshape = pathshape;
    return this;
  }

  /**
   * Get pathshape
   * @return pathshape
  */
  
  @Schema(name = "pathshape", required = false)
  public String getPathshape() {
    return pathshape;
  }

  public void setPathshape(String pathshape) {
    this.pathshape = pathshape;
  }

  public GraphEdge bidirectional(Boolean bidirectional) {
    this.bidirectional = bidirectional;
    return this;
  }

  /**
   * Get bidirectional
   * @return bidirectional
  */
  
  @Schema(name = "bidirectional", required = false)
  public Boolean getBidirectional() {
    return bidirectional;
  }

  public void setBidirectional(Boolean bidirectional) {
    this.bidirectional = bidirectional;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GraphEdge graphEdge = (GraphEdge) o;
    return Objects.equals(this.edgeId, graphEdge.edgeId) &&
        Objects.equals(this.fromNode, graphEdge.fromNode) &&
        Objects.equals(this.toNode, graphEdge.toNode) &&
        Objects.equals(this.fromToAction, graphEdge.fromToAction) &&
        Objects.equals(this.toFromAction, graphEdge.toFromAction) &&
        Objects.equals(this.distance, graphEdge.distance) &&
        Objects.equals(this.pathshape, graphEdge.pathshape) &&
        Objects.equals(this.bidirectional, graphEdge.bidirectional);
  }

  @Override
  public int hashCode() {
    return Objects.hash(edgeId, fromNode, toNode, fromToAction, toFromAction, distance, pathshape, bidirectional);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GraphEdge {\n");
    sb.append("    edgeId: ").append(toIndentedString(edgeId)).append("\n");
    sb.append("    fromNode: ").append(toIndentedString(fromNode)).append("\n");
    sb.append("    toNode: ").append(toIndentedString(toNode)).append("\n");
    sb.append("    fromToAction: ").append(toIndentedString(fromToAction)).append("\n");
    sb.append("    toFromAction: ").append(toIndentedString(toFromAction)).append("\n");
    sb.append("    distance: ").append(toIndentedString(distance)).append("\n");
    sb.append("    pathshape: ").append(toIndentedString(pathshape)).append("\n");
    sb.append("    bidirectional: ").append(toIndentedString(bidirectional)).append("\n");
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

