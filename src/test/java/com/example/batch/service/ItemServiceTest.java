package com.example.batch.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.batch.entity.Item;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ItemServiceTest {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	@Autowired
	private ItemService itemService;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		//SQLでDBに情報を入れる
		//一回実行したら消さないと重複のエラーがでる→@AfterEachで削除実行
		String sql = "INSERT INTO items";
		       sql += " (name,condition,category,brand,price,shipping,description)";
		       sql += "  VALUES('dummyName',1,1,'dummyBrand',1,1,'dummyDescription');";
				
		SqlParameterSource param = new MapSqlParameterSource();
		template.update(sql, param);
	}

	@AfterEach
	void tearDown() throws Exception {
		//テスト用のダミーコード削除
		String sql = "DELETE FROM items WHERE name='dummyName' OR name='dummyName2';";
		SqlParameterSource param = new MapSqlParameterSource();
		template.update(sql, param);
	}
	
	@DisplayName("testBatchFileメソッドのテスト：tsv読み込み")
	@ParameterizedTest
	@CsvSource({
		"dummyName, 1" 	//重複データ
		, "dummyName2, 2"	//新規データ
	})
	void testBatchFile(String name, Integer price) {	
		try {
			itemService.batchFile();
		} catch (IOException e) {
			System.out.println("batchFileテスト失敗");
		}
		
		String sql = "SELECT price FROM items WHERE name=:name;";
        SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);       
        Integer priceAnswer = template.queryForObject(sql, param, Integer.class);
        
		assertEquals(price, priceAnswer);
	}

	@DisplayName("itemjudgeメソッドのテスト：分岐の確認")
	@ParameterizedTest
	@CsvSource({
		"dummyName,1,1,dummyBrand,1,1,dummyDescription, 1" 		//重複データ
		, "dummyName,1,1,dummyBrand,10,1,dummyDescription, 10"	//金額更新データ
		, "dummyName2,2,2,dummyBrand2,2,2,dummyDescription2, 2"	//新規データ
	})
	void testItemjudge(String name, Integer condition, Integer category, String brand, 
    		Integer price, Integer shipping, String description, Integer priceResult) {
		Item item = new Item();
    	item.setName(name);
    	item.setCondition(condition);
    	item.setCategory(category);
    	item.setBrand(brand);
    	item.setPrice(price);
    	item.setShipping(shipping);
    	item.setDescription(description);
    	
    	itemService.itemjudge(item);
    	
    	String sql = "SELECT price FROM items WHERE name=:name;";
        SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);       
        Integer priceAnswer = template.queryForObject(sql, param, Integer.class);
        
        assertEquals(priceAnswer, priceResult);
    	
	}

}
