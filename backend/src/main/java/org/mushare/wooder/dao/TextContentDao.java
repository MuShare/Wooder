package org.mushare.wooder.dao;

import org.mushare.wooder.domain.TextContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextContentDao extends JpaRepository<TextContent, String> {
}
