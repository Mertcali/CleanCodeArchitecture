package dataAccess.concretes.entityFramework;

import dataAccess.abstracts.EmployeeRepositoryService;
import entities.concretes.Employee;

public class EfEmployeeRepository implements EmployeeRepositoryService{

	@Override
	public void add(Employee employee) {
		System.out.println("Entity Framework ile veritabanýna eklendi");
		
	}


	@Override
	public boolean checkEmployeeExists(Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}

}
