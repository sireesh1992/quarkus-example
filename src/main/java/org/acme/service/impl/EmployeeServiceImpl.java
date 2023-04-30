package org.acme.service.impl;

import java.util.List;

import org.acme.entity.Employee;
import org.acme.exception.EmployeeNotFoundException;
import org.acme.repository.EmployeeRepository;
import org.acme.service.EmployeeService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;
	
	@Inject
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee getEmployeeById(long id) throws EmployeeNotFoundException {
		return employeeRepository.findByIdOptional(id).orElseThrow(() -> new EmployeeNotFoundException("Employee doesn't exist"));
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.listAll();
	}

	@Transactional
	@Override
	public void saveEmployee(Employee employee) {
		employeeRepository.persistAndFlush(employee);
	}

	
}
