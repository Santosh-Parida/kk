package com.project.ems.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.ems.dao.EmployeeRepository;
import com.project.ems.entity.Employee;
import com.project.ems.entity.LoginDetails;
import com.project.ems.exception.EmployeeNotFoundException;




@RestController
public class HomeResource {
	
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	EmployeeRepository repo;

    @GetMapping("/")
    public String home() {
        return ("Welcome to Employee management system");
    }

    @PostMapping("/user")
    public Optional<Employee> user(@Valid @RequestBody LoginDetails loginDetails) throws EmployeeNotFoundException {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDetails.getUsername(), loginDetails.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		Optional<Employee>emp= repo.findByEmail(loginDetails.getUsername());
		emp.orElseThrow (() -> new EmployeeNotFoundException("This employee is not found"));
		return emp;
    }

    @GetMapping("/admin")
    public String admin() {
        return ("Welcome Admin");
    }
}

