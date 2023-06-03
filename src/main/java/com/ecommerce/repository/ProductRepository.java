package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	/* Admin */
	@Query("select p from Product p")
	Page<Product> pageProduct(Pageable pageable);

	@Query("select p from Product p where p.description like %?1% or p.name like %?1%")
	Page<Product> searchProducts(String keyword, Pageable pageable);
	
	@Query("select p from Product p where p.description like %?1% or p.name like %?1%")
	List<Product> searchProductsList(String keyword);

}
