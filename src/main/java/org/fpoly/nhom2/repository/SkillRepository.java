package org.fpoly.nhom2.repository;

import java.util.List;

import org.fpoly.nhom2.entiry.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    List<Skill> findByTitleContainingIgnoreCase(String title);

    Skill findFirstByTitleIgnoreCase(String title);
}
