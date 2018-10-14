package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

}
