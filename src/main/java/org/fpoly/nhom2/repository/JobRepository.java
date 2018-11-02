package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    Page<Job> findByCompany(Company company, Pageable pageable);
}
