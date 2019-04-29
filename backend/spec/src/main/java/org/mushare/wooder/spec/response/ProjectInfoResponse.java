package org.mushare.wooder.spec.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProjectInfoResponse {
  private String name;
  private long id;
  private long createTime;
  private String description;
  private long createdByUserId;
  private String createdByUserName;
  private long groupId;
  private String groupName;
}
