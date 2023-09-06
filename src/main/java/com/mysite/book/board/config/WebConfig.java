package com.mysite.book.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  implements WebMvcConfigurer{
	private String resourePath = "/upload/**";		// view에서 접근할 경로
	private String savePath = "file:///C:/springboot_img";		// 실제 파일 저장경로
	
	@Override
	public void addResourceHandlers (ResourceHandlerRegistry registry) {
		registry.addResourceHandler(resourePath)
						.addResourceLocations(savePath);
	}

}
