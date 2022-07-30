package dataAccess.abstracts;

import entities.concretes.Employee;

public interface EmployeeRepositoryService {
	void add(Employee employee);
	boolean checkEmployeeExists(Employee employee);
}
