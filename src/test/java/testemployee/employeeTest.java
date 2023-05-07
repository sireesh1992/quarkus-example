package testemployee;

import org.acme.controller.DepartmentController;
import org.acme.controller.EmployeeController;
import org.acme.controller.EmployeeController.EmployeeResponse;
import org.acme.entity.Department;
import org.acme.entity.Employee;
import org.acme.exception.DepartmentNotFoundException;
import org.acme.exception.EmployeeNotFoundException;
import org.acme.service.DepartmentService;
import org.acme.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class employeeTest {
	
	@InjectMock EmployeeService employeeService;
	@InjectMock DepartmentService departmentService;
	@Inject EmployeeController employeeController;
	
	private Employee emp;
	private Department dept;
	
	
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
	void getEmpByNameTest() throws EmployeeNotFoundException {
		
		Mockito.when(employeeService.getEmployeeById(1)).thenReturn(emp);
		EmployeeResponse response = employeeController.getEmployees(1);
		assertNotNull(response);
		assertEquals(response.getFirstName(), "prathyusha");
		assertEquals(response.getLastName(), "N");
		assertEquals(response.getAge(), 23);
		assertEquals(response.getDeptName(), "Dept 1");

		
	}
	
	@Test
	public void whendeptnotfound_thenAssertexception() throws EmployeeNotFoundException {
		Mockito.when(employeeService.getEmployeeById(20)).thenThrow(EmployeeNotFoundException.class);
	    Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
	    	 employeeController.getEmployees(20);
	    });
	}
	
	@Test
	void createEmployee_OK()  throws DepartmentNotFoundException  {
		EmployeeController.EmployeeDto dto = new EmployeeController.EmployeeDto();
		dto.setFirstName("Prathyusha");
		dto.setLastName("N");
		dto.setAge(24);
		dto.setDeptName("Dept 1");
		Employee employee = dto.toEmployee();
		Mockito.doNothing().when(employeeService).saveEmployee(employee);
		Response response = employeeController.createEmployee(dto);
		
		assertNotNull(response);
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

		
	}

}
