package business.concretes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import business.abstracts.CourseService;
import business.abstracts.CampaignService;
import dataAccess.abstracts.CourseDao;
import entities.Course;

public class CourseManager implements CourseService{

	private CourseDao courseDao;
	private CampaignService campaignService;
	
	public CourseManager(CourseDao courseDao, CampaignService campaignService) {
		super();
		this.courseDao = courseDao;
		this.campaignService = campaignService;
	}

	@Override
	public void add(Course course) {
		courseDao.add(course);
		
	}

	@Override
	public List<Course> getAll() {
				
		List<Course> egitimler = courseDao.getAll();
		campaignService.updatePrice(egitimler);
		return egitimler;
		
	}
	


}
