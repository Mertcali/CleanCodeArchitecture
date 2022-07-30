package workshop6HowToCleanCode;

import adapters.serviceAdapters.KpsServiceAdapter;
import business.abstracts.CustomerService;
import business.concretes.CustomerManager;
import dataAccess.abstracts.CustomerRepositoryService;
import dataAccess.concretes.entityFramework.EfCustomerRepository;
import entities.concretes.Customer;

public class Main {

	public static void main(String[] args) throws Exception {

		CustomerService custService = new CustomerManager(new EfCustomerRepository(), new KpsServiceAdapter());
		custService.add(new Customer());
	}
}


	


