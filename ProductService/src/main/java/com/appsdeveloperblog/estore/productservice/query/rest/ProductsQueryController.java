package com.appsdeveloperblog.estore.productservice.query.rest;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.estore.productservice.query.FindProductsQuery;

@RestController
@RequestMapping("/products")
public class ProductsQueryController {

	@Autowired
	private QueryGateway queryGateway;

	@GetMapping
	public List<ProductRestModel> getProducts(){
		
		FindProductsQuery findProductsQuery = new FindProductsQuery();
		List<ProductRestModel> products = queryGateway.query(findProductsQuery,
				ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();
	return products;
	}
}
