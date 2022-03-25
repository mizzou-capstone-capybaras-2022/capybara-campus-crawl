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
 * TimeConstraint
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-18T22:02:47.023914Z[Etc/UTC]")
public class TimeConstraint   {

  @JsonProperty("maxTime")
  private Double maxTime;

  public TimeConstraint maxTime(Double maxTime) {
    this.maxTime = maxTime;
    return this;
  }

  /**
   * Get maxTime
   * @return maxTime
  */
  @NotNull 
  @Schema(name = "maxTime", required = true)
  public Double getMaxTime() {
    return maxTime;
  }

  public void setMaxTime(Double maxTime) {
    this.maxTime = maxTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimeConstraint timeConstraint = (TimeConstraint) o;
    return Objects.equals(this.maxTime, timeConstraint.maxTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TimeConstraint {\n");
    sb.append("    maxTime: ").append(toIndentedString(maxTime)).append("\n");
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

