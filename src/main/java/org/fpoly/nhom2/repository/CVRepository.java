package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CVRepository extends JpaRepository<Cv, Integer> {

}
