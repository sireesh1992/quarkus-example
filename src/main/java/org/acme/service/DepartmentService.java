package org.acme.service;

import org.acme.entity.Department;
import org.acme.exception.DepartmentNotFoundException;

public interface DepartmentService {

	Department getDepartmentByName(String name) throws DepartmentNotFoundException;
	
	public void saveDepartment(Department department);


}
