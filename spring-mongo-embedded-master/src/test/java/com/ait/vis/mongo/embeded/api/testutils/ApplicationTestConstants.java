package com.ait.vis.mongo.embeded.api.testutils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ait.vis.mongo.embeded.api.model.Address;
import com.ait.vis.mongo.embeded.api.model.Product;
import com.ait.vis.mongo.embeded.api.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApplicationTestConstants {
	public static final String URL = "http://localhost:%s/api/orders";
	public static final int id = 0;
	public static final String firstName = "test";
	public static final String lastName = "test";
	public static final String gender = "m";
	public static final String name = "test";
	public static final String city = "toronto";
	public static final String updated_first_name = "James";

	public static User givenUserDocument() {
		User user = new User();
		user.setId(new Random().nextInt(100000) + 1);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		Product p = new Product();
		p.setName(name);
		p.setPrice(100);
		p.setQuantity(100);
		List<Product> products = new ArrayList<>();
		products.add(p);
		products.add(p);
		user.setProducts(products);
		Address a = new Address();
		a.setCity(city);
		a.setPincode("M1E2L5");
		a.setState("ON");
		user.setAddress(a);
		return user;
	}

	public static User givenUserDocumentByValue(String name, String prodName, String cityName) {
		User user = new User();
		user.setId(new Random().nextInt(100000) + 1);
		// user.setFirstName(firstName);
		if (name != null) {
			user.setFirstName(name);
		}

		user.setLastName(lastName);
		user.setGender(gender);
		Product p = new Product();
		// p.setName(name);
		if (prodName != null) {
			p.setName(prodName);
		}
		p.setPrice(100);
		p.setQuantity(100);

		List<Product> products = new ArrayList<>();
		products.add(p);
		products.add(p);
		user.setProducts(products);
		Address a = new Address();
		// a.setCity(city);
		if (cityName != null) {
			a.setCity(cityName);
		}
		a.setPincode("M1E2L5");
		a.setState("ON");
		user.setAddress(a);
		return user;
	}

	public static User givenUserDocumentWithoutProduct() {
		User user = new User();
		user.setId(new Random().nextInt(100000) + 1);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		List<Product> products = new ArrayList<>();
		user.setProducts(products);
		Address a = new Address();
		a.setCity(city);
		a.setPincode("M1E2L5");
		a.setState("ON");
		user.setAddress(a);
		return user;
	}
	
	public static User givenUserDocumentWithoutAddress() {
		User user = new User();
		user.setId(new Random().nextInt(100000) + 1);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		Product p = new Product();
		p.setName(name);
		p.setPrice(100);
		p.setQuantity(100);
		List<Product> products = new ArrayList<>();
		products.add(p);
		products.add(p);
		user.setProducts(products);
		Address a = null;
		user.setAddress(a);
		return user;
	}

	// For testing the Java Object
	public static String getJsonObject(User user) {
		ObjectMapper om = new ObjectMapper();
		String jsonUser = null;
		try {
			jsonUser = om.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return jsonUser;
	}
}
