package org.acme.service.impl;

import org.acme.entity.Department;
import org.acme.exception.DepartmentNotFoundException;
import org.acme.repository.DepartmentRepository;
import org.acme.service.DepartmentService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepository departmentRepository;
	
	@Inject
	public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@Override
	public Department getDepartmentByName(String name) throws DepartmentNotFoundException {
		return departmentRepository.findByDeptName(name).orElseThrow(() -> new DepartmentNotFoundException("Department doesn't exist"));
	}

	@Override
	@Transactional
	public void saveDepartment(Department department) {
		departmentRepository.persistAndFlush(department);
	}

	
	
}
