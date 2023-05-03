package testemployee;

import org.acme.entity.Employee;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response.Status;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response.Status;
import static io.restassured.RestAssured.given;





@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class employeeTest {
	
	@Test
	@Order(1)
	public void getEmployees() {
		final Employee emp = new Employee();
		emp.setFirstName("prathyusha");
		emp.setLastName("neerugatti");
		emp.setAge(24);
		//emp.setDepartment();
		
		given()
			.body(emp)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
			.when()
			.post()
			.then()
				.statusCode(Status.CREATED.getStatusCode())
				.header("location", "http://localhost:8081/v1/employees/1");
				
			
	}

}
