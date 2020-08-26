package com.project.ems.controller;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.ems.dao.EmployeeRepository;
import com.project.ems.entity.Employee;
import com.project.ems.exception.EmployeeNotFoundException;
import com.project.ems.exception.IdNotFoundException;
import com.project.ems.exception.SortOrderException;



@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeRepository repo;
	
	
	@GetMapping("/getAllEmployees") 
	@PreAuthorize(" hasRole('ROLE_ADMIN')")
	public List<Employee> getEmployees( @RequestParam("page_no") Integer page_no, @RequestParam("page_size") Integer page_size) throws EmployeeNotFoundException
	{
			Pageable paging = PageRequest.of(page_no, page_size);
			Page<Employee> res = repo.findAll(paging);
			
			if(res.hasContent())
				return res.getContent();
			else
				throw new EmployeeNotFoundException("Employee does not exist in this page");
	}
	
	
	@GetMapping("/getEmployeeById/{eid}")
	@PreAuthorize(" hasRole('ROLE_ADMIN')")
	public  Optional<Employee> getEmployee(  @PathVariable("eid") Integer eid) throws EmployeeNotFoundException
	{
		Optional<Employee>emp= repo.findById(eid);
		emp.orElseThrow (() -> new EmployeeNotFoundException("Employee of this id:"+eid+" is not present in database" ));
		return emp;
	}
	
	@PostMapping("/saveEmployee")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String addEmployee(@Valid @RequestBody Employee e) 
	{
		 repo.save(e);
		 return "Successfull added";
	}
	
	
	@DeleteMapping("/removeEmployee/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String removeEmployee( @PathVariable("id") int id) throws EmployeeNotFoundException
	{
		Optional <Employee> e = repo.findById(id);
		if(e.isPresent())
		{
			repo.deleteById(id);		
			return "deleted successfully";
		}
		else {
			throw new EmployeeNotFoundException("Employee of this id:"+id+" does not exist in database");
		}
	}
	
	@PutMapping("/updateEmployee")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String updateEmployee( @Valid @RequestBody Employee e) throws EmployeeNotFoundException
	{
		Integer id=e.getId();
		if(id!=null) 
		{
			Optional<Employee>emp=repo.findById(id);
			if(emp.isPresent()) 
			{
				repo.save(e);
				return "Successfully updated";
			}
			else
				throw new EmployeeNotFoundException("Employee of this id:"+id+" does not exist in database");
		}
		else 
			throw new IdNotFoundException("Please enter id!");
	}
	
	@GetMapping("/sortBy/{col}/{order}")
	//@GetMapping("/sortBy")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Employee> sort(@PathVariable("col") String col,@PathVariable("order") String order,@RequestParam("page_no") Integer page_no, @RequestParam("page_size") Integer page_size) throws EmployeeNotFoundException, SortOrderException
	//public List<Employee> sort(@RequestParam("col") String col,@RequestParam("order") String order,@RequestParam("page_no") Integer page_no, @RequestParam("page_size") Integer page_size) throws EmployeeNotFoundException, SortOrderException
	{
		
		Pageable paging ;
		if(order.equals("ascending")||order.equals("descending"))
		{
			if(order.equals("ascending"))
				 paging = PageRequest.of(page_no, page_size, Sort.by(col).ascending()); 
			else
				 paging = PageRequest.of(page_no, page_size, Sort.by(col).descending()); 
			
			Page<Employee> pagedResult = repo.findAll(paging); 
			if(pagedResult.hasContent())
				return pagedResult.getContent();
			else
				throw new EmployeeNotFoundException("Employee doesnot  exist in this page!!");
		}
		else
			throw new SortOrderException("Enter a valid sorting Order");
	}
	
	
	
	

}
