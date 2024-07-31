package com.ait.vis.mongo.embeded.api.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "order_collections")
public class User {
	@Id
	private int id;
	private String firstName;
	private String lastName;
	private String gender;
	private List<Product> products;
	private Address address;

}
