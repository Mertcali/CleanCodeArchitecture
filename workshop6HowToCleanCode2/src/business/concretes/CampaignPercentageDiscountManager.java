package business.concretes;

import java.util.List;

import business.abstracts.CampaignService;
import entities.Course;

public class CampaignPercentageDiscountManager implements CampaignService{

	@Override
	public void updatePrice(List<Course> egitimler) {
		for (Course course : egitimler) {
			course.setPrice(course.getPrice()-(course.getPrice()*(getPercentageDiscount()/100)));
		}
		
	}
	
	private double getPercentageDiscount() {
		return 10;
		
	}

}
