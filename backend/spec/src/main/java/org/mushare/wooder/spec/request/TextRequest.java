package org.mushare.wooder.spec.request;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TextRequest {

  private long id;
  private String identifier;
  private String name;
  private List<String> platforms;
  private List<TextContentRequest> textContentRequests;

  @Builder
  @Data
  public static class TextContentRequest {

    private long id;
    private String string;
  }
}
