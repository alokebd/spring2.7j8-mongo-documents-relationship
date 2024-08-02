package com.ait.vis.mongo.embeded.api.controller;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import com.ait.vis.mongo.embeded.api.SpringMongoEmbededApplication;
import com.ait.vis.mongo.embeded.api.config.ApplicationAttributes;
import com.ait.vis.mongo.embeded.api.exception.ErrorMessages;
import com.ait.vis.mongo.embeded.api.model.User;
import com.ait.vis.mongo.embeded.api.testutils.ApplicationTestConstants;
import com.ait.vis.mongo.embeded.api.testutils.HttpHelper;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = SpringMongoEmbededApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserOrderControllerTests {
	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	private int port;

	@Test
	public void test_1_should_return_201_place_order() {
		// Given
		String url = String.format(ApplicationTestConstants.URL, port);
		User user = ApplicationTestConstants.givenUserDocument();
		HttpEntity<User> request = HttpHelper.getHttpEntity(user);
		// When
		ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.POST, request, String.class);
		// Then
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().toString(), ApplicationAttributes.SAVED_SUCCESS);
	}

	@Test
	public void test_2_should_return_200_get_users_by_name() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		String url = String.format(ApplicationTestConstants.URL.concat("/").concat(user.getFirstName()), port);
		// When
		ResponseEntity<List<User>> response = testRestTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {
				});
		// Then
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());

		List<User> users = response.getBody();
		assertNotNull(users.get(0));
	}

	@Test
	public void test_3_should_return_200_get_users_by_city() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		String url = String
				.format(ApplicationTestConstants.URL.concat("/addresses/").concat(user.getAddress().getCity()), port);
		// When
		ResponseEntity<List<User>> response = testRestTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {
				});
		// Then
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());

		List<User> users = response.getBody();
		assertNotNull(users.get(0));
	}

	@Test
	public void test_4_should_return_200_update_order_by_supplied_user() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		int id = user.getId();
		HttpEntity<User> request = HttpHelper.getHttpEntity(user);
		String url = String.format(ApplicationTestConstants.URL, port);
		testRestTemplate.exchange(url, HttpMethod.POST, request, String.class);
					
		String update_url = String.format(ApplicationTestConstants.URL, port);
		// When
		user.setId(id);
		user.setFirstName(ApplicationTestConstants.updated_first_name);
		ResponseEntity<String> response = testRestTemplate.exchange(update_url, HttpMethod.PUT, request, String.class);
		// Then
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().toString(), ApplicationAttributes.UPDATE_SUCCESS);
	}
	
	@Test
	public void test_5_should_return_200_for_remove_order_by_id() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		int id = user.getId();
		HttpEntity<User> request = HttpHelper.getHttpEntity(user);
		String url = String.format(ApplicationTestConstants.URL, port);
		testRestTemplate.exchange(url, HttpMethod.POST, request, String.class);
		String update_url = String.format(ApplicationTestConstants.URL.concat("/").concat(String.valueOf(id)), port);
		// When
		ResponseEntity<String> delet_response = testRestTemplate.exchange(update_url, HttpMethod.DELETE, request,
				String.class);
		// Then
		assertEquals(delet_response.getStatusCode(), HttpStatus.OK);
		assertNotNull(delet_response.getBody());
		assertEquals(delet_response.getBody().toString(), ApplicationAttributes.DELETE_SUCCESS);
	}
	
	@Test
	public void test_6_should_return_400_remove_order_by_negative_id() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		user.setId(-200);
		int id = user.getId();
		HttpEntity<User> request = HttpHelper.getHttpEntity(user);
		String url = String.format(ApplicationTestConstants.URL, port);
		testRestTemplate.exchange(url, HttpMethod.POST, request, String.class);
		String update_url = String.format(ApplicationTestConstants.URL.concat("/").concat(String.valueOf(id)), port);
		// When
		ResponseEntity<String> delet_response = testRestTemplate.exchange(update_url, HttpMethod.DELETE, request,
				String.class);
		// Then
		assertEquals(delet_response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertNotNull(delet_response.getBody().toString().indexOf(ErrorMessages.ERROR_ORDER_BAD_REQUESTD + id), ErrorMessages.ERROR_ORDER_BAD_REQUESTD + id);
	}

}
