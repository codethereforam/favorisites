package team.soth.favorisites.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 * Created by thinkam on 17-11-2.
 */
@Component
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo());
	}

	/**
	 * api接口作者相关信息
	 */
	private ApiInfo apiInfo() {
		Contact contact = new Contact("thinkam", "https://github.com/codethereforam", "1203948298@qq.com");
		ApiInfo apiInfo = new ApiInfoBuilder()
				.title("favorisites")
				.description("前台restful接口文档")
				.contact(contact)
				.version("1.0")
				.build();
		return apiInfo;
	}
}
