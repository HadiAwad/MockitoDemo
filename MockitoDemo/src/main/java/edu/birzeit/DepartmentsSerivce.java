package edu.birzeit;

import java.util.Collections;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.birzeit.dao.DepartmentDAO;
import edu.birzeit.entity.Department;

@Component
public class DepartmentsSerivce {

	@Autowired
	DepartmentDAO departmentHibernateDAO;

	public int createDepartMent(String name, String headOfDep, String faculty) {
		Integer id = departmentHibernateDAO.addDepartment(name, headOfDep, faculty);
		return id;
	}

	public String deleteDepartment(String departMentName) {
		boolean operation = departmentHibernateDAO.deleteDepartmentByName(departMentName);
		return "Delete Operation" + (operation?" Succeeded":" Failed");
	}

	public List<Department> getAllDepartmentsWithPagination(int page, int pageSize) {
		
		//This method connects to DB and Query DEpartments Table 
		List<Department> list = departmentHibernateDAO.getAllDepartments();
		
		if (pageSize <= 0 || page <= 0) {
			throw new IllegalArgumentException("invalid page size: " + pageSize);
		}

		int fromIndex = (page - 1) * pageSize;
		if (list == null || list.size() < fromIndex) {
			return Collections.emptyList();
		}

		// toIndex exclusive
		return list.subList(fromIndex, Math.min(fromIndex + pageSize, list.size()));
	}
	
	public Department searchDepartments(String departmentId)  {
		Department department = null;
		try {
			department = departmentHibernateDAO.findDepartmentById(departmentId);
			department.setFaculty("Some faculty");
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	
		return department;
		
	}
}
