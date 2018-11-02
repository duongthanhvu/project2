package org.fpoly.nhom2.repository;

import java.util.List;

import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.CompanyAdmin;
import org.fpoly.nhom2.entiry.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CompanyAdminRepository
 */
public interface CompanyAdminRepository extends JpaRepository<CompanyAdmin,Integer>{

    boolean existsByCompanyAndUser(Company company, User user);
    List<CompanyAdmin> findByUser(User user);
}