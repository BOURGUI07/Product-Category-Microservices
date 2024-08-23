/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import main.client.CategoryApiClient;
import main.dto.ProductRequest;
import main.dto.ProductResponse;
import main.exception.OptimisticLockException;
import main.exception.ProductNameAlreadyExistsException;
import main.exception.ProductNotFoundException;
import main.mapper.ProductMapper;
import main.repo.ProductRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductService {
    ProductRepo repo;
    ProductMapper mapper;
    CategoryApiClient client;
    @NonFinal Validator validator;
    
    public ProductResponse findById(Integer id){
        if(id==null || id<1){
            throw new IllegalArgumentException("Id should be non null and positive");
        }
        return repo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ProductNotFoundException("no product was found for id: " + id));
    }
    
    public List<ProductResponse> findByCategoryId(Integer id){
        if(id==null || id<1){
            throw new IllegalArgumentException("Id should be non null and positive");
        }
        return repo.findByCategoryId(id)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
    
    public Page<ProductResponse> findAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    
    @Transactional
    public ProductResponse create(ProductRequest x){
        var violations = validator.validate(x, ProductRequest.class);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        if(repo.existsByNameIgnoreCase(x.name())){
            throw new ProductNameAlreadyExistsException("product with name: "+ x.name() + " already exists");
        }
        var product = mapper.toEntity(x);
        var saved = repo.save(product);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    public ProductResponse update(Integer id,ProductRequest x){
        if(id==null || id<1){
            throw new IllegalArgumentException("Id should be non null and positive");
        }
        var violations = validator.validate(x, ProductRequest.class);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var product = repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("no category was found for id: " + id))
                .setName(x.name()).setDesc(x.desc().orElse(null))
                .setCategoryId(x.catgeoryId())
                .setPrice(x.price());
        try{
            var saved = repo.save(product);
            var category = client.findById(saved.getCategoryId());
            return new ProductResponse(saved.getId(),saved.getName(),Optional.ofNullable(saved.getDesc()),saved.getPrice()
                                    ,category.id(),category.name(),saved.getVersion());
        }catch(DataIntegrityViolationException e){
            throw new ProductNameAlreadyExistsException("A product with this name already exists.");
        }catch(ObjectOptimisticLockingFailureException e){
            throw new OptimisticLockException("This category has been updated by another user. Please review the changes.");
        }
    }
    
    @Transactional
    public void delete(Integer id){
        if(id==null || id<1){
            throw new IllegalArgumentException("Id should be non null and positive");
        }
        repo.findById(id).ifPresent(repo::delete);
    }
    
    
}
