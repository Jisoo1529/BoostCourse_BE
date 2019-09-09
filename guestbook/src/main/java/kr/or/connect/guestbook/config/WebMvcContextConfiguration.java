package kr.or.connect.guestbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import kr.or.connect.guestbook.interceptor.LogInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"kr.or.connect.guestbook.controller"})
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {//Java Config spring setting 위해서 필요. 요청이 들어왔을때 url요청에 따라 각각의 디렉토리에 들어가서 그에 맞춰서 사용함. 
		  registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(31556926);
	      registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
	      registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
	      registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
	}
	
	//DefaultServletHandler사용
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {//Controller는 없으나 spring이 내부적으로 사용함. 
		System.out.println("addViewControllers가 호출됩니다.");
		registry.addViewController("/").setViewName("index");
	}
	
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {//인터셉터 등록
				registry.addInterceptor(new LogInterceptor());
	}
}

