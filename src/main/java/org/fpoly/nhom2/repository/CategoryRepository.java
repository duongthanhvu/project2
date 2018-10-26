package org.fpoly.nhom2.repository;

import java.util.List;

import org.fpoly.nhom2.entiry.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findTop5ByNameContaining(String name, Pageable pageable);
}
