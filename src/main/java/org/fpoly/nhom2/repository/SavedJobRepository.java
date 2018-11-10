package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.Job;
import org.fpoly.nhom2.entiry.SavedJob;
import org.fpoly.nhom2.entiry.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, Integer> {

    SavedJob findByUserAndJob(User user, Job job);
}
