package edu.birzeit.runner;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.birzeit.DepartmentsSerivce;
import edu.birzeit.dao.DemoSesssionFactory;
import edu.birzeit.dao.DepartmentDAO;
import edu.birzeit.dao.DepartmentHibernateDAO;
import javassist.NotFoundException;

public class MockitoDemoSpringConfig {
	
	public static void main (String[] args) {
        AnnotationConfigApplicationContext context = new
                            AnnotationConfigApplicationContext(Config.class);
        DepartmentsSerivce managingDepartments = context.getBean(DepartmentsSerivce.class);
		managingDepartments.searchDepartments("1");

    }

    @Configuration
    public static class Config {

        @Bean(autowire = Autowire.NO)
        public DemoSesssionFactory getSessionFactory () {
            return new DemoSesssionFactory();
        }

        @Bean(name="hibernateDAO")
        public DepartmentDAO getDepartmentDAO () {
            return new DepartmentHibernateDAO();
        }
        
        @Bean
        public DepartmentsSerivce getDepartmentsSerivce  () {
            return new DepartmentsSerivce();
        }
    }

  

}
