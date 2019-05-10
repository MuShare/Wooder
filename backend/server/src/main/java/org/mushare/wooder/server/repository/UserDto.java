package org.mushare.wooder.server.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_authority",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "authority_id")}
  )
  Set<AuthorityDto> authorities;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private Long createTime;

  @Column(nullable = false)
  private Long updateTime;

  @Column(nullable = false)
  private String email;

  @ManyToOne
  @JoinColumn(name = "group_id")
  private GroupDto groupDto;

  @JsonIgnore
  public List<SimpleGrantedAuthority> getAuthorities() {
    return authorities.stream().map(authorityDto ->
        new SimpleGrantedAuthority(authorityDto.getAuthority().getAuthority()))
        .collect(Collectors.toList());
  }
}
