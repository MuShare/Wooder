package org.mushare.wooder.spec.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TextFolderListResponse {

  private long totalCount;
  private List<TextFolderListItem> textFolderListItems;

  @Builder
  @Data
  public static class TextFolderListItem {

    private String name;
    private long createTime;
    private long updateTime;
    private ProjectInfoResponse projectInfoResponse;
  }

}
