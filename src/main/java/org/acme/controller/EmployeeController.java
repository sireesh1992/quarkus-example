package org.acme.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.acme.controller.DepartmentController.DepartmentResponse;
import org.acme.entity.Department;
import org.acme.entity.Employee;
import org.acme.exception.DepartmentNotFoundException;
import org.acme.exception.EmployeeNotFoundException;
import org.acme.service.DepartmentService;
import org.acme.service.EmployeeService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@RequestScoped
@Path("/v1/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class EmployeeController {
	
	@Inject
	private EmployeeService employeeService;
	
	@Inject
	private DepartmentService departmentService;


	@GET
	public List<EmployeeResponse> getEmployees() {
		List<Employee> emp = employeeService.getAllEmployees();
		List<EmployeeResponse> empResponseList = new ArrayList<>();
		for(Employee e : emp) {
			empResponseList.add(new EmployeeResponse(e));
		}		
		return empResponseList;
	}
	
	
	
	@GET
	@Path("/{id}")
	public EmployeeResponse getEmployees(@NotNull(message = "employee id must not be empty") @PathParam("id") long id) throws EmployeeNotFoundException {
		return new EmployeeResponse(employeeService.getEmployeeById(id));
	}
	
		
	
	@POST
	public Response createEmployee(@Valid EmployeeDto employeeDto) throws DepartmentNotFoundException {
		Employee employee = employeeDto.toEmployee();
		Department dept = departmentService.getDepartmentByName(employeeDto.getDeptName());
		employee.setDepartment(dept);
		employeeService.saveEmployee(employee);
		return Response.status(Response.Status.CREATED).build();
	}
	
	@Data
	public static class EmployeeDto {
		
		@NotBlank
		private String firstName;
		
		@NotBlank
		private String lastName;
		
		@Min(value = 1, message = "age must be more than 0")
		@Max(value = 200, message = "age must be less than 200")
		int age;
		
		private String deptName;
		
		public Employee toEmployee() {
			Employee employee = new Employee();
			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			employee.setAge(age);
			return employee;
		}
		
	}
	
	
	@Data
	@AllArgsConstructor
	public static class EmployeeResponse {
		
		private long id;
		private String firstName;
		private String lastName;
		private int age;
		private String deptName;
		private Department dept;
		private List<String> employees;
		
		public EmployeeResponse(Employee emp) {
			id = emp.getId();
			age = emp.getAge();
			deptName = emp.getDepartment().getDeptName();
			firstName = emp.getFirstName();
			lastName = emp.getLastName();
			
		}
		
	}
	
	
}
