package com.appsdeveloperblog.estore.productservice.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<ProductEntity, String> {

}
