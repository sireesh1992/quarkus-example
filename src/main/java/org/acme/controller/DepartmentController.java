package org.acme.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.acme.entity.Department;
import org.acme.entity.Employee;
import org.acme.exception.DepartmentNotFoundException;

import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Data;

@RequestScoped
@Path("/v1/departments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartmentController {

	@GET
	public DepartmentResponse getDepartment(@NotNull(message = "Department name must not be empty") @QueryParam("name") String name) throws DepartmentNotFoundException {
		//we can query database directly by just doing Department.< defined_method_name >. We used 'Department.' as its static method call
		return new DepartmentResponse(Department.getDepartmentByName(name));
	}
	
	@POST
	public Response createDepartment(@Valid DepartmentDto dto) {
		Department dept = dto.toDepartment();
		//Again static method call
		Department.saveDepartment(dept);
		return Response.ok(dept).status(Response.Status.CREATED).build();
	}
	
	
	@Data
	public static class DepartmentDto {
		
		@NotBlank
		private String deptName;

		public Department toDepartment() {
			Department department = new Department();
			department.setDeptName(deptName);
			return department;
		}
		
	}
	
	@Data
	@AllArgsConstructor
	public static class DepartmentResponse {
		
		private long id;
		private String deptName;
		private List<String> employees;
		
		public DepartmentResponse(Department dept) {
			id = dept.id;
			deptName = dept.getDeptName();
			employees = dept.getEmployees().stream().map(Employee::toString).collect(Collectors.toList());
		}
		
	}
	
}
