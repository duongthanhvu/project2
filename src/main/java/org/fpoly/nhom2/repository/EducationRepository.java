package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {

}
