package com.greatlearning.EmpREST.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greatlearning.EmpREST.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

	  
	  List<Employee> findByEmail(String email);
	List<Employee> findByFirstNameContaining(String firstName);
	

}
