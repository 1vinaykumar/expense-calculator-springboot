package com.personal.expensesCalculator.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal.expensesCalculator.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String userName);
}
