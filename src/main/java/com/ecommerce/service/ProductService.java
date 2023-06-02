package com.ecommerce.service;

import java.util.Base64;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.utilts.ImageUpload;
import com.nimbusds.jose.util.Base64;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ImageUpload imageUpload;

	public List<ProductDto> findAll() {
		List<Product> products = productRepository.findAll();
		List<ProductDto> productDtoList = transfer(products);
		return productDtoList;
	}
//	Product save(ProductDto productDto);
//	Product update(ProductDto productDto);
//	void deleteById(Long id);
//	void enableById(Long id);

	private List<ProductDto> transfer(List<Product> products) {
		List<ProductDto> productDtoList = new ArrayList<>();
		for (Product product : products) {
			ProductDto productDto = new ProductDto();
			productDto.setId(product.getId());
			productDto.setName(product.getName());
			productDto.setDescription(product.getDescription());
			productDto.setCurrentQuantity(product.getCurrentQuantity());
			productDto.setCategory(product.getCategory());
			productDto.setSalePrice(product.getSalePrice());
			productDto.setCostPrice(product.getCostPrice());
			productDto.setImage(product.getImage());
			productDto.setDeleted(product.is_deleted());
			productDto.setActivated(product.is_activated());
			productDtoList.add(productDto);
		}
		return productDtoList;
	}

	public Product save(MultipartFile imageProduct, ProductDto productDto) {
		try {
			Product product = new Product();
			if (imageProduct == null) {
				product.setImage(null);
			} else {
				if (imageUpload.uploadImage(imageProduct)) {
					System.out.println("Upload successfully");
				}
				product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
			}
			product.setName(productDto.getName());
			product.setDescription(productDto.getDescription());
			product.setCategory(productDto.getCategory());
			product.setCostPrice(productDto.getCostPrice());
			product.setCurrentQuantity(productDto.getCurrentQuantity());
			product.set_activated(true);
			product.set_deleted(false);
			return productRepository.save(product);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
