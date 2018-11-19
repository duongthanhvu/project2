package org.fpoly.nhom2.repository;

import java.util.List;

import org.fpoly.nhom2.entiry.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    @Query(value = "SELECT p FROM Profile p LEFT JOIN p.address a LEFT JOIN a.commune co LEFT JOIN co.cityOrDist dist LEFT JOIN p.educations e LEFT JOIN p.skillLists sl WHERE (?1 is null OR dist.provinceOrCity.pocId = ?1) AND (?2 LIKE '' OR e.school like CONCAT('%', ?2, '%')) AND (?3 LIKE '' OR p.jobLevel like CONCAT('%', ?3, '%')) AND (?5 = 1 OR sl.skill.skillId IN ?4) GROUP BY p.profileId")
    Page<Profile> searchProfile(Integer locationId, String school, String jobLevel, List<Integer> skills, int isSkillEmpty,
            Pageable pageable);

    Profile findByUrlName(String urlName);
}
