package business.concretes;

import business.abstracts.CheckPersonService;
import business.abstracts.CustomerService;
import dataAccess.abstracts.CustomerRepositoryService;
import entities.concretes.Customer;
import entities.concretes.Person;

public class CustomerManager implements CustomerService {

	CustomerRepositoryService customerRepositoryService;
	CheckPersonService checkPersonService;
	public CustomerManager(CustomerRepositoryService customerRepositoryService,CheckPersonService checkPersonService) {
		this.customerRepositoryService = customerRepositoryService;
		this.checkPersonService = checkPersonService;
	}

	public void add(Customer customer) throws Exception {

		validateFirstName(customer);
		validateIdentityNumber(customer);
		validateLastName(customer);
		

		checkPersonExists(customer);
		checkCustomerExists(customer);
		customerRepositoryService.add(customer);
	}

	public void addByOtherBusiness(Customer customer) throws Exception {
		validateFirstName(customer);
		validateIdentityNumber(customer);
		validateLastName(customer);
		validateFirstNameLength(customer);
		
		checkPersonExists(customer);
		checkCustomerExists(customer);
		customerRepositoryService.add(customer);
	}

	private void checkPersonExists(Person person) throws Exception {
		if(!(checkPersonService.CheckPerson(person))) {
			throw new Exception("Kiþi bilgileri hatalý");
		}
	}

	private void validateFirstName(Customer customer) throws Exception {
		if (customer.getFirstName().isEmpty()) {
			throw new Exception("VALÝDASYON HATASI");
		}
	}

	private void validateIdentityNumber(Customer customer) throws Exception {
		if (customer.getIdentityNumber().isEmpty()) {
			throw new Exception("VALÝDASYON HATASI");
		}
	}

	private void validateLastName(Customer customer) throws Exception {
		if (customer.getLastName().isEmpty()) {
			throw new Exception("VALÝDASYON HATASI");
		}
	}

	private void validateFirstNameLength(Customer customer) throws Exception {
		if (customer.getFirstName().length() < 2) {
			throw new Exception("VALÝDASYON HATASI");
		}
	}

	private void checkCustomerExists(Customer customer) throws Exception {
		
		if (customerRepositoryService.customerExists(customer)) {
			throw new Exception("Müþteri mevcut");
		}
	}
}
