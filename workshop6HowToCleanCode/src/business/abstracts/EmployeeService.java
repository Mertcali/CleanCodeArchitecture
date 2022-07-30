package business.abstracts;

import entities.concretes.Customer;
import entities.concretes.Employee;

public interface EmployeeService {
	void add(Employee employee) throws Exception;
	void addByOtherBusiness(Employee employee) throws Exception;
}
