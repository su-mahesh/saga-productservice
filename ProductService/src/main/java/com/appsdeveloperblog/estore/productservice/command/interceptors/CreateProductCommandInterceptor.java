package com.appsdeveloperblog.estore.productservice.command.interceptors;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.productservice.command.CreateProductCommand;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
			List<? extends CommandMessage<?>> messages) {
		// TODO Auto-generated method stub
		return (index, command) ->{
			if(CreateProductCommand.class.equals(command.getPayloadType()))
			{
				CreateProductCommand createProductCommand = 
						(CreateProductCommand) command.getPayload();
				
				if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0)
				{
					throw new IllegalArgumentException("price cannot be less than zero");
				}
			}
			return command;
		};
	}

	
}
