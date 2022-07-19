package com.appsdeveloperblog.estore.productservice.data;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="products")
public class ProductEntity implements Serializable {

	private static final long serialVersionUID = -5055106262702148871L;
	@Id
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;
}
