package kr.or.connect.daoexam.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.daoexam.config.ApplicationConfig;
import kr.or.connect.daoexam.dao.RoleDao;
import kr.or.connect.daoexam.dto.Role;

public class JDBCTest {

	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);

		RoleDao roleDao = ac.getBean(RoleDao.class);
		
		Role role = new Role();
		role.setRoleId(201);
		role.setDescription("System Engineer");
		/*Insert 
		int count = roleDao.insert(role);
		System.out.println("Insert Query Completed : "+count);
		*/	

		//update
		int count = roleDao.update(role);
		System.out.println("Update Query Completed : "+count);
		
		Role resultRole = roleDao.selectById(201);
		System.out.println(resultRole);
		
		int deleteCount = roleDao.deleteById(500);
		System.out.println( deleteCount+" delete");
		
		Role resultRole2 = roleDao.selectById(500);
		System.out.println(resultRole2);
		
	
	}

}