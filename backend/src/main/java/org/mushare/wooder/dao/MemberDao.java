package org.mushare.wooder.dao;

import org.mushare.wooder.domain.Group;
import org.mushare.wooder.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberDao extends JpaRepository<Member, String> {

    Member getByEmail(String email);

    List<Member> findByGroupOrderByCreatedAt(Group group);

}
