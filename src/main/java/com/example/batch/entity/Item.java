package com.example.batch.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"name", "condition", "category", "brand", "price", "shipping", "description"})
public class Item {

    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("condition")
    private Integer condition;
    @JsonProperty("category")
    private Integer category;
    @JsonProperty("brand")
    private String brand;
    @JsonProperty("price")
    private Integer price;
    @JsonProperty("shipping")
    private Integer shipping;
    @JsonProperty("description")
    private String description;
    private String nameAll;
    
    public Item(){}
    
    
    
    public Item(String name, Integer condition, Integer category, String brand, 
    		Integer price, Integer shipping, String description) {    	
    }
    
	
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getShipping() {
        return shipping;
    }

    public void setShipping(Integer shipping) {
        this.shipping = shipping;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




    public String getNameAll() {
        return nameAll;
    }

    public void setNameAll(String nameAll) {
        this.nameAll = nameAll;
    }

    public String getDaiCategoryName() {
        return nameAll != null ? nameAll.split("/")[0] : "";
    }

    public String getChuCategoryName() {
        return nameAll != null ? nameAll.split("/")[1] : "";
    }

    public String getSyoCategoryName() {
        return nameAll != null ? nameAll.split("/")[2] : "";
    }

}
