package com.personal.expensesCalculator.entities;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(unique = true, nullable = false)
	private String userName;
	@Column(nullable = false)
	private String password;
	private String roles;
	private boolean isEnabled;
	private boolean isLocked;
	private LocalDateTime createdDate;
	private String name;
	private double available;
	private double moneyTo;
	private double moneyFrom;
	private double net;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<MoneySource> moneySources;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<Transactions> transactions;
	
	
}
