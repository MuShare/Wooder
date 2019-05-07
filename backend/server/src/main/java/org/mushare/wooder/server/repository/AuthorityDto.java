package org.mushare.wooder.server.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mushare.wooder.server.security.SecurityConstants.Authority;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated
  @Column(unique = true)
  private Authority authority;

}
