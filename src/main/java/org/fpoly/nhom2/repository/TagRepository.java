package org.fpoly.nhom2.repository;

import java.util.List;

import org.fpoly.nhom2.entiry.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    List<Tag> findByTitleContainingIgnoreCase(String title);
}
