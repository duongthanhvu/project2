package org.fpoly.nhom2.repository;

import java.util.List;

import org.fpoly.nhom2.entiry.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Nullable
	User findFirstByUsernameOrEmail(String username, String email);

	List<User> findTop10ByUsernameContainingIgnoreCase(String username);

	List<User> findByEmail(String email);

	@Query(value="SELECT COUNT(u) FROM User u WHERE YEAR(u.dateRegistered) = 2018 GROUP BY MONTH(u.dateRegistered) ORDER BY MONTH(u.dateRegistered)")
	List<Long> getUserQuantityPerMonth();
}
