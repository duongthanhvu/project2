package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

}
