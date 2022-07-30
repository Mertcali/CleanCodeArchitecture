import java.util.List;

import business.abstracts.CourseService;
import business.concretes.CourseManager;
import business.concretes.CampaignBasePriceManager;
import business.concretes.CampaignPercentageDiscountManager;
import dataAccess.concretes.CourseHibernateDao;
import entities.Course;

public class Main {

	public static void main(String[] args) {
		CourseService courseService = new CourseManager(new CourseHibernateDao(),new CampaignBasePriceManager());
		Course egitim1 = new Course(1,"Java",300);
		courseService.add(egitim1);
		
		for (Course course : courseService.getAll()) {
			System.out.println("Eðitim: " +course.getName() +" Fiyat: " +course.getPrice());
		}

	}

}
