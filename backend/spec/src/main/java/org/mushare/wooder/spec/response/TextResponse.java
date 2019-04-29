package org.mushare.wooder.spec.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TextResponse {

  private long id;
  private String identifier;
  private String name;
  private List<String> platforms;
  private List<TextContent> textContents;

  @Data
  @Builder
  public static class TextContent {

    private String string;
    private LanguageInfoResponse languageInfoResponse;
    private long id;
    private long createTime;
    private long updateTime;
  }
}
