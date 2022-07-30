package dataAccess.concretes.nHibernate;

import dataAccess.abstracts.CustomerRepositoryService;
import entities.concretes.Customer;

public class NhCustomerRepository implements CustomerRepositoryService{

	@Override
	public void add(Customer customer) {
		System.out.println("NH ile veritabanưna eklendi");
		
	}

	@Override
	public boolean customerExists(Customer customer) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
