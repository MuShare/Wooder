package org.mushare.wooder.dao;

import org.mushare.wooder.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupDao extends JpaRepository<Group, String> {

    Group getByEmail(String email);
}
