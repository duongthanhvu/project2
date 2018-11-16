package org.fpoly.nhom2.service;

import java.util.ArrayList;
import java.util.List;

import org.fpoly.nhom2.entiry.CompanyAdmin;
import org.fpoly.nhom2.entiry.User;
import org.fpoly.nhom2.repository.CompanyAdminRepository;
import org.fpoly.nhom2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CompanyAdminRepository caRepository;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findFirstByUsernameOrEmail(username, username);
		if(user == null) {
			throw new UsernameNotFoundException("user that has username or email " + username + " was not found on the db");
		}
		System.out.println("found user with username/email " + username);
		List<CompanyAdmin> ca = caRepository.findByUser(user);
		List<GrantedAuthority> grantList = new ArrayList<>();
		grantList.add(new SimpleGrantedAuthority("ROLE_"+user.getRole().getName()));
		UserDetails userDetail = new CustomUser(user.getUsername(), user.getPassword(), grantList, user.isEnabled() ,user,ca);
		return userDetail;
	}
}
