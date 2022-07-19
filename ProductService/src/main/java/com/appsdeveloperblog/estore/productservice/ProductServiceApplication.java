package com.appsdeveloperblog.estore.productservice;

import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.appsdeveloperblog.estore.productservice.command.interceptors.CreateProductCommandInterceptor;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Autowired
	public void registerCreateProductCommandInterceptor(ApplicationContext context, CommandBus commandBus) {
		
		commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));		
	}
}
