package realtalk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;
import realtalk.model.User;
import realtalk.service.UserService;
import realtalk.service.exception.UserLoginExistsException;

import java.util.Date;

@SpringBootTest
@WebAppConfiguration
class RealtalkApplicationTests {
	@Autowired
	UserService userService;

	@Test
	void contextLoads() {
	}
	@Test
	void simpleCalculate(){
		Assertions.assertEquals(2, 1 + 1);
	}

	@Test
	void loginException(){
		User kiraTep = userService.findUserByLogin("kiraTep");
		if(kiraTep==null){
			userService.registration("kiraTep", "test",
					"Кирилл", "Тепечин", null, new Date(),"Ульяновск");
		}
		Assertions.assertThrows(UserLoginExistsException.class,
				() -> userService.registration("kiraTep", "test",
				"Кирилл", "Тепечин", null, new Date(),"Ульяновск"));
	}
}
