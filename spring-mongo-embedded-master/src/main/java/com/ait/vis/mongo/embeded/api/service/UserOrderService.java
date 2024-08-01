package com.ait.vis.mongo.embeded.api.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.vis.mongo.embeded.api.exception.ErrorMessages;
import com.ait.vis.mongo.embeded.api.exception.ResourceAlreadyExistsException;
import com.ait.vis.mongo.embeded.api.exception.ResourceNotFoundException;
import com.ait.vis.mongo.embeded.api.model.Address;
import com.ait.vis.mongo.embeded.api.model.Product;
import com.ait.vis.mongo.embeded.api.model.User;
import com.ait.vis.mongo.embeded.api.repository.UserOderRepository;

@Service
public class UserOrderService {
	@Autowired
	private UserOderRepository repository;

	public void save(User user) {
		StringBuilder rules = inputValidation(user);
		if (rules.length() == 0) {
			if (findById(user.getId()).isPresent()) {
				throw new ResourceAlreadyExistsException(ErrorMessages.ERROR_ORDER_ALREADY_FOUND + user.getId());
			} else {
				repository.save(user);
			}
		} else {
			throw new IllegalArgumentException(rules.toString());
		}
	}

	public List<User> findByName(String name) {
		if (name == null || name.trim().length() < 2 || name.split("\\s").length > 1) {
			throw new IllegalArgumentException(ErrorMessages.USER_NAEME_ERROR);
		}
		List<User> list = repository.findByFirstName(name);
		if (list == null || list.size() == 0) {
			throw new ResourceNotFoundException(ErrorMessages.RESOURCE_NOT_FOUND + name);
		}
		return list;
	}

	public List<User> findByCity(String city) {
		if (city == null || city.trim().length() < 2 || city.split("\\s").length > 1) {
			throw new IllegalArgumentException(ErrorMessages.CITY_NAME_ERROR);
		}
		List<User> list = repository.findByCity(city);
		if (list == null || list.size() == 0) {
			throw new ResourceNotFoundException(ErrorMessages.RESOURCE_NOT_FOUND + city);
		}
		return list;
	}

	public User updatOrder(User user) {
		StringBuilder rules = inputValidation(user);
		if (rules.length() == 0) {
			if (findById(user.getId()).isPresent()) {
				return repository.save(user);
			} else {
				throw new ResourceNotFoundException(ErrorMessages.ERROR_ORDER_NOT_FOUND + user.getId());
			}
		} else {
			throw new IllegalArgumentException(rules.toString());
		}
	}

	public void deletUserOrder(int id) {
		if (findById(id).isPresent()) {
			repository.deleteById(id);
		} else {
			throw new IllegalArgumentException(ErrorMessages.ERROR_ORDER_BAD_REQUESTD + id);
		}
	}

	private Optional<User> findById(int userId) {
		return repository.findById(userId);
	}

	private StringBuilder inputValidation(User user) {
		StringBuilder value = new StringBuilder();
		if (user != null) {
			if (user.getId() <= 0) {
				value.append(ErrorMessages.USER_ID_ERROR);
			}
			if (user.getFirstName() == null || user.getFirstName().trim().length() < 2
					|| user.getFirstName().split("\\s").length > 1) {
				value.append(ErrorMessages.USER_NAEME_ERROR);
			}
			Address address = user.getAddress();
			if (address == null || address.getCity() == null || address.getCity().trim().length() < 2
					|| address.getCity().split("\\s").length > 1) {
				value.append(ErrorMessages.USER_ADDRESS_ERROR);
			}
			List<Product> products = user.getProducts();
			if (products == null || products.size()==0) {
				value.append(ErrorMessages.PRODUCT_INPUT_ERROR);
			} else {
				for (Product product : products) {
					if (product.getName() == null || product.getName().trim().length() < 2) {
						value.append(ErrorMessages.PRODUCT_NAME_ERROR);
						break;
					}
				}
			}
		}
		return value;
	}
}
