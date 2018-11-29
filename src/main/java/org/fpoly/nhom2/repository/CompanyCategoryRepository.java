package org.fpoly.nhom2.repository;

import java.util.List;

import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.CompanyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CompanyCategoryRepository
 */
public interface CompanyCategoryRepository extends JpaRepository<CompanyCategory,Integer>{

    List<CompanyCategory> findByCompany(Company company);
}