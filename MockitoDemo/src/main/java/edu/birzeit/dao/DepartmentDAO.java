package edu.birzeit.dao;

import java.util.List;

import org.hibernate.HibernateException;

import edu.birzeit.entity.Department;

public interface DepartmentDAO {

	public Department findDepartmentById(String id) throws HibernateException  ;

	public List<Department> getAllDepartments();

	public Integer addDepartment(String name, String headOfDepartment, String faculty);

	public void updateDeparment(Integer depID, String headOfDepartment, String faculty);

	public void deleteDepartment(Integer depId) ;

	public boolean deleteDepartmentByName(String name);

	public String demoenstrateSpy(String name);

}
