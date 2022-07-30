package business.abstracts;

import java.util.List;

import entities.Course;

public interface CourseService {

	void add(Course course);
	List<Course> getAll();
	
}
