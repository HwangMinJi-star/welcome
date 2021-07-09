package com.it.herb.product.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductVO {
	private int productNo;
	private int categoryNo;
	private String productName;
	private int sellPrice;
	private String company;
	private String imageUrl;
	private String explains;
	private String description;
	private Timestamp regDate;
	private int mileage;
}
