package com.personal.expensesCalculator.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.personal.expensesCalculator.repos.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepo userRepo;

	public UserDetailsServiceImpl(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new UserDetailsImpl(userRepo.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User with user name "+username+" is not available in database")));
	}

}
