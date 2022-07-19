package com.appsdeveloperblog.estore.productservice.query;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.core.events.ProductReservedEvent;
import com.appsdeveloperblog.estore.productservice.command.ProductAggregate;
import com.appsdeveloperblog.estore.productservice.data.ProductEntity;
import com.appsdeveloperblog.estore.productservice.data.ProductsRepository;
import com.appsdeveloperblog.estore.productservice.events.ProductCreatedEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductEventsHandler {

	private final ProductsRepository productsRepository;
	
	//@Autowired
	public ProductEventsHandler(ProductsRepository productsRepository) {
		super();
		this.productsRepository = productsRepository;
	}
	
	@EventHandler
	public void on(ProductCreatedEvent event) {
		
		ProductEntity product = new ProductEntity();
		
		BeanUtils.copyProperties(event, product);
		
		productsRepository.save(product);
		
	}
	
	@EventHandler
	public void on(ProductReservedEvent productReservedEvent) {
		
		ProductEntity productEntity = productsRepository.findById(productReservedEvent.getProductId()).get();

		productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());
		
		productsRepository.save(productEntity);
		log.info("EventHandler ProductReservedEvent order id "+productReservedEvent.getOrderId());
	}
	
}
