package org.mushare.wooder.spec.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProjectRequest {

  private long groupId;
  private String description;
  private String name;
}
