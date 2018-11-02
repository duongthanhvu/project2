package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByCompany(Company company, Pageable pageable);
}
