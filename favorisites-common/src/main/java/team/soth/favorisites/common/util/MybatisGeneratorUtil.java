package team.soth.favorisites.common.util;

import org.apache.commons.lang.ObjectUtils;
import org.apache.velocity.VelocityContext;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static team.soth.favorisites.common.util.StringUtil.lineToHump;

/**
 * Created by thinkam on 17-10-31.
 */
public class MybatisGeneratorUtil {
	/**
	 * generatorConfig模板路径
	 */
	private static String generatorConfig_vm = "/template/generatorConfig.vm";
	/**
	 * Service模板路径
 	 */
	private static String service_vm = "/template/Service.vm";
	/**
	 * ServiceImpl模板路径
 	 */
	private static String serviceImpl_vm = "/template/ServiceImpl.vm";

	private static final String PROJECT_NAME = "favorisites";

	/**
	 * 根据模板生成generatorConfig.xml文件
	 * @param jdbc_driver   驱动路径
	 * @param jdbc_url      链接
	 * @param jdbc_username 帐号
	 * @param jdbc_password 密码
	 * @param database      数据库
	 * @param package_name  包名
	 */
	public static void generator(
			String jdbc_driver,
			String jdbc_url,
			String jdbc_username,
			String jdbc_password,
			String database,
			String package_name,
			Map<String, String> last_insert_id_tables) throws Exception {

		generatorConfig_vm = MybatisGeneratorUtil.class.getResource(generatorConfig_vm).getPath();
		System.out.println(generatorConfig_vm);
		service_vm = MybatisGeneratorUtil.class.getResource(service_vm).getPath().replaceFirst("/", "");
		serviceImpl_vm = MybatisGeneratorUtil.class.getResource(serviceImpl_vm).getPath().replaceFirst("/", "");

		String targetProject = PROJECT_NAME + "-dao";
		System.out.println(MybatisGeneratorUtil.class.getResource("/").getPath());
		String basePath = MybatisGeneratorUtil.class.getResource("/").getPath().replace("/target/classes/", "").replace(targetProject, "");
		System.out.println("basePath=" + basePath);
		String generatorConfig_xml = MybatisGeneratorUtil.class.getResource("/").getPath().replace("/target/classes/", "") + "/src/main/resources/generatorConfig.xml";
		System.out.println(generatorConfig_xml);
		targetProject = basePath + targetProject;
		System.out.println("targetproject=" + targetProject);
		String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "';";

		System.out.println("========== 开始生成generatorConfig.xml文件 ==========");
		List<Map<String, Object>> tables = new ArrayList<>();
		try {
			VelocityContext context = new VelocityContext();
			Map<String, Object> table;

			// 查询定制前缀项目的所有表
			JdbcUtil jdbcUtil = new JdbcUtil(jdbc_driver, jdbc_url, jdbc_username, jdbc_password);
			List<Map> result = jdbcUtil.selectByParams(sql, null);
			for (Map map : result) {
				System.out.println(map.get("TABLE_NAME"));
				table = new HashMap<>();
				table.put("table_name", map.get("TABLE_NAME"));
				table.put("model_name", lineToHump(ObjectUtils.toString(map.get("TABLE_NAME"))));
				tables.add(table);
			}
			jdbcUtil.release();

			String targetProject_sqlMap = basePath + PROJECT_NAME + "-service";
			context.put("tables", tables);
			context.put("generator_javaModelGenerator_targetPackage", package_name + ".dao.model");
			context.put("generator_sqlMapGenerator_targetPackage", package_name + ".dao.mapper");
			context.put("generator_javaClientGenerator_targetPackage", package_name + ".dao.mapper");
			context.put("targetProject", targetProject);
			context.put("targetProject_sqlMap", targetProject_sqlMap);
			context.put("last_insert_id_tables", last_insert_id_tables);
			VelocityUtil.generate(generatorConfig_vm, generatorConfig_xml, context);
			// 删除dao模块旧代码
//			deleteDir(new File(targetProject + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/dao/model"));
//			deleteDir(new File(targetProject + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/dao/mapper"));
//			deleteDir(new File(targetProject_sqlMap + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/dao/mapper"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("========== 结束生成generatorConfig.xml文件 ==========");

		System.out.println("========== 开始运行MybatisGenerator ==========");
		List<String> warnings = new ArrayList<>();
		File configFile = new File(generatorConfig_xml);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		//设置不覆盖之前以前的代码
		DefaultShellCallback callback = new DefaultShellCallback(false);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
		for (String warning : warnings) {
			System.out.println(warning);
		}
		System.out.println("========== 结束运行MybatisGenerator ==========");

		System.out.println("========== 开始生成Service ==========");
		String ctime = new SimpleDateFormat("yy-MM-dd").format(new Date());
		String servicePath = basePath + PROJECT_NAME + "-service" + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/service";
		String serviceImplPath = basePath + PROJECT_NAME + "-service" + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/service/impl";
		for (int i = 0; i < tables.size(); i++) {
			String model = lineToHump(ObjectUtils.toString(tables.get(i).get("table_name")));
			String service = servicePath + "/" + model + "Service.java";
			String serviceImpl = serviceImplPath + "/" + model + "ServiceImpl.java";
			// 生成service
			File serviceFile = new File(service);
			if (!serviceFile.exists()) {
				VelocityContext context = new VelocityContext();
				context.put("package_name", package_name);
				context.put("model", model);
				context.put("ctime", ctime);
				VelocityUtil.generate(service_vm, service, context);
				System.out.println(service);
			}
			// 生成serviceImpl
			File serviceImplFile = new File(serviceImpl);
			if (!serviceImplFile.exists()) {
				VelocityContext context = new VelocityContext();
				context.put("package_name", package_name);
				context.put("model", model);
				context.put("mapper", StringUtil.toLowerCaseFirstOne(model));
				context.put("ctime", ctime);
				VelocityUtil.generate(serviceImpl_vm, serviceImpl, context);
				System.out.println(serviceImpl);
			}
		}
		System.out.println("========== 结束生成Service ==========");
	}

	// 递归删除非空文件夹
	public static void deleteDir(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				deleteDir(file);
			}
		}
		dir.delete();
	}

}
