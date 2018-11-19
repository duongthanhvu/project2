package org.fpoly.nhom2.repository;

import java.util.List;

import org.fpoly.nhom2.entiry.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company findFirstByUrlName(String urlName);

    List<Company> findTop10ByNameContainingIgnoreCase(String name);

    @Query(value = "SELECT c FROM Company c JOIN c.address a JOIN a.commune co JOIN co.cityOrDist dist LEFT JOIN c.companyCategories cc WHERE (?1 is null OR cc.category.categoryId = ?1) AND (?2 is null OR dist.provinceOrCity.pocId = ?2) AND (c.name LIKE CONCAT('%', ?3, '%') OR c.description LIKE CONCAT('%', ?4, '%')) GROUP BY c.companyId")
    Page<Company> searchCompany(Integer categoryId, Integer locationId, String name, String description, Pageable pageable);

    @Query(value="SELECT COUNT(c) FROM Company c WHERE YEAR(c.dateAdded) = 2018 GROUP BY MONTH(c.dateAdded) ORDER BY MONTH(c.dateAdded)")
    List<Long> getCompanyQuantityPerMonth();
    
    List<Company> findTop10ByOrderByDateAddedDesc();

    @Query(value="SELECT c FROM Company c JOIN c.viewCount vc ORDER BY vc.count DESC")
    Page<Company> getMostFollowedCompanies(Pageable pageable);

    @Query(value="SELECT DISTINCT c FROM Company c JOIN c.jobs j WHERE j.dateExpired > CURRENT_DATE")
    Page<Company> getHiringCompanies(Pageable pageable);
}
