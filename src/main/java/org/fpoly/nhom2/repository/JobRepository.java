package org.fpoly.nhom2.repository;

import java.util.List;

import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.Job;
import org.fpoly.nhom2.entiry.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    Page<Job> findByCompany(Company company, Pageable pageable);

    Job findByUrlTitle(String urlTitle);

    @Query(value = "SELECT j FROM Job j LEFT JOIN j.tagOfJobs toj JOIN j.provinceOrCity p WHERE (?1 is null OR p.pocId = ?1) AND (?4 = 1 OR toj.tag.tagId IN ?2) AND j.title LIKE CONCAT('%',?3,'%') GROUP BY j.jobId")
    Page<Job> searchJob(Integer locationId, List<Integer> tagIds, String keyword, Pageable pageable, int isTagIdEmpty);

    @Query(value = "SELECT COUNT(j) FROM Job j WHERE YEAR(j.dateCreated) = 2018 GROUP BY MONTH(j.dateCreated) ORDER BY MONTH(j.dateCreated)")
    List<Long> getJobQuantityPerMonth();

    @Query(value = "SELECT COUNT (j) FROM Job j WHERE j.dateCreated > CURRENT_DATE")
    Long numberOfJobsCreatedToday();

    @Query(value = "SELECT j FROM Job j LEFT JOIN j.tagOfJobs toj WHERE toj.tag IN ?1 GROUP BY j.jobId")
    Page<Job> findSimilarJob(List<Tag> tags, Pageable pageable);

    @Query(value = "SELECT j FROM Job j WHERE j.company IN ?1")
    Page<Job> findJobFromFollowedC(List<Company> companies, int quantity, Pageable pageable);
}
