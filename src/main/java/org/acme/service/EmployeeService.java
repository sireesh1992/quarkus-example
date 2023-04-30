package org.acme.service;

import java.util.List;

import org.acme.entity.Employee;
import org.acme.exception.EmployeeNotFoundException;

public interface EmployeeService {

	Employee getEmployeeById(long id) throws EmployeeNotFoundException;
	
	List<Employee> getAllEmployees();
	
	void saveEmployee(Employee employee);
	
}
