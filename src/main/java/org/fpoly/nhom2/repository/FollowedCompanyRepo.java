package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.FollowedCompany;
import org.fpoly.nhom2.entiry.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

/**
 * FollowedCompanyRepo
 */
public interface FollowedCompanyRepo extends JpaRepository<FollowedCompany, Integer>{

    @Nullable
    FollowedCompany findByUserAndCompany(User user, Company company);
}