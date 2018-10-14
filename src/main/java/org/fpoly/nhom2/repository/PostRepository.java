package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

}
