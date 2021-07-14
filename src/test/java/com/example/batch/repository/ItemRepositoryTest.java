package com.example.batch.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
class ItemRepositoryTest {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	@Autowired
	private ItemRepository itemRepository;

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

	@DisplayName("tsvInsertメソッドのテスト：IDにitemの情報を入れる")
	@Test
	void testTsvInsert() {
		Item item = new Item();
    	item.setName("dummyName2");
    	item.setCondition(2);
    	item.setCategory(2);
    	item.setBrand("dummyBrand2");
    	item.setPrice(2);
    	item.setShipping(2);
    	item.setDescription("dummyDescription2");
		
    	Item itemResult = itemRepository.tsvInsert(item);
    	
    	assertEquals("dummyName2", itemResult.getName());
    	assertEquals(2, itemResult.getCondition());
    	assertEquals(2, itemResult.getCategory());
    	assertEquals("dummyBrand2", itemResult.getBrand());
    	assertEquals(2, itemResult.getPrice());
    	assertEquals(2, itemResult.getShipping());
    	assertEquals("dummyDescription2", itemResult.getDescription());   	
	}
	
	
	@DisplayName("tsvUpdateメソッドのテスト：name,categoryが一致していたらprice更新")
	@Test
	void testTsvUpdate() {
		Item item = new Item();
    	item.setName("dummyName");
    	item.setCondition(2);
    	item.setCategory(1);
    	item.setBrand("dummyBrand2");
    	item.setPrice(2);
    	item.setShipping(2);
    	item.setDescription("dummyDescription2");
		
    	Item itemUpdata = itemRepository.tsvUpdate(item);
		
    	assertEquals(2, itemRepository.tsvPrice(itemUpdata.getName(), itemUpdata.getCategory()));
	}
	
	@DisplayName("tsvCountメソッドのテスト：DBに何件あるか取得する")
	@ParameterizedTest
	@CsvSource({
		"dummyName, 1, 1" 
		, "dummyName, 2, 0"
	})
	void testTsvCount(String name, Integer category, int result) {
		itemRepository.tsvCount(name, category);
		assertEquals(result, itemRepository.tsvCount(name, category));
	}
	
	
	@DisplayName("tsvPriceメソッドのテスト：name,categoryが一致しているPriceを持ってくる")
	@ParameterizedTest
	@CsvSource({
		"dummyName, 1, 1" 
		//エラー確認用 "dummyName, 2"
	})
	void testTsvPrice(String name, Integer category, int result) {		
		assertEquals(1, itemRepository.tsvPrice(name, category));
	}
	

}
