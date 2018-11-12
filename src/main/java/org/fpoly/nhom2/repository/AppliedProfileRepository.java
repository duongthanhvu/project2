package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.AppliedProfile;
import org.fpoly.nhom2.entiry.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AppliedProfileRepository
 */
public interface AppliedProfileRepository extends JpaRepository<AppliedProfile, Integer> {

    Page<AppliedProfile> findByJob(Job job, Pageable pageable);
}