package org.acme.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Optional;

import org.acme.exception.DepartmentNotFoundException;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department")
// extends PanacheEntity
public class Department extends PanacheEntity {

	
	//We don't need to define 'id' column explicitly, its already present in PanacheEntity which we extended
    @Column(name="dept_name", nullable = false)
    @NotBlank
    @Size(max = 256)
    private String deptName;
    
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employees;
    
    //Create static methods with the same logic as 'Repository'
    public static Department getDepartmentByName(String name) throws DepartmentNotFoundException {
    	Optional<Department> dept = find("upper(deptName)", name.toUpperCase().trim()).firstResultOptional();
    	return dept.orElseThrow(() -> new DepartmentNotFoundException("Department doesn't exist"));
    }
    
    //Again static method, Need to add @Transactional annotation for persist methods to work
    @Transactional
    public static void saveDepartment(Department department) {
    	department.persist();
    }
    
}
