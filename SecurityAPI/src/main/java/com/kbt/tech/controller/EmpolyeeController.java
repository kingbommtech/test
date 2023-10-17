package com.kbt.tech.controller;


import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbt.tech.bean.Employee;
import com.kbt.tech.exception.ResourceNotFoundException;
import com.kbt.tech.repository.EmployeeRepository;



/**
 * @author KINSHUK MAITY
 *
 */


@RestController
@RequestMapping("/kbt/")
public class EmpolyeeController {
	

	
	@Autowired
	private EmployeeRepository empRepo;
	
	
	//get all employees
	//No need to secure
	@GetMapping("/home")
	public String home(){		
		String str="<h1>Welcome to Kingbomm tech</h1>";
		return str;
	}
	
	//get all employees
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees(){		
		List<Employee> empList= empRepo.findAll();
		return new ResponseEntity<>(empList, HttpStatus.OK);
	}
	
	//create the employee details
	@PostMapping("/saveEmployee")
	public Employee createEmployee(@RequestBody Employee emp) {		
		return empRepo.save(emp);		
	}
	
	@GetMapping("/employee/maxsal")
	public ResponseEntity<Optional<Employee>> getMaxSalEmployee(){
		List<Employee> empList =empRepo.findAll();
		Optional<Employee> maxSalEmp=empList.stream().collect(Collectors.maxBy(Comparator.comparingDouble(Employee :: getSalary)));	
		
		Optional<Employee> secondHighestSalEmp=empList.stream().sorted(Comparator.comparingDouble(Employee :: getSalary).reversed()).skip(1).findFirst();
		System.out.println("Second highest salary of Employee:"+secondHighestSalEmp.get());
		
		//Predicate is a Functional Interface where test is abstract method.It's return type is boolean. 
		Predicate<Integer> lessThen= i->(i<20);
	    System.out.println("Predicate Test: "+lessThen.test(10));
	    
	    //Function is a Functional Interface where apply is abstract method.It's return type is generic.
	    //Function<Input generic type,Return generic type>
	    Function<Integer,String> half= a ->String.valueOf(a/2);
	    System.out.println("Function Test: "+half.apply(20));
	    
	    //BiPredicate is a Functional Interface 
	    BiPredicate<Integer,String> lessThenBiPre = (a,b) -> { 
	    	                                                   if(a== Integer.parseInt(b))
	    	                                                	   return true;
	    	                                                   return false;
	    	                                                   
	                                                         }; 
	    System.out.println("BiPredicate Test: "+lessThenBiPre.test(2, "2"));
	    
	    //BiFunction is a Functional Interface
	    //BiFunction<Integer,Integer,String> addTwoNumber =
	    
	    List<Integer> list=Arrays.asList(1,2,6,8,9);
	    list.stream().filter(n->n%2==0).forEach(System.out::print);
	    
	    
	    
		
		return new ResponseEntity<>(maxSalEmp, HttpStatus.OK);
	}
	
	
	
	// get the employee by id
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
		Employee emp=empRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not Exist where Employee"+id));
		return ResponseEntity.ok(emp);
		
	}
	
	// update the employee by id
	@PutMapping("/setEmployee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee empDetails){
		Employee emp = empRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		emp.setFirstName(empDetails.getFirstName());
		emp.setLastName(empDetails.getLastName());
		emp.setEmailId(empDetails.getEmailId());
		emp.setAddress(empDetails.getAddress());
		emp.setSalary(empDetails.getSalary());
		emp.setStatus(empDetails.getStatus());
		
		Employee empUpdate= empRepo.save(emp);
		return new ResponseEntity<>(empUpdate,HttpStatus.OK);
	}
	
	//delete the employee by id
	@DeleteMapping("/deleteEmployee/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee emp = empRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		empRepo.delete(emp);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	
	
	/*
	{
    "firstName": "Gaurav",
    "lastName" : "Singh",
    "emailId":"gaurav.singh@gmail.com",
    "address":"ALIGARH",
    "salary": 60000.00,
    "status": {
            "status": "Active"
     }
}
	*/
	
	/*  
	 * // Accumulate names into a List
     List<String> list = people.stream().map(Person::getName).collect(Collectors.toList());

     // Accumulate names into a TreeSet
     Set<String> set = people.stream().map(Person::getName).collect(Collectors.toCollection(TreeSet::new));

     // Convert elements to strings and concatenate them, separated by commas
     String joined = things.stream()
                           .map(Object::toString)
                           .collect(Collectors.joining(", "));

     // Compute sum of salaries of employee
     int total = employees.stream()
                          .collect(Collectors.summingInt(Employee::getSalary)));

     // Group employees by department
     Map<Department, List<Employee>> byDept
         = employees.stream()
                    .collect(Collectors.groupingBy(Employee::getDepartment));

     // Compute sum of salaries by department
     Map<Department, Integer> totalByDept
         = employees.stream()
                    .collect(Collectors.groupingBy(Employee::getDepartment,
                                                   Collectors.summingInt(Employee::getSalary)));

     // Partition students into passing and failing
     Map<Boolean, List<Student>> passingFailing =
         students.stream()
                 .collect(Collectors.partitioningBy(s -> s.getGrade() >= PASS_THRESHOLD));

	 */

}
