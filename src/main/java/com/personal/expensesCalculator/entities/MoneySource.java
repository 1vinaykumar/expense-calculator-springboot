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
public class MoneySource {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String description;
	private double amount;
	private LocalDateTime timeStamp;
	private byte sourceType;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
}
