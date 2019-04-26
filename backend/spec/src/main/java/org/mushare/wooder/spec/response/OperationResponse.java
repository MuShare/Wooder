package org.mushare.wooder.spec.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationResponse {

  private String message;
  private boolean succeed;
  @JsonInclude(Include.NON_NULL)
  private Object object;
}
