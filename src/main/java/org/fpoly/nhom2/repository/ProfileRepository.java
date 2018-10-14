package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

}
