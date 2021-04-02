package com.personal.expensesCalculator.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal.expensesCalculator.entities.Transactions;
import com.personal.expensesCalculator.entities.User;

public interface TransactionsRepo extends JpaRepository<Transactions, Integer> {
	List<Transactions> findAllByUser(User user);
}
