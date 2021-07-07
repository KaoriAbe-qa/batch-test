package com.example.batch.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.batch.entity.Item;
import com.example.batch.repository.ItemRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;



@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;
    
    //private Map<String, Item> itemMap;
    private List<Item> itemList;
    
    BufferedReader br = null;
	FileInputStream fi = null;
	InputStreamReader is = null;
    
    public void batchFile() throws IOException {
    	
    	CsvMapper mapper = new CsvMapper();
    	//ヘッダーなし、タブ区切り
    	CsvSchema schema = mapper.schemaFor(Item.class).withColumnSeparator('\t');
    	//tsvファイルのパス C:/rks/result-test.tsv
    	//Path path = Paths.get("src/main/resources/tmp/tsv/result0.tsv");
    	
    	//utf-8に変換 パス指定
    	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/tmp/tsv/test2.tsv"),"utf-8"));;

    	//うまく読み込めないので↑のやり方に変更
//		try {
//			br = Files.newBufferedReader(path);	//UTF-8に変換したい…
//		} catch (IOException e) {
//			System.out.println("ファイル読み込みエラー");
//		}   	
		
    	MappingIterator<Item> it = mapper.readerFor(Item.class).with(schema).readValues(br);    	
    	
    	//ファイル１行ずつ読み込み
    	while(it.hasNextValue()) {
    		Item item = it.nextValue();
    		System.out.println(item.getName());
    		itemjudge(item);
    	}
    	   	
    	try {
			br.close();
		} catch (IOException e) {
			System.out.println("ファイル閉じるエラー");
		}
    	
    }
    
    
    public void itemjudge(Item item) {
    	    	
    	String name = item.getName();
    	Integer category = item.getCategory();
    	Integer price = item.getPrice();
    		
    	if(itemRepository.tsvCount(name, category) == 0) {
    		itemRepository.tsvInsert(item);
    	}else {
    		if(itemRepository.tsvPrice(name, category) == price) {
    			//同一Item
    			System.out.println("同じItem");
    		}else {
    			itemRepository.tsvUpdate(item);
    		}   			   			
    	}    		   	
    }
    	
    /*
      public void itemjudge(List<Item> item) {
    	    	
    	for(Item itemparts : item) {
    		String name = itemparts.getName();
    		Integer category = itemparts.getCategory();
    		Integer price = itemparts.getPrice();
    		
    		if(itemRepository.tsvCount(name, category) == 0) {
    			itemRepository.tsvInsert(itemparts);
    		}else {
    			if(itemRepository.tsvPrice(name, category) == price) {
    				//同一Item
    				System.out.println("同じItem");
    			}else {
    				itemRepository.tsvUpdate(itemparts);
    			}   			   			
    		}    		
    	}    	
    }
     
    
    
     */
    

}


