package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    Page<Report> findByStatus(boolean status, Pageable pageable);

    long countByStatus(boolean status);
}
