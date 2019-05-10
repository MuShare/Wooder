package org.mushare.wooder.server.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextContentRepository extends JpaRepository<TextContentDto, Long> {

  List<TextContentDto> findByTextIdOrderByLanguageDto(long textId);
}
