package test.ringbackstage.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ringbackstage.BackstageApplication;
import ringbackstage.web.service.resource.AdminResourceService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=BackstageApplication.class)
public class ServiceTest {
	
	@Autowired
	private AdminResourceService adminResourceService;
	
	@Test
	public void testResource(){
		System.out.println(adminResourceService);
	}
	
}
