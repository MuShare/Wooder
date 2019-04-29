package org.mushare.wooder.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDto, Long> {

  UserDto findByUsername(String username);

  boolean existsByUsername(String username);

  Page<UserDto> findByGroupDtoId(long groupId, Pageable pageable);
}
