package CyPirates.avere;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootTest
class AvereApplicationTests {

	@MockBean
	private UserDetailsService userDetailsService;

	@Test
	void contextLoads() {
	}

}
