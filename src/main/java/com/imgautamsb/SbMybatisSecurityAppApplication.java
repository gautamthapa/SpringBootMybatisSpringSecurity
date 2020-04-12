package com.imgautamsb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.imgautamsb.db")
public class SbMybatisSecurityAppApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SbMybatisSecurityAppApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SbMybatisSecurityAppApplication.class);
	}

}
