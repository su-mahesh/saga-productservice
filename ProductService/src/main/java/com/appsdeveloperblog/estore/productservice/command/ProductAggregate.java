package com.appsdeveloperblog.estore.productservice.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.appsdeveloperblog.estore.core.commands.ReserveProductCommand;
import com.appsdeveloperblog.estore.core.events.ProductReservedEvent;
import com.appsdeveloperblog.estore.productservice.events.ProductCreatedEvent;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class ProductAggregate {

	@AggregateIdentifier
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;

	public ProductAggregate() {
	}

	@CommandHandler
	public ProductAggregate(CreateProductCommand createProductCommand) {
		// validate Create Product Command

		ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();

		BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

		AggregateLifecycle.apply(productCreatedEvent);
	}

	@CommandHandler
	public void handle(ReserveProductCommand reserveProductCommand) {

		if (quantity < reserveProductCommand.getQuantity()) {
			throw new IllegalArgumentException("insufficient items in stock ");
		}
		
		ProductReservedEvent productReservedEvent = 
				ProductReservedEvent.builder()
				.orderId(reserveProductCommand.getOrderId())
				.productId(reserveProductCommand.getProductId())
				.quantity(reserveProductCommand.getQuantity())
				.build();
		
		log.info("Command ReserveProductCommand for order id "+reserveProductCommand.getOrderId());
		AggregateLifecycle.apply(productReservedEvent);
	}

	@EventSourcingHandler
	public void on(ProductCreatedEvent productCreatedEvent) {
		this.productId = productCreatedEvent.getProductId();
		this.title = productCreatedEvent.getTitle();
		this.quantity = productCreatedEvent.getQuantity();
		this.price = productCreatedEvent.getPrice();
		log.info("EventSourcingHandler ProductCreatedEvent product id "+productCreatedEvent.getProductId());
	}
	
	@EventSourcingHandler
	public void on(ProductReservedEvent productReservedEvent) {
		
		this.quantity -= productReservedEvent.getQuantity();
		log.info("EventSourcingHandler ProductReservedEvent product id "+productReservedEvent.getOrderId());
		
	}
}
