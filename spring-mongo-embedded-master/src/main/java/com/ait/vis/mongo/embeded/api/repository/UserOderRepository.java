package com.ait.vis.mongo.embeded.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ait.vis.mongo.embeded.api.model.User;

@Repository
public interface UserOderRepository extends MongoRepository<User, Integer>{

	List<User> findByFirstName(String firstName);

	@Query("{'Address.city':?0}")
	List<User> findByCity(String city);
}
