package org.acme.repository;

import java.util.Optional;

import org.acme.entity.Department;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class DepartmentRepository implements PanacheRepository<Department> {

	public Optional<Department> findByDeptName(String name) {
	    return find("upper(deptName)", name.toUpperCase().trim())
	            .firstResultOptional();
	}
	
}