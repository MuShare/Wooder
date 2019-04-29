package org.mushare.wooder.server.repository;

import java.util.List;
import java.util.Set;
import org.mushare.wooder.server.security.SecurityConstants.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityDto, Long> {

  Set<AuthorityDto> findByAuthorityIn(List<Authority> authorities);

  boolean existsByAuthority(Authority authority);

}
