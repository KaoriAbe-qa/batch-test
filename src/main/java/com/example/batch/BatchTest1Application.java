package com.example.batch;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.batch.controller.BatchController;
import com.example.batch.service.ItemService;

@SpringBootApplication
public class BatchTest1Application {
	
	@Autowired
	ItemService itemservice;

	@Autowired
	BatchController batchController;

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(BatchTest1Application.class, args);
	
		BatchTest1Application app = ctx.getBean(BatchTest1Application.class);
		try {
			app.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() throws IOException {
		batchController.runController();
	}
	
	/** 引数いる？
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(BatchTest1Application.class, args);
	
		BatchTest1Application app = ctx.getBean(BatchTest1Application.class);
		try {
			app.run(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(String... args) throws IOException {
		batchController.runController();
	}  
	  
	  */

}
