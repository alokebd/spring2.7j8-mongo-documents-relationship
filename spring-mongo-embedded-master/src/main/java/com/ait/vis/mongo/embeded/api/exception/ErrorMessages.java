package com.ait.vis.mongo.embeded.api.exception;

public class ErrorMessages {
	public static final String ERROR_ORDER_NOT_FOUND = "Order not exist with id:";
	public static final String ERROR_ORDER_ALREADY_FOUND = "Order already exist with register id:";
	public static final String ERROR_ORDER_BAD_REQUESTD = "Order not exist with id:";
	public static final String USER_ID_ERROR = "id must be numeric and between 1 and max integer";
	public static final String USER_NAEME_ERROR = "User firstName is required &  it must be at least 3 characters. The First name must be one word. ";
	public static final String USER_ADDRESS_ERROR = "User address is requried and the city of the address must be at least 3 characters. The city name must be one word. ";
	public static final String PRODUCT_INPUT_ERROR = "At least one product is required to place order ";
	public static final String PRODUCT_NAME_ERROR = "Product name must not be empty and it must be at least 3 characters. ";
	public static final String CITY_NAME_ERROR = "The city of the address must be at least 3 characters. The city name must be one word. ";
	public static final String RESOURCE_NOT_FOUND = "No collection found in resource for supplied :";
}
