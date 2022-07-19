package com.appsdeveloperblog.estore.productservice.command.rest;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.estore.productservice.command.CreateProductCommand;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final Environment env;
	private final CommandGateway commandGateway;

//	@Autowired
	public ProductController(Environment env, CommandGateway commandGateway) {
		this.env = env;
		this.commandGateway = commandGateway;
	}

	@PostMapping
	public String getProducts(@RequestBody CreateProductRestModel createProductRestModel) {
		
		String productId = UUID.randomUUID().toString();
		
		CreateProductCommand createProductCommand = CreateProductCommand.builder()
				.price(createProductRestModel.getPrice())
				.quantity(createProductRestModel.getQuantity())
				.title(createProductRestModel.getTitle())
				.productId(productId).build();

		String returnValue = commandGateway.sendAndWait(createProductCommand);
		return returnValue;
//		return "post method handled port: " + env.getProperty("local.server.port");
	}
}
