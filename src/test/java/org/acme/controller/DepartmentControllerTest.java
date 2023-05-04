package org.acme.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import jakarta.ws.rs.core.Response;

import org.acme.controller.DepartmentController.DepartmentResponse;
import org.acme.entity.Department;
import org.acme.controller.DepartmentController.DepartmentDto;
import org.acme.entity.Employee;
import org.acme.exception.DepartmentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;


@QuarkusTest
public class DepartmentControllerTest {
	
	@Inject DepartmentController departmentController;
	
	//This method is executed before each test, setting up the mock behavior
	@BeforeEach
	void setUp() throws DepartmentNotFoundException {
		Department dept = new Department();
		dept.id = 1L;
		dept.setDeptName("Dept 1");
		Employee e = new Employee();
		e.setFirstName("First Name");
		e.setLastName("Last Name");
		e.setAge(20);
		e.setDepartment(dept);
		List<Employee> employees = new ArrayList<>();
		employees.add(e);
		dept.setEmployees(employees);
		
		//Use panache mock to mock Entity classes
		//We did mock of Department.class as we want to mock static methods
        PanacheMock.mock(Department.class);
        //Mock static methods
        Mockito.when(Department.getDepartmentByName("Dept 1")).thenReturn(dept);
		Mockito.when(Department.getDepartmentByName("Dept 2")).thenThrow(new DepartmentNotFoundException("Department doesn't exist"));
	}

	@Test
	void getDepartmentByName_OK() throws DepartmentNotFoundException {
		DepartmentResponse actual = departmentController.getDepartment("Dept 1");
		assertEquals(1, actual.getId());
		assertEquals("Dept 1", actual.getDeptName());
		assertNotNull(actual.getEmployees());
		assertEquals(1, actual.getEmployees().size());
		assertEquals("First Name Last Name", actual.getEmployees().get(0));
	}
	
	@Test
	void getDepartmentByName_ThrowException() {
		assertThrows(Exception.class, () -> departmentController.getDepartment("Dept 2"));
	}
	
	@Test
	void createDepartment_OK() {
		DepartmentDto dto = new DepartmentDto();
		dto.setDeptName("Dept 3");
		Response actual = departmentController.createDepartment(dto);
		assertEquals(Response.Status.CREATED.getStatusCode(), actual.getStatus());
	}
	

}
