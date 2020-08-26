package com.project.ems.controller;


import java.util.List;
import java.util.Optional;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.project.ems.dao.UserRepository;

import com.project.ems.entity.User;

import com.project.ems.exception.IdNotFoundException;
import com.project.ems.exception.RoleException;
import com.project.ems.exception.UserNotFoundException;





@RestController
public class UserController {
	
	@Autowired
	UserRepository repo;
	
	@GetMapping("/getAllUsers") 
	@PreAuthorize(" hasRole('ROLE_ADMIN')")
	public List<User> getUsers(@RequestParam("page_no") Integer page_no, @RequestParam("page_size") Integer page_size)
	{
		Pageable paging = PageRequest.of(page_no, page_size);
		Page<User> res = repo.findAll(paging);
		if(res.hasContent())
			return res.getContent();
		else
			throw new UserNotFoundException("User does not exist in this page");
	}
	@GetMapping("/getUsersById/{eid}")
	@PreAuthorize(" hasRole('ROLE_ADMIN')")
	public  Optional<User> getUsers( @PathVariable("eid") int eid)
	{
		Optional<User>emp= repo.findById(eid);
		emp.orElseThrow (() -> new UserNotFoundException("User id:"+eid+" not found"));
		return emp;
	}
	
	@PostMapping("/saveUser")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String addUser(@Valid @RequestBody User e) {
		String role = e.getRoles();
		if (role.equals("ROLE_USER") || role.equals("ROLE_ADMIN")) {
			repo.save(e);
			return "Successfully Added";
		} else
			throw new RoleException("Role must be 'ROLE_USER' or 'ROLE_ADMIN'");

	}
	
	@DeleteMapping("/removeUser/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String removeUser( @PathVariable("id") int id) throws UserNotFoundException
	{
		Optional<User> e = repo.findById(id);
		if(e.isPresent())
		{
			repo.deleteById(id);		
			return "deleted successfully";
		}
		else {
			throw new UserNotFoundException("User id:"+id+" not found");
		}
	}
	
	
	@PutMapping("/updateUser")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String updateUser(@Valid @RequestBody User e)
	{
		Integer id=e.getId();
		if(id!=null) {
		Optional<User>user=repo.findById(id);
		if(user.isPresent()) {
		String role=e.getRoles();
		if(role.equals("ROLE_USER")||role.equals("ROLE_ADMIN"))
		{
			repo.save(e);
			 return "Successfully updated";
		}
		else {
			throw new RoleException("Role must be 'ROLE_USER' or 'ROLE_ADMIN'");}
} 
else {
		throw new UserNotFoundException("User with this id:"+id+" is not present");}}
		else
			throw new IdNotFoundException("Please enter id!");
	}
	}
	
	
	


