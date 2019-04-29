package org.mushare.wooder.spec.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GroupInfoResponse {

  private String name;
  private long id;
  private String description;
  private long createdTime;
  private long updatedTime;
  private long createdUserId;
  private String createdUsername;
}
