package dataAccess.concretes.nHibernate;

import dataAccess.abstracts.EmployeeRepositoryService;
import entities.concretes.Employee;

public class NhEmployeeRepository implements EmployeeRepositoryService{

	@Override
	public void add(Employee employee) {
		System.out.println("nHibernate Framework ile veritabanýna eklendi");
		
	}

	@Override
	public boolean checkEmployeeExists(Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}

}
