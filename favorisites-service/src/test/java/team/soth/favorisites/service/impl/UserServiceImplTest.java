package team.soth.favorisites.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import team.soth.favorisites.dao.model.UserExample;
import team.soth.favorisites.service.UserService;

import javax.annotation.Resource;

/**
 * Created by thinkam on 17-10-31.
 */
/*@RunWith(SpringRunner.class)
@ContextConfiguration({
		"classpath:META-INF/spring/applicationContext-jdbc.xml",
		"classpath:META-INF/spring/applicationContext.xml",
		"classpath:META-INF/spring/applicationContext-listener.xml"
})
@Transactional(transactionManager = "transactionManager")
public class UserServiceImplTest {
	@Resource
	private UserService userService;

	@Test
	public void testSelectByExample() {
		userService.selectByExample(new UserExample()).forEach(System.out::println);
	}
}*/
