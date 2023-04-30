package org.acme.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    
    @Column(name="first_name", nullable = false)
    @NotBlank
    @Size(max = 256)
    private String firstName;

    @Column(name="last_name", nullable = false)
    @NotBlank
    @Size(max = 256)
    private String lastName;
    
    @Column(name="age", nullable = false)
    @Min(1)
    @Max(200)
    private int age;
    
    @ManyToOne
    @JoinColumn(name = "dept_id", referencedColumnName="id", nullable = false)
    private Department department;

    @Override
    public String toString() {
    	return firstName + ' ' + lastName;
    }
    
}
