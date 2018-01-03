package team.soth.favorisites.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.soth.favorisites.common.base.BaseInterface;

/**
 * 系统接口
 * Created by thinkam on 17-10-31.
 */
public class Initialize implements BaseInterface {

	private static Logger logger = LoggerFactory.getLogger(Initialize.class);

	@Override
	public void init() {
		logger.info(">>>>> 系统初始化");
	}

}
