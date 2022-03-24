package com.capybara.CapybaraCampusCrawlBackend.Models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * BuildingLocation
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-18T22:02:47.023914Z[Etc/UTC]")
public class BuildingLocation   {

  @JsonProperty("buildingId")
  private BigDecimal buildingId;

  public BuildingLocation buildingId(BigDecimal buildingId) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BuildingLocation buildingLocation = (BuildingLocation) o;
    return Objects.equals(this.buildingId, buildingLocation.buildingId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(buildingId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BuildingLocation {\n");
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

