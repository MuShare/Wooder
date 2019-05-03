package org.mushare.wooder.spec.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TextListResponse {
  private long totalCount;
  private List<TextResponse> textResponses;
}
