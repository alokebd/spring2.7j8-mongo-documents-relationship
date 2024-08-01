package com.ait.vis.mongo.embeded.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.vis.mongo.embeded.api.config.ApplicationAttributes;
import com.ait.vis.mongo.embeded.api.model.User;
import com.ait.vis.mongo.embeded.api.service.UserOrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class UserOrderController {
	
	@Autowired
	private UserOrderService userOrderService;
	
	@ApiOperation(value = "SavedUserOrderAPI", notes = "CRUD Operations - placeOrder Event [The user request body is required", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully Saved User", response = String.class),
			@ApiResponse(code = 400, message = "Invalid Request", response = String.class),
			@ApiResponse(code = 409, message = "Resource Exists", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	@PostMapping("/orders")
	public ResponseEntity<Object> placeOrder(@RequestBody User user) {
		userOrderService.save(user);
		return new ResponseEntity<Object>(ApplicationAttributes.SAVED_SUCCESS, HttpStatus.OK);
	}
	
	@ApiOperation(value = "GetUserOrderByUserFirstNameAPI", notes = "Get Operations - getUserbyName Event [the name path variable is required, it must be at least 3 characters and it must be one word.]", response = User.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully Saved User", response = User.class),
			@ApiResponse(code = 400, message = "Invalid Request", response = User.class),
			@ApiResponse(code = 404, message = "Not Found", response = User.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = User.class) })
	@GetMapping("/orders/{name}")
	public ResponseEntity<Object> getUserbyName(@PathVariable String name) {
		 List<User> list= userOrderService.findByName(name);
		 return new ResponseEntity<Object>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "GetUserOrderByCityAPI", notes = "Get Operations - getUserbyAddress Event [The city path variable must be at least 3 characters and must be one word.]", response = User.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully Saved User", response = User.class),
			@ApiResponse(code = 400, message = "Invalid Request", response = User.class),
			@ApiResponse(code = 404, message = "Not Found", response = User.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = User.class) })
	@GetMapping("/orders/addresses/{city}")
	public ResponseEntity<Object> getUserbyAddress(@PathVariable String city) {
		 List<User> list= userOrderService.findByCity(city);
		 return new ResponseEntity<Object>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "UpdatOrderByUserAPI", notes = "CRUD Operations -updateOrder Event [The user request body is required]", response = String.class)
	@ApiResponses({ @ApiResponse(code = 201, message = "Successfully Fetched All Employees", response = String.class),
			@ApiResponse(code = 400, message = "Invalid Request", response = String.class),
			@ApiResponse(code = 404, message = "Resource Not Found", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	@PutMapping(value = "/orders")
	public ResponseEntity<Object> updatOrder(@RequestBody User user) {
		userOrderService.updatOrder(user);
		return new ResponseEntity<Object>(ApplicationAttributes.UPDATE_SUCCESS, HttpStatus.OK);
	}
	
	@ApiOperation(value = "DeleteUserOrderByIdAPI", notes = "CRUD Operations -deleteUserOrder Event [id path variable must be numeric and between 1 and max integer]", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully Deleted Employee", response = String.class),
			@ApiResponse(code = 400, message = "Invalid Request", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	@DeleteMapping(value = "/orders/{id}")
	public ResponseEntity<Object> deleteUserOrder(@PathVariable int id) {
		userOrderService.deletUserOrder(id);
		return new ResponseEntity<Object>(ApplicationAttributes.DELETE_SUCCESS, HttpStatus.OK);
	}

}
