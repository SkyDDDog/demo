package com.lczyfz.demo.config;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    private static final String SPLITOR = ";";

    /**
     * 切割扫描的包生成Predicate<RequestHandler>
     *
     * @param basePackage
     * @return
     */
    public static Predicate<RequestHandler> scanBasePackage(final String basePackage) {
        if (StrUtil.isBlank(basePackage)) {
            throw new NullPointerException("basePackage不能为空，多个包扫描使用" + SPLITOR + "分隔");
        }
        String[] controllerPack = basePackage.split(SPLITOR);
        Predicate<RequestHandler> predicate = null;
        for (int i = controllerPack.length - 1; i >= 0; i--) {
            String strBasePackage = controllerPack[i];
            if (StrUtil.isNotBlank(strBasePackage)) {
                Predicate<RequestHandler> tempPredicate = RequestHandlerSelectors.basePackage(strBasePackage);
                predicate = predicate == null ? tempPredicate : Predicates.or(tempPredicate, predicate);
            }
        }
        if (predicate == null) {
            throw new NullPointerException("basePackage配置不正确，多个包扫描使用" + SPLITOR + "分隔");
        }
        return predicate;
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lczyfz"))//扫描的包路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Eep-Demo-SpringBoot")
                .description("Springboot基础开发工程swagger-api文档")
                .termsOfServiceUrl("https://www.jianshu.com/u/9ea571de7752")
                .version("1.0")
                .contact(new Contact("Maple", "https://www.jianshu.com/u/9ea571de7752", "cfqfzu@163.com"))
                .build();
    }
}
