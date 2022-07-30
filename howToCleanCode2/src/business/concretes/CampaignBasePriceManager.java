package business.concretes;

import java.util.List;

import business.abstracts.CampaignService;
import entities.Course;

public class CampaignBasePriceManager implements CampaignService{

	@Override
	public void updatePrice(List<Course> egitimler) {
		for (Course course : egitimler) {
			course.setPrice(getCurrentBasePrice());
		}
		
	}
	
	private double getCurrentBasePrice() {
		return 25;
		
	}

}
