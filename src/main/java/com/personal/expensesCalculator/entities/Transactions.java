package com.personal.expensesCalculator.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Transactions {

	@Id
	@GeneratedValue
	private Integer id;
	private Double amount;
	private boolean debt;
	private String title;
	private String description;
	private LocalDateTime dateStamp;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
}
