package com.ait.vis.mongo.embeded.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;
import com.ait.vis.mongo.embeded.api.controller.UserOrderController;

@SpringBootTest
public class SpringMongoEmbededApplicationTests {

	@Autowired
	private UserOrderController userOrderController;
	
	
	@Test
	void contextLoads() {
		Assertions.assertThat(userOrderController).isNotNull();
	}
	
	@Test
	public void applicationStarts() {
		SpringMongoEmbededApplication.main(new String[] {});
	 }

}
