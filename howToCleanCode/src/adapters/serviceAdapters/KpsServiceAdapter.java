package adapters.serviceAdapters;

import business.abstracts.CheckPersonService;
import entities.concretes.Person;

public class KpsServiceAdapter implements CheckPersonService {
	@Override
	public boolean CheckPerson(Person person) {
		KpsService kpsService = new KpsService();
		return kpsService.checkPerson(Long.parseLong(person.getIdentityNumber()), person.getFirstName(),
				person.getLastName(), person.getDogumYili());
	}
}

class KpsService {
	public boolean checkPerson(long tcNo, String adi, String soyadi, int dogumYili) {
		return true;
	}
}
