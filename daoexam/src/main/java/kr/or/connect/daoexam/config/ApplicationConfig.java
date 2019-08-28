package kr.or.connect.daoexam.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = { "kr.or.connect.daoexam.dao" })
@Import ({DBConfig.class}) //Import 통해서 여러 개의 설정파일 나눠서 작성가능, DB관련 설정은 따로 관리

public class ApplicationConfig {
	
}
