package business.concretes;

import business.abstracts.CheckPersonService;
import business.abstracts.EmployeeService;
import dataAccess.abstracts.EmployeeRepositoryService;
import entities.concretes.Employee;
import entities.concretes.Person;

public class EmployeeManager implements EmployeeService{
	
	EmployeeRepositoryService employeeRepositoryService;
	CheckPersonService checkPersonService;
	public EmployeeManager(EmployeeRepositoryService employeeRepositoryService,CheckPersonService checkPersonService) {
		this.employeeRepositoryService = employeeRepositoryService;
		this.checkPersonService = checkPersonService;
	}

	@Override
	public void add(Employee employee) throws Exception {
		validateFirstName(employee);
		validateIdentityNumber(employee);
		validateLastName(employee);
		

		checkPersonExists(employee);
		checkEmployeeExists(employee);
		employeeRepositoryService.add(employee);
		
	}

	@Override
	public void addByOtherBusiness(Employee employee) throws Exception {
		validateFirstName(employee);
		validateIdentityNumber(employee);
		validateLastName(employee);
		validateFirstNameLength(employee);
		
		checkPersonExists(employee);
		checkEmployeeExists(employee);
		employeeRepositoryService.add(employee);
		
	}


private void checkPersonExists(Person person) throws Exception {
	if(!(checkPersonService.CheckPerson(person))) {
		throw new Exception("Kiþi bilgileri hatalý");
	}
}

private void validateFirstName(Person person) throws Exception {
	if (person.getFirstName().isEmpty()) {
		throw new Exception("VALÝDASYON HATASI");
	}
}

private void validateIdentityNumber(Person person) throws Exception {
	if (person.getIdentityNumber().isEmpty()) {
		throw new Exception("VALÝDASYON HATASI");
	}
}

private void validateLastName(Person person) throws Exception {
	if (person.getLastName().isEmpty()) {
		throw new Exception("VALÝDASYON HATASI");
	}
}

private void validateFirstNameLength(Person person) throws Exception {
	if (person.getFirstName().length() < 2) {
		throw new Exception("VALÝDASYON HATASI");
	}
}

private void checkEmployeeExists(Employee employee) throws Exception {
	
	if (employeeRepositoryService.checkEmployeeExists(employee)) {
		throw new Exception("Müþteri mevcut");
	}
}}
