package org.mushare.wooder.spec.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {

  List<UserItem> users;

  @Data
  @Builder
  public static class UserItem {

    private String username;
    private long createTime;
    private long updateTime;
    private String email;
    private String groupName;
  }
}
