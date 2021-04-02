package com.personal.expensesCalculator.controllers;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.personal.expensesCalculator.auth.UserDetailsImpl;
import com.personal.expensesCalculator.entities.MoneySource;
import com.personal.expensesCalculator.entities.User;
import com.personal.expensesCalculator.exceptions.UnauthorizedException;
import com.personal.expensesCalculator.exceptions.UserNotFoundException;
import com.personal.expensesCalculator.repos.MoneySourcesRepo;
import com.personal.expensesCalculator.repos.UserRepo;

@RestController
public class UserController {
	
	private final UserRepo userRepo;
	private final MoneySourcesRepo moneySourceRepo;
	
	public UserController(UserRepo userRepo, MoneySourcesRepo moneySourceRepo) {
		this.userRepo = userRepo;
		this.moneySourceRepo = moneySourceRepo;
	}
	
	@GetMapping("/users")
	public List<User> getUsers() {
		return userRepo.findAll();
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> addUser(@RequestBody User user) {
		user.setCreatedDate(LocalDateTime.now());
		user.setEnabled(true);
		user.setLocked(false);
		user.setRoles("user");
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userRepo.save(user);
		URI link = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{userName}").buildAndExpand(user.getUserName()).toUri();
		return ResponseEntity.created(link).build();
	}
	
	@GetMapping("/users/{userName}")
	public User getUser(@PathVariable("userName") String userName) {
		User user = userRepo.findByUserName(userName).orElseThrow(() -> new UserNotFoundException("User with user name "+userName+" is not available in database"));
		UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(!userDetails.getUsername().equals(user.getUserName())) {
			throw new UnauthorizedException("Cannot access other user's Details");
		}
		user.setAvailable(user.getMoneySources().stream().filter(src -> src.getSourceType()==0).mapToDouble(MoneySource::getAmount).sum());
		user.setMoneyTo(user.getMoneySources().stream().filter(src -> src.getSourceType()==-1).mapToDouble(MoneySource::getAmount).sum());
		user.setMoneyFrom(user.getMoneySources().stream().filter(src -> src.getSourceType()==1).mapToDouble(MoneySource::getAmount).sum());
		user.setNet(user.getAvailable()+user.getMoneyFrom()-user.getMoneyTo());
		User savedUser = userRepo.save(user);
		return savedUser;
	}
	
	@PostMapping("/users/{userName}/sources")
	public ResponseEntity<Object> addSource(@RequestBody MoneySource moneySource, @PathVariable("userName") String userName) {
		User user = userRepo.findByUserName(userName).orElseThrow(() -> new UserNotFoundException("User with user name '"+userName+"' is not available in database"));
		moneySource.setTimeStamp(LocalDateTime.now());
		moneySource.setUser(user);
		MoneySource savedMoneySource = moneySourceRepo.save(moneySource);
		URI link = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/users/{id}/sources/{sId}").buildAndExpand(userName, savedMoneySource.getId()).toUri();
		return ResponseEntity.created(link).build();
	}
	
	@PutMapping("/users/{userName}/sources/{id}")
	public MoneySource editSource(@RequestBody MoneySource moneySource, @PathVariable("userName") String userName, @PathVariable("id") Integer sourceId) {
		User user = userRepo.findByUserName(userName).orElseThrow(() -> new UserNotFoundException("User with user name '"+userName+"' is not available in database"));
		moneySource.setId(sourceId);
		moneySource.setUser(user);
		MoneySource savedMoneySource = moneySourceRepo.save(moneySource);
		return savedMoneySource;
	}
	
	@DeleteMapping("/users/{userName}/sources/{id}")
	public ResponseEntity<Object> deleteSource(@PathVariable("userName") String userName, @PathVariable("id") Integer sourceId) {
		moneySourceRepo.deleteById(sourceId);
		return ResponseEntity.ok().build();
	}

}
