/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.mapper;

import java.util.Optional;
import main.dto.CategoryRequest;
import main.dto.CategoryResponse;
import main.entity.Category;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class CategoryMapper {
    
    public Category toEntity(CategoryRequest x){
        var category = new Category().setName(x.name());
        x.desc().ifPresent(category::setDesc);
        return category;
    }
    
    public CategoryResponse toDTO(Category c){
        return new CategoryResponse(c.getId(),c.getName(),Optional.ofNullable(c.getDesc()),c.getVersion());
    }
}
