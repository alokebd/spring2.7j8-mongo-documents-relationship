package com.ait.vis.mongo.embeded.api.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import com.ait.vis.mongo.embeded.api.exception.ResourceNotFoundException;
import com.ait.vis.mongo.embeded.api.model.User;
import com.ait.vis.mongo.embeded.api.repository.UserOderRepository;
import com.ait.vis.mongo.embeded.api.testutils.ApplicationTestConstants;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserOrderServiceTests {
	@Mock
	private UserOderRepository repository;
	@InjectMocks
	private UserOrderService service;

	@Test
	public void test_1_should_save() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		// When
		service.save(user);
		// Then
		verify(repository, times(1)).save(user);
	}

	@Test
	public void test_2_should_throws_IllegalArgumentException_on_save() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		// When
		user.setFirstName("");
		// doThrow(IllegalArgumentException.class).when(repository).save(user);
		// Then
		assertThatThrownBy(() -> service.save(user)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void test_3_should_throws_IllegalArgumentException_on_findByName_when_name_not_valid() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		// When
		user.setFirstName("");
		// doThrow(IllegalArgumentException.class).when(repository).save(user);
		// Then
		assertThatThrownBy(() -> service.findByName(user.getFirstName())).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void test_4_should_find_user_by_name() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		user.setFirstName("dammy");
		when(repository.findByFirstName(user.getFirstName())).thenReturn(Collections.singletonList(user));
		// Then
		List<User> serviceListDto = service.findByName(user.getFirstName());
		assertThat(serviceListDto).isNotNull();
		assertThat(serviceListDto.size()).isEqualTo(1);
	}

	@Test
	public void test_5_should_find_user_by_city() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		user.getAddress().setCity("dummay2");
		when(repository.findByCity(user.getAddress().getCity())).thenReturn(Collections.singletonList(user));
		// Then
		List<User> serviceListDto = service.findByCity(user.getAddress().getCity());
		assertThat(serviceListDto).isNotNull();
		assertThat(serviceListDto.size()).isEqualTo(1);
	}

	@Test
	public void test_6_should_throws_IllegalArgumentException_on_findByCity_city_not_valid() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		// When
		user.getAddress().setCity("dummay2 dummay");
		// Then
		assertThatThrownBy(() -> service.findByCity(user.getAddress().getCity()))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void test_7_should_throw_ResourceNotFoundException_when_update() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		user.setFirstName(ApplicationTestConstants.updated_first_name);
		// When
		repository.save(user);
		// Then
		// verify(repository, times(1)).save(user);
		assertThatThrownBy(() -> service.updatOrder(user)).isInstanceOf(ResourceNotFoundException.class);
	}
	
	/*
	
	@Test
	public void test_8_should_update() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		   //When
		when(repository.save(user)).thenReturn(user);
		//then
		user.setId(user.getId());
		user.setFirstName(ApplicationTestConstants.updated_first_name);
	 
	    assertEquals(user, service.updatOrder(user));
	}*/


}
