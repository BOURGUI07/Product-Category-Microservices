/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.mapper;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.client.CategoryApiClient;
import main.dto.ProductRequest;
import main.dto.ProductResponse;
import main.entity.Product;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class ProductMapper {
    CategoryApiClient client;
    
    public Product toEntity(ProductRequest x){
        var product = new Product()
                .setName(x.name())
                .setPrice(x.price());
        var category = client.findById(x.catgeoryId());
        if(category!=null){
            product.setCategoryId(category.id());
        }
        x.desc().ifPresent(product::setDesc);
        return product;
    }
    
    public ProductResponse toDTO(Product p){
        var category = client.findById(p.getCategoryId());
        return new ProductResponse(p.getId(),p.getName(),Optional.ofNullable(p.getDesc()),p.getPrice()
                                    ,category.id(),category.name(),p.getVersion());
    }
}
