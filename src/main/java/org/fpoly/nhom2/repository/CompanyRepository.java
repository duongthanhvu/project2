package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company findFirstByUrlName(String urlName);
}
