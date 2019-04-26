package org.mushare.wooder.dao;

import org.mushare.wooder.domain.Text;
import org.mushare.wooder.domain.TextFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextDao extends JpaRepository<Text, String> {

    List<Text> findByFolderOrderByIdentifer(TextFolder folder);

}
