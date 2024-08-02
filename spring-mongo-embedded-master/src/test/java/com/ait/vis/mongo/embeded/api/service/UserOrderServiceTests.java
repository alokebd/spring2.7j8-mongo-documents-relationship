package com.ait.vis.mongo.embeded.api.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;

import com.ait.vis.mongo.embeded.api.exception.ResourceAlreadyExistsException;
import com.ait.vis.mongo.embeded.api.exception.ResourceNotFoundException;
import com.ait.vis.mongo.embeded.api.model.Product;
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
		User user = ApplicationTestConstants.givenUserDocumentByValue("", "test", "test"); // passing name empty
		// Then
		assertThatThrownBy(() -> service.save(user)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void test_3_should_throws_IllegalArgumentException_on_findByName_when_name_not_valid() {
		// Given
		User user = ApplicationTestConstants.givenUserDocumentByValue("", "test", "test"); // passing name empty
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
		assertThatThrownBy(() -> service.updatOrder(user)).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	public void test_8_should_update_UserOrderService_Retun_User() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		// When
		when(repository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
		user.setFirstName(ApplicationTestConstants.updated_first_name);
		service.updatOrder(user);
		// Then
		assertAll(() -> service.updatOrder(user));
	}

	@Test
	public void test_9_UserOrderService_DeletUserOrder_Return_Void() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		// When
		// When findById is invoked with the specified id, it returns an Optional
		// containing the user.
		when(repository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
		// not required as it is unnecessary stabbing
		// doNothing().when(repository).delete(user);
		// Invoke the delete method of the UserOrderService with the created id.
		service.deletUserOrder(user.getId());
		// Then
		// Use assertAll to ensure that no exceptions are thrown during the execution of
		// the delete method.
		assertAll(() -> service.deletUserOrder(user.getId()));
	}

	@Test
	public void test_10_should_throws_IllegalArgumentException_on_update_when_product_name_not_valid() {
		// Given
		User user = ApplicationTestConstants.givenUserDocumentByValue("test", "", "test"); // passing product empty
		// When
		repository.save(user);
		// Then
		assertThatThrownBy(() -> service.updatOrder(user)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void test_11_should_throws_IllegalArgumentException_on_update_with_out_product() {
		// Given
		User user = ApplicationTestConstants.givenUserDocumentWithoutProduct();
		// When
		repository.save(user);
		// Then
		assertThatThrownBy(() -> service.updatOrder(user)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void test_12_should_throw_IllegalArgumentException_when_Id_zero_for_delete() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		user.setId(0);
		// When
		repository.delete(user);
		// Then
		assertThatThrownBy(() -> service.deletUserOrder(user.getId())).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void test_13_should_throws_ResourceAlreadyExistsException_on_save() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		when(repository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
		// Then
		assertThatThrownBy(() -> service.save(user)).isInstanceOf(ResourceAlreadyExistsException.class);
	}

	@Test
	public void test_14_should_throws_IllegalArgumentException_on_update_with_out_address() {
		// Given
		User user = ApplicationTestConstants.givenUserDocumentWithoutAddress();
		// When
		repository.save(user);
		// Then
		assertThatThrownBy(() -> service.updatOrder(user)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void test_15_should_throws_ResourceNotFoundException_on_find_by_city() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		// When
		when(repository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
		service.deletUserOrder(user.getId());
		// Then
		assertThatThrownBy(() -> service.findByCity(user.getAddress().getCity()))
				.isInstanceOf(ResourceNotFoundException.class);

	}

	@Test
	public void test_16_should_throws_ResourceNotFoundException_on_find_by_name() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		// When
		when(repository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
		service.deletUserOrder(user.getId());
		// Then
		assertThatThrownBy(() -> service.findByName(user.getFirstName())).isInstanceOf(ResourceNotFoundException.class);

	}
	
	@Test
	public void test_17_should_throw_IllegalArgumentException_when_Id_negative_integer_for_delete() {
		// Given
		User user = ApplicationTestConstants.givenUserDocument();
		user.setId(-200);
		// When
		repository.delete(user);
		// Then
		assertThatThrownBy(() -> service.deletUserOrder(user.getId())).isInstanceOf(IllegalArgumentException.class);
	}
}
