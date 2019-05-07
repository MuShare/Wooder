package org.mushare.wooder.spec.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
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
