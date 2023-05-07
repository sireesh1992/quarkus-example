package testemployee;

import org.acme.controller.DepartmentController;
import org.acme.controller.DepartmentController.DepartmentDto;
import org.acme.controller.DepartmentController.DepartmentResponse;
import org.acme.entity.Department;
import org.acme.entity.Employee;
import org.acme.exception.DepartmentNotFoundException;
import org.acme.repository.DepartmentRepository;
import org.acme.service.DepartmentService;
import org.acme.service.EmployeeService;
import org.mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.*;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;

@QuarkusTest
public class departmentTest {
	
	@InjectMock DepartmentService departmentService;
	@InjectMock EmployeeService employeeService;
	@Inject DepartmentController departmentController;
	
	private Employee emp;
	private Department dept;
	private DepartmentDto deptDto;
	
	
	@BeforeEach
	void setup() {
		emp = new Employee();
		emp.setFirstName("prathyusha");
		emp.setLastName("N");
		emp.setAge(24);
		dept = new Department();
		dept.setDeptName("Dept 1");
		dept.setId(10);
		emp.setDepartment(dept);
		List<Employee> employees = new ArrayList<>();
		employees.add(emp);
		dept.setEmployees(employees);	
	}
	
	@Test
	void getDeptByNameTest() throws DepartmentNotFoundException {
		
		Mockito.when(departmentService.getDepartmentByName("Dept 1")).thenReturn(dept);
		DepartmentResponse response = departmentController.getDepartment("Dept 1");
		assertNotNull(response);
		assertEquals(response.getDeptName(), "Dept 1");
		assertNotNull(response.getEmployees());
		assertEquals(response.getEmployees().size(), 1);
		
		
	}
	
	@Test
	public void whendeptnotfound_thenAssertexception() throws DepartmentNotFoundException {
		Mockito.when(departmentService.getDepartmentByName("Dept 2")).thenThrow(DepartmentNotFoundException.class);
	    Exception exception = assertThrows(DepartmentNotFoundException.class, () -> {
	    	 departmentController.getDepartment("Dept 2");
	    });
	}
	
	@Test
	void createDept_OK() {
		DepartmentController.DepartmentDto dto = new DepartmentController.DepartmentDto();
		dto.setDeptName("Dept 1");
		Department department = dto.toDepartment();
		Mockito.doNothing().when(departmentService).saveDepartment(department);
		Response response = departmentController.createDepartment(dto);
		
		assertNotNull(response);
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

		
	}
	

}
