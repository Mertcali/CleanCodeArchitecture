package dataAccess.abstracts;

import entities.concretes.Customer;

public interface CustomerRepositoryService{
	void add(Customer customer);
	boolean customerExists(Customer customer);
}
