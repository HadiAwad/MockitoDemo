package edu.birzeit.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import edu.birzeit.entity.Department;

public class DepartmentHibernateDAO implements DepartmentDAO  {
	
	@Autowired
	DemoSesssionFactory sessionFactory;

	@Override
	public Department findDepartmentById(String id) throws HibernateException  {
        Session session = sessionFactory.getSession();
        Transaction tx = null;
        Department department = null;
        try {
            tx = session.beginTransaction();
            department = session.get(Department.class, id);
   
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            session.close();
     
        }
        
        return department;
	}

	@Override
	public List<Department> getAllDepartments() {
        Session session = sessionFactory.getSession();
        List<Department> deparmentsList = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            deparmentsList = session.createQuery("FROM Department").list();
            for (Iterator<Department> iterator = deparmentsList.iterator(); iterator.hasNext();) {
                Department department = iterator.next();
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return deparmentsList;
	}

	@Override
	public Integer addDepartment(String name, String headOfDepartment, String faculty) {
        Session session = sessionFactory.getSession();
        Transaction tx = null;
        Integer depId = null;
        try {
            tx = session.beginTransaction();
            Department departments = new Department(name, headOfDepartment, faculty);
            depId = (Integer) session.save(departments);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return depId;
    }
	
	@Override
    public void updateDeparment(Integer depID, String headOfDepartment, String faculty) {
        Session session = sessionFactory.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Department dep = session.get(Department.class, depID);
            dep.setHeadOfDepartment(headOfDepartment);
            dep.setFaculty(faculty);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
	
	@Override
    public void deleteDepartment(Integer depId) {
        Session session = sessionFactory.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Department department = session.get(Department.class, depId);
            session.delete(department);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

	@Override
    public boolean deleteDepartmentByName(String name) {
        Session session = sessionFactory.getSession();
        Transaction tx = null;
        boolean operation = true;
        try {
            tx = session.beginTransaction();
            Criteria query = session.createCriteria(Department.class);
            query.add(Restrictions.eq("name", name));
            @SuppressWarnings("unchecked")
			List<Department> list = query.list();
            for (Department departments : list) {
                System.out.println("Will delete the following department : " + departments.getName());
                session.delete(departments);
            }
            tx.commit();
        } catch (HibernateException e) {
        	operation = false;
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            System.out.println("DepartmentHibernateDAO.deleteDepartmentByName(): "+e.getMessage());
        } finally {
            session.close();
        }
        
        return operation;
    }
	
	@Override
    public String demoenstrateSpy(String name) {
      return name + " is returned by Spied Object";
    }
}
