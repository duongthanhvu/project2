package org.fpoly.nhom2.repository;


import java.util.List;

import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findByCompany(Company company, Pageable pageable);
    List<Post> findTop10ByCompany(Company company);
    Post findByUrlTitle(String urlTitle);

    @Query(value="SELECT p FROM Post p WHERE p.company IN ?1")
    Page<Post> findPostFromFollowedC(List<Company> companies, int quantity, Pageable pageable);
}
