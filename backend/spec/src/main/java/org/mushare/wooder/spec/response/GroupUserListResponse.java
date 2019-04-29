package org.mushare.wooder.spec.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupUserListResponse {
  private long totalCount;
  private List<GroupUserItem> groupUserList;

  @Data
  @Builder
  public static class GroupUserItem {
    private long id;
    private long createTime;
    private long updateTime;
    private String email;
    private String name;
  }
}
