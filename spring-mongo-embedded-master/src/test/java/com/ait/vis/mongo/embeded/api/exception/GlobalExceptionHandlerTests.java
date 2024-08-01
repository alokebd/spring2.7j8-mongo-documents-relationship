package com.ait.vis.mongo.embeded.api.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ait.vis.mongo.embeded.api.model.User;
import com.ait.vis.mongo.embeded.api.service.UserOrderService;
import com.ait.vis.mongo.embeded.api.testutils.ApplicationTestConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@SpringBootTest
public class GlobalExceptionHandlerTests {
	@InjectMocks
	private GlobalExceptionHandler globalExceptionHandler;
	@Autowired
	private UserOrderService userOrderService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void test_1_HandleResourceNotFoundException() {
		ResourceNotFoundException ex = new ResourceNotFoundException(ErrorMessages.ERROR_ORDER_NOT_FOUND + 1000);
		// expected
		ResponseEntity<ErrorResponse> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));

		// Actual response
		ResponseEntity<ErrorResponse> actualResponse = globalExceptionHandler.handleResourceNotFoundException(ex);

		// assert the response
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());

		ErrorResponse expectedResultBody = expectedResponse.getBody();
		ErrorResponse actualResultBody = actualResponse.getBody();

		if (expectedResultBody != null && actualResultBody != null) {
			assertEquals(expectedResultBody.getMessage(), actualResultBody.getMessage());
		}
	}

	@Test
	public void test_2_HandleResourceExistsException() {
		ResourceAlreadyExistsException ex = new ResourceAlreadyExistsException(
				ErrorMessages.ERROR_ORDER_ALREADY_FOUND + 123);
		// expected response
		ResponseEntity<ErrorResponse> expectedResponse = ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage()));

		// actual
		ResponseEntity<ErrorResponse> actualResponse = globalExceptionHandler.handleResourceAlreadyExistsException(ex);
		// assert the response
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		ErrorResponse expectedResultBody = expectedResponse.getBody();
		ErrorResponse actualResultBody = actualResponse.getBody();

		if (expectedResultBody != null && actualResultBody != null) {
			assertEquals(expectedResultBody.getMessage(), actualResultBody.getMessage());
		}
	}

	@Test
	public void test_3_IllegalArgumentException() {
		User user = ApplicationTestConstants.givenUserDocument();
		// When
		user.setFirstName("");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			userOrderService.save(user);
		});

		ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleIllegalArgumentException(exception);
		ErrorResponse actualResultBody = responseEntity.getBody();

		if (actualResultBody != null) {
			assertEquals(ErrorMessages.USER_NAEME_ERROR, responseEntity.getBody().getMessage());
		}
	}

	@Test
	public void test_4_MethodArgumentTypeMismatchException() {
		MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
		when(ex.getMessage()).thenReturn("Given request param is not supported");
		// Call the method and assert the response
		ResponseEntity<ErrorResponse> actualResult = globalExceptionHandler
				.handleMethodArgumentTypeMismatchException(ex);
		assertEquals(HttpStatus.METHOD_NOT_ALLOWED, actualResult.getStatusCode());
		ErrorResponse actualResultBody = actualResult.getBody();
		if (actualResultBody != null) {
			assertEquals("Given request param is not supported", actualResult.getBody().getMessage());
		}
	}
	
	@Test
	public void test_5_HttpRequestMethodNotSupportedException() {
		HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("Method Not Allowed");
		// expected
		ResponseEntity<ErrorResponse> expectedResponse = ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage()));

		// Actual response
		ResponseEntity<ErrorResponse> actualResponse = globalExceptionHandler.handleRequestMethodNotSupportedException(ex);

		// assert the response
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());

		ErrorResponse expectedResultBody = expectedResponse.getBody();
		ErrorResponse actualResultBody = actualResponse.getBody();

		if (expectedResultBody != null && actualResultBody != null) {
			assertEquals(expectedResultBody.getMessage(), actualResultBody.getMessage());
		}
	}
	
	@Test
	public void test_6_HttpMediaTypeNotSupportedException() {
		HttpMediaTypeNotSupportedException ex = new HttpMediaTypeNotSupportedException("Unsupported Media Type");
		
		// expected
		ResponseEntity<ErrorResponse> expectedResponse = ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
				.body(new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), ex.getMessage()));

		// Actual response
		ResponseEntity<ErrorResponse> actualResponse = globalExceptionHandler.handleUnsupportedMediaTypeException(ex);

		// assert the response
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());

		ErrorResponse expectedResultBody = expectedResponse.getBody();
		ErrorResponse actualResultBody = actualResponse.getBody();

		if (expectedResultBody != null && actualResultBody != null) {
			assertEquals(expectedResultBody.getMessage(), actualResultBody.getMessage());
		}
	}

}
