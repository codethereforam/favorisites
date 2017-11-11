package team.soth.favorisites.web.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.soth.favorisites.common.util.MD5Util;
import team.soth.favorisites.dao.model.User;
import team.soth.favorisites.dao.model.UserExample;
import team.soth.favorisites.service.UserService;

import java.util.List;

/**
 * 用户认证
 * Created by thinkam on 17-11-9.
 */
@Component
public class UserAuthenticatingRealm extends AuthenticatingRealm {
	private static Logger logger = LoggerFactory.getLogger(UserAuthenticatingRealm.class);

	@Autowired
	private UserService userService;

	/**
	 * 认证：登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		String accountName = (String) authenticationToken.getPrincipal();
		String password = new String((char[]) authenticationToken.getCredentials());
		// 查询用户信息
		UserExample userExample = new UserExample();
		UserExample.Criteria criteria1 = userExample.createCriteria();
		criteria1.andUsernameEqualTo(accountName);
		UserExample.Criteria criteria2 = userExample.createCriteria();
		criteria2.andEmailEqualTo(accountName);
		userExample.or(criteria2);
		List<User> users = userService.selectByExample(userExample);
		User user = users.size() == 0 ? null : users.get(0);
		//判断并得出结果
		if (user == null) {
			throw new UnknownAccountException();
		}
		if (!user.getPassword().equals(MD5Util.MD5(password + user.getSalt()))) {
			throw new IncorrectCredentialsException();
		}
		if (user.getLocked() == 1) {
			throw new LockedAccountException();
		}
		return new SimpleAuthenticationInfo(accountName, password, getName());
	}

}
