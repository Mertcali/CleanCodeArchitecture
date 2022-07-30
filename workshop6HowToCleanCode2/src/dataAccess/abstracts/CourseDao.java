package dataAccess.abstracts;

import java.util.List;

import entities.Course;

public interface CourseDao {
	
	void add(Course user);
	void delete(Course user);
	void update(Course user);
	List<Course> getAll();

}
