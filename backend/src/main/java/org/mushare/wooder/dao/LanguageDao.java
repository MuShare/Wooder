package org.mushare.wooder.dao;

import org.mushare.wooder.domain.Language;
import org.mushare.wooder.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageDao extends JpaRepository<Language, String> {

    List<Language> findByProjectOrderByName(Project project);

}
