package com.capybara.CapybaraCampusCrawlBackend.Models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * RouteErrorResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-05-03T22:02:07.033940Z[Etc/UTC]")
public class RouteErrorResponse   {

  /**
   * Gets or Sets failingConstraint
   */
  public enum FailingConstraintEnum {
    FOOD("food"),
    
    PITSTOP("pitstop"),
    
    TIME("time"),
    
    CROWDS("crowds"),
    
    ROUTE("route");

    private String value;

    FailingConstraintEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static FailingConstraintEnum fromValue(String value) {
      for (FailingConstraintEnum b : FailingConstraintEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("failingConstraint")
  @Valid
  private List<FailingConstraintEnum> failingConstraint = null;

  public RouteErrorResponse failingConstraint(List<FailingConstraintEnum> failingConstraint) {
    this.failingConstraint = failingConstraint;
    return this;
  }

  public RouteErrorResponse addFailingConstraintItem(FailingConstraintEnum failingConstraintItem) {
    if (this.failingConstraint == null) {
      this.failingConstraint = new ArrayList<>();
    }
    this.failingConstraint.add(failingConstraintItem);
    return this;
  }

  /**
   * Get failingConstraint
   * @return failingConstraint
  */
  
  @Schema(name = "failingConstraint", required = false)
  public List<FailingConstraintEnum> getFailingConstraint() {
    return failingConstraint;
  }

  public void setFailingConstraint(List<FailingConstraintEnum> failingConstraint) {
    this.failingConstraint = failingConstraint;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RouteErrorResponse routeErrorResponse = (RouteErrorResponse) o;
    return Objects.equals(this.failingConstraint, routeErrorResponse.failingConstraint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(failingConstraint);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RouteErrorResponse {\n");
    sb.append("    failingConstraint: ").append(toIndentedString(failingConstraint)).append("\n");
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

