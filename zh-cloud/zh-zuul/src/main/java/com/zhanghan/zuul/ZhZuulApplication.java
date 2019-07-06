package com.zhanghan.zuul;

import com.zhanghan.zuul.filter.GrayZuulFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ZhZuulApplication {

	public static void main(String[] args) {

		SpringApplication.run(ZhZuulApplication.class, args);
	}

	@Bean
	public GrayZuulFilter myZuulFilter() {
		return new GrayZuulFilter();
	}


}
