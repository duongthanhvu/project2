package org.fpoly.nhom2.repository;

import org.fpoly.nhom2.entiry.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Nullable
	User findFirstByUsernameOrEmail(String username, String email);
}
