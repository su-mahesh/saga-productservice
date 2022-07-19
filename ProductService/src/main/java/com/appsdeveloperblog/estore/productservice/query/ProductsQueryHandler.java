package com.appsdeveloperblog.estore.productservice.query;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.productservice.data.ProductEntity;
import com.appsdeveloperblog.estore.productservice.data.ProductsRepository;
import com.appsdeveloperblog.estore.productservice.query.rest.ProductRestModel;

@Component
public class ProductsQueryHandler {

	private ProductsRepository productsRepository;

	public ProductsQueryHandler(ProductsRepository productsRepository) {
		this.productsRepository = productsRepository;
	}

	@QueryHandler
	public List<ProductRestModel> findProducts(FindProductsQuery findProductsQuery) {
		List<ProductRestModel> productsRest = new ArrayList<>();
		List<ProductEntity> storedProducts = productsRepository.findAll();

		storedProducts.forEach(c -> {
			ProductRestModel productRestModel = new ProductRestModel();
			BeanUtils.copyProperties(c, productRestModel);
			productsRest.add(productRestModel);
		});
		return productsRest;
	}
}
