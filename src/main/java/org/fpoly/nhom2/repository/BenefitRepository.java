package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Integer> {

}
