package org.mushare.wooder.server.repository;

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
public class GroupAuthorityDto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false, name = "user_id")
  private UserDto userDto;

  @ManyToOne
  @JoinColumn(nullable = false, name = "group_id")
  private GroupDto groupDto;

  @ManyToOne
  @JoinColumn(nullable = false, name = "authority_id")
  private AuthorityDto authorityDto;

}
