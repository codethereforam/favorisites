package team.soth.favorisites.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.soth.favorisites.dao.model.User;
import team.soth.favorisites.dao.model.UserExample;
import team.soth.favorisites.service.UserService;

import java.util.List;

/**
 * 登录
 * Created by thinkam on 17-10-31.
 */
@RestController
public class LoginController {

	@Autowired
	private UserService userService;

	@GetMapping("users")
	public List<User> list() {
		return userService.selectByExample(new UserExample());
	}
}
