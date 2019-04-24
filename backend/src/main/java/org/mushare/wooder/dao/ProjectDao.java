package org.mushare.wooder.dao;

import org.mushare.wooder.domain.Group;
import org.mushare.wooder.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDao extends JpaRepository<Project, String> {

    List<Project> findByGroupOrderByCreatedAt(Group group);

}
