package com.ecommerce.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.utilts.ImageUpload;

@Service
public class ProductService {
	 @Autowired
	    private ProductRepository productRepository;

	    @Autowired
	    private ImageUpload imageUpload;

	    /*Admin*/
	    
	    public List<ProductDto> findAll() {
	        List<Product> products = productRepository.findAll();
	        List<ProductDto> productDtoList = transfer(products);
	        return productDtoList;
	    }

	    
	    public Product save(MultipartFile imageProduct, ProductDto productDto) {
	        try {
	            Product product = new Product();
	            if(imageProduct == null){
	                product.setImage(null);
	            }else{
	                if(imageUpload.uploadImage(imageProduct)){
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
	        }catch (Exception e){
	            e.printStackTrace();
	            return null;
	        }

	    }

	    
	    public Product update(MultipartFile imageProduct ,ProductDto productDto) {
	        try {
	            Product product = productRepository.getById(productDto.getId());
	            if(imageProduct == null){
	                product.setImage(product.getImage());
	            }else{
	                if(imageUpload.checkExisted(imageProduct) == false){
	                    imageUpload.uploadImage(imageProduct);
	                }
	                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
	            }
	            product.setName(productDto.getName());
	            product.setDescription(productDto.getDescription());
	            product.setSalePrice(productDto.getSalePrice());
	            product.setCostPrice(productDto.getCostPrice());
	            product.setCurrentQuantity(productDto.getCurrentQuantity());
	            product.setCategory(productDto.getCategory());
	            return productRepository.save(product);
	        }catch (Exception e){
	            e.printStackTrace();
	            return null;
	        }

	    }

	
	    public void deleteById(Long id) {
	        Product product = productRepository.getById(id);
	        product.set_deleted(true);
	        product.set_activated(false);
	        productRepository.save(product);
	    }


	    public void enableById(Long id) {
	        Product product = productRepository.getById(id);
	        product.set_activated(true);
	        product.set_deleted(false);
	        productRepository.save(product);
	    }

	    
	    public ProductDto getById(Long id) {
	        Product product = productRepository.getById(id);
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
	        return productDto;
	    }

	   
	    public Page<ProductDto> pageProducts(int pageNo) {
	        Pageable pageable = PageRequest.of(pageNo, 5);
	        List<ProductDto> products = transfer(productRepository.findAll());
	        Page<ProductDto> productPages = toPage(products, pageable);
	        return productPages;
	    }

	    
	    public Page<ProductDto> searchProducts(int pageNo, String keyword) {
	        Pageable pageable = PageRequest.of(pageNo, 5);
	        List<ProductDto> productDtoList = transfer(productRepository.searchProductsList(keyword));
	        Page<ProductDto> products = toPage(productDtoList, pageable);
	        return products;
	    }



	    private Page toPage(List<ProductDto> list , Pageable pageable){
	        if(pageable.getOffset() >= list.size()){
	            return Page.empty();
	        }
	        int startIndex = (int) pageable.getOffset();
	        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
	                ? list.size()
	                : (int) (pageable.getOffset() + pageable.getPageSize());
	        List subList = list.subList(startIndex, endIndex);
	        return new PageImpl(subList, pageable, list.size());
	    }


	    private List<ProductDto> transfer(List<Product> products){
	        List<ProductDto> productDtoList = new ArrayList<>();
	        for(Product product : products){
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

}