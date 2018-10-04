package edu.birzeit;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.AssertJUnit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.InOrderImpl;
import org.mockito.internal.verification.InOrderContextImpl;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.birzeit.dao.DemoSesssionFactory;
import edu.birzeit.dao.DepartmentDAO;
import edu.birzeit.dao.DepartmentHibernateDAO;
import edu.birzeit.entity.Department;

public class DepartmentsSerivceTest{

	@InjectMocks
	DepartmentsSerivce departmentsSerivce;
	
	@Mock
	DepartmentDAO departmentHibernateDAO;
	
	@BeforeClass
    public void setup() {
		 MockitoAnnotations.initMocks(this);
    }
	
	@BeforeMethod
    public void beforeTest() {
		 Mockito.reset(departmentHibernateDAO);
    }
	
	@Test
	public void deleteDepartmentTest() {
		String departMentName = "sampleName";
		Mockito.when(departmentHibernateDAO.deleteDepartmentByName(Mockito.anyString())).thenReturn(true);
		String result = departmentsSerivce.deleteDepartment(departMentName);
		Assert.assertNotNull(result);
		Assert.assertEquals(result, "Delete Operation Succeeded");
	}
	
	@Test
	public void deleteDepartmentTest_faiulre() {
		String departMentName = "sampleName";
		Mockito.when(departmentHibernateDAO.deleteDepartmentByName(Mockito.anyString())).thenReturn(false);
		String result = departmentsSerivce.deleteDepartment(departMentName);
		Assert.assertNotNull(result);
		AssertJUnit.assertEquals(result, "Delete Operation Failed");
	}
	
	@Test
	public void searchDepartmentTest() {
		String departMentName = "sampleName";
		Department department = Mockito.mock(Department.class);
		Mockito.when(department.getName()).thenReturn(departMentName);
		
		Mockito.when(departmentHibernateDAO.findDepartmentById(Mockito.anyString())).thenReturn(department);
		
		Department result = departmentsSerivce.searchDepartments(departMentName);
		AssertJUnit.assertNotNull(result);
		AssertJUnit.assertEquals(departMentName, result.getName());
	}
	
	@Test(description = "Negative test case")
	public void searchDepartmentTest_faiulre() {
		String departMentName = "sampleName";
		Mockito.when(departmentHibernateDAO.findDepartmentById(Mockito.anyString())).thenThrow(HibernateException.class);
		Department result = departmentsSerivce.searchDepartments(departMentName);
		System.out.println("searchDepartmentTest_faiulre : "+result);
		
		Assert.assertNull(result);
		Mockito.verify(departmentHibernateDAO, Mockito.times(1)).findDepartmentById(departMentName);
	}
	
	@Test
	public void searchDepartmentTest2() {
		
		String departmentName = "sampleName";
		String departmentName1= "name-1";

		Mockito.when(departmentHibernateDAO.findDepartmentById(Mockito.anyString())).then(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				String id = (String) args[0];
				if(id.equals("1")) {
					return new Department(departmentName1, "headOfDepartment-1", null);
				}

				return new Department(departmentName, "sample head of dep", null);
			}
		});
		
		Department result = departmentsSerivce.searchDepartments("1");
		Assert.assertNotNull(result);
		Assert.assertNotSame(departmentName, result.getName());
		Assert.assertEquals(departmentName1,  result.getName());

		result = departmentsSerivce.searchDepartments("2");
		Assert.assertNotNull(result);
		Assert.assertNotSame(departmentName1, result.getName());
		Assert.assertEquals(departmentName,  result.getName());
	}
	
	@Test
	public void demoSpyFeature() {
		List<String> spyList= Mockito.spy(new LinkedList<>());
		Mockito.when(spyList.size()).thenReturn(20);
		spyList.add("one");
		
		System.out.println("[demoSpyFeature] Object at index 0 is : "+spyList.get(0));
		System.out.println("[demoSpyFeature] list size is : " +spyList.size());
	}
	
	@Test
	public void demoSpyFeature2() {
		DepartmentDAO spyDepartmentHibernateDAO = Mockito.spy(new DepartmentHibernateDAO()) ;
		String result = spyDepartmentHibernateDAO.demoenstrateSpy("DummyString");
		Assert.assertEquals("DummyString is returned by Spied Object", result );
		
		String spiedString = "My Mocked Response!";
		Mockito.when(spyDepartmentHibernateDAO.demoenstrateSpy(Mockito.anyString())).thenReturn("My Mocked Response!");
		
		result = spyDepartmentHibernateDAO.demoenstrateSpy("DummyString");
		Assert.assertNotEquals("DummyString is returned by Spied Object", result );
		Assert.assertEquals(spiedString, result );
		
	}
    
	@Test
	public void demoOrderVerification() {
		DepartmentHibernateDAO spyDepartmentHibernateDAO = Mockito.spy(new DepartmentHibernateDAO()) ;
		
		List<Object> list = new ArrayList<>();
		list.add(departmentHibernateDAO);
		list.add(spyDepartmentHibernateDAO);
		InOrder inorder = new InOrderImpl(list);
		
		departmentHibernateDAO.demoenstrateSpy("1");
		spyDepartmentHibernateDAO.demoenstrateSpy("2");
		
		
		inorder.verify(spyDepartmentHibernateDAO).demoenstrateSpy(Mockito.anyString());
		inorder.verify(departmentHibernateDAO).demoenstrateSpy(Mockito.anyString());
	}
}
