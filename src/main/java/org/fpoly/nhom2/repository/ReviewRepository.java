package org.fpoly.nhom2.repository;

import java.util.List;

import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByCompany(Company company);
}
