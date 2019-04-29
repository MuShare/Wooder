package org.mushare.wooder.server.repository;

import java.io.Serializable;
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
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LanguageDto implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long createdAt;

  @Column(nullable = false)
  private Long updatedAt;

  @Column(nullable = false)
  private String identifier;

  @Column(nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(nullable = false, name = "project_id")
  private ProjectDto projectDto;

}
