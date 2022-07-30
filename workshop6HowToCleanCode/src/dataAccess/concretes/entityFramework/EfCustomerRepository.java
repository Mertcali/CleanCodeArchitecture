package dataAccess.concretes.entityFramework;

import dataAccess.abstracts.CustomerRepositoryService;
import entities.concretes.Customer;

public class EfCustomerRepository implements CustomerRepositoryService{

	@Override
	public void add(Customer customer) {
		System.out.println("Entity Framework ile veritabanýna eklendi");
		
	}

	@Override
	public boolean customerExists(Customer customer) {
		
		return false;
	}

	
}
