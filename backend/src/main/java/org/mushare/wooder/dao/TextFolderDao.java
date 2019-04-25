package org.mushare.wooder.dao;

import org.mushare.wooder.domain.Project;
import org.mushare.wooder.domain.TextFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextFolderDao extends JpaRepository<TextFolder, String> {

    List<TextFolder> findByProjectOrderByName(Project project);

}
