package org.fpoly.nhom2.repository;

import java.util.List;

import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    Page<Job> findByCompany(Company company, Pageable pageable);
    Job findByUrlTitle(String urlTitle);
    @Query(value="SELECT j FROM Job j LEFT JOIN j.tagOfJobs toj JOIN j.provinceOrCity p WHERE (?1 is null OR p.pocId = ?1) AND (?2 is null OR toj.tag.tagId = ?2) AND j.title LIKE CONCAT('%',?3,'%') GROUP BY j.jobId")
    Page<Job> searchJob(Integer locationId, Integer tagId, String keyword, Pageable pageable);

    @Query(value="SELECT COUNT(j) FROM Job j WHERE YEAR(j.dateCreated) = 2018 GROUP BY MONTH(j.dateCreated) ORDER BY MONTH(j.dateCreated)")
	List<Long> getJobQuantityPerMonth();
}
