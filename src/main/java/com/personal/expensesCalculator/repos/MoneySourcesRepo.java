package com.personal.expensesCalculator.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal.expensesCalculator.entities.MoneySource;
import com.personal.expensesCalculator.entities.User;

public interface MoneySourcesRepo extends JpaRepository<MoneySource, Integer> {
	List<MoneySource> findAllByUser(User user);
}
