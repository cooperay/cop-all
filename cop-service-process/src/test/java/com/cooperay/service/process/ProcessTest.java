package com.cooperay.service.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cooperay.facade.process.service.WorkFlowFacade;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProcessTest {

	@Autowired
	private WorkFlowFacade workFlowFacade;
	
	@Test
	public void deployTest(){
		File file = new File("/home/cooperay/test.zip");
		ZipInputStream inputStream = null;
		try {
			inputStream = new ZipInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		workFlowFacade.deploy(inputStream,"test");;
	}
	
	
	
}
