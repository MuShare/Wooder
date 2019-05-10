package org.mushare.wooder.server.repository;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TextDto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // TODO: Use the same style like 'createAt'.
  @Column(nullable = false)
  private Long createTime;

  // TODO: Use the same style like 'createAt'.
  @Column(nullable = false)
  private Long updateTime;

  @Column(nullable = false)
  private String identifier;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Boolean android;

  @Column(nullable = false)
  private Boolean ios;

  @ManyToOne
  @JoinColumn(nullable = false, name = "text_folder_id")
  private TextFolderDto textFolderDto;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TextDto text = (TextDto) o;
    return Objects.equals(id, text.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
