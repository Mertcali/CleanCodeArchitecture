package dataAccess.concretes;

import java.util.ArrayList;
import java.util.List;

import dataAccess.abstracts.CourseDao;
import entities.Course;

public class CourseHibernateDao implements CourseDao{

	@Override
	public void add(Course user) {
		System.out.println("Hibernate ile eklendi.");
		
	}

	@Override
	public void delete(Course user) {
		System.out.println("Hibernate ile silindi.");
		
	}

	@Override
	public void update(Course user) {
		System.out.println("Hibernate ile güncellendi.");
		
	}

	@Override
	public List<Course> getAll() {
		List<Course> egitimler = new ArrayList<Course>();
		Course egitim1 = new Course(1,"Java",300);
		Course egitim2 = new Course(2,"C#",200);
		Course egitim3 = new Course(3,"Python",400);
		egitimler.add(egitim1);
		egitimler.add(egitim3);
		egitimler.add(egitim2);
		return egitimler;
	}



}
