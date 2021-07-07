package com.example.batch.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.batch.entity.Item;

@Repository
public class ItemRepository {

    private static final Logger logger = LoggerFactory.getLogger(ItemRepository.class);

    //private static final RowMapper<Item> ROW_MAPPER = new BeanPropertyRowMapper<>(Item.class);

    private static final RowMapper<Item> ROW_MAPPER = (rs, i) -> {
    	Item item = new Item();
    	item.setName(rs.getString("name"));
    	item.setCondition(rs.getInt("condition"));
    	item.setCategory(rs.getInt("category"));
    	item.setBrand(rs.getString("brand"));
    	item.setPrice(rs.getInt("price"));
    	item.setShipping(rs.getInt("shipping"));
    	item.setDescription(rs.getString("description"));
    	return item;
    	};        
    
    
    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    
    /*
    public List<Item> searchAll() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = "SELECT";
        sql += "  i.id id";
        sql += " , i.name \"name\"";
        sql += " , condition";
        sql += " , category";
        sql += " , brand";
        sql += " , price";
        sql += " , shipping";
        sql += " , description";
        sql += " , name_all";
        sql += " FROM items i";
        sql += " LEFT JOIN category c ON c.id = i.category";
        sql += " WHERE 1 = 1";

        return namedJdbcTemplate.query(sql, params, ROW_MAPPER);
      
    }
    
    */
    
    //
    public Item tsvInsert(Item item) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(item);
        String sql = "INSERT INTO items";
        sql += " (name,condition,category,brand,price,shipping,description)";
        sql += "  VALUES(:name,:condition,:category,:brand,:price,:shipping,:description);";

        namedJdbcTemplate.update(sql, params);
        
        return item; 
    }
    
    //
    public Item tsvUpdate(Item item) {
    	SqlParameterSource params = new BeanPropertySqlParameterSource(item);
        String sql = "UPDATE items";
        sql += " SET price=:price";
        sql += " WHERE name=:name AND category=:category;";

        namedJdbcTemplate.update(sql, params);
        
        return item; 
    }
    
    //
    public Integer tsvCount(String name, Integer category) {
    	
        String sql = "SELECT COUNT(*) FROM items";
        sql += " WHERE name=:name AND category=:category;";
        
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("name", name).addValue("category", category);
        
        return namedJdbcTemplate.queryForObject(sql, params, Integer.class);
        
    }
    
    //
    public Integer tsvPrice(String name, Integer category) {
    	
        String sql = "SELECT price FROM items";
        sql += " WHERE name=:name AND category=:category;";
        
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("name", name).addValue("category", category);
        
        return namedJdbcTemplate.queryForObject(sql, params, Integer.class);
        
    }


}
