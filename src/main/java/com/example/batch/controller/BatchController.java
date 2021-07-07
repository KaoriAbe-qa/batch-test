package com.example.batch.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.batch.service.ItemService;


@Controller
public class BatchController {
	
	@Autowired
    private ItemService itemService;
	
	public void runController() throws IOException {		
		itemService.batchFile();
	}

}
