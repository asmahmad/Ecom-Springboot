package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> findAll(){
		
		return categoryRepository.findAll();
		
	}
	
	public Category save(Category category) {
		
		try {
			
			Category categorySave = new Category(category.getName());
			return categoryRepository.save(categorySave);
		
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Optional<Category> findById(Long id) {
		
		return categoryRepository.findById(id);
	}
    public Category update(Category category) {
        Category categoryUpdate = null;
        try {
            categoryUpdate= categoryRepository.findById(category.getId()).get();
            categoryUpdate.setName(category.getName());
            categoryUpdate.set_activated(category.is_activated());
            categoryUpdate.set_deleted(category.is_deleted());
        }catch (Exception e){
            e.printStackTrace();
        }
        return categoryRepository.save(categoryUpdate);
    }
	public void deleteById(Long id) {
		Category category = categoryRepository.findById(id).get();
		category.set_deleted(true);
		category.set_activated(false);
		categoryRepository.save(category);
		categoryRepository.deleteById(id);
	
	}
	public void enabledById(Long id) {
		Category category = categoryRepository.findById(id).get();
		category.set_activated(true);
		category.set_deleted(false);
		categoryRepository.save(category);
	}
	

}
