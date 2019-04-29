package org.mushare.wooder.spec.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProjectInfoListResponse {
  private long totalCount;
  private List<ProjectInfoResponse> projectInfoResponses;
}
