package org.acme.repository;

import org.acme.entity.Employee;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {

}
