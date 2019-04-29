package org.mushare.wooder.spec.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LanguageInfoResponse {

  private long id;
  private String identifier;
  private String name;
  private long createTime;
  private long updateTime;
}
