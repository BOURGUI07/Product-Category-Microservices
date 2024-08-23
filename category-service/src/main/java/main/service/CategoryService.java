/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import main.dto.CategoryRequest;
import main.dto.CategoryResponse;
import main.exception.CategoryNameAlreadyExistsException;
import main.exception.CategoryNotFoundException;
import main.exception.OptimisticLockException;
import main.mapper.CategoryMapper;
import main.repo.CategoryRepo;
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
public class CategoryService {
    CategoryRepo repo;
    CategoryMapper mapper;
    @NonFinal Validator validator;
    
    public CategoryResponse findById(Integer id){
        if(id==null || id<1){
            throw new IllegalArgumentException("Id should be non null and positive");
        }
        return repo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new CategoryNotFoundException("no category was found for id: " + id));
    }
    
    public Page<CategoryResponse> findAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    
    @Transactional
    public CategoryResponse create(CategoryRequest x){
        var violations = validator.validate(x, CategoryRequest.class);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        if(repo.existsByNameIgnoreCase(x.name())){
            throw new CategoryNameAlreadyExistsException("category with name: "+ x.name() + " already exists");
        }
        var category = mapper.toEntity(x);
        var saved = repo.save(category);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    public CategoryResponse update(Integer id,CategoryRequest x){
        if(id==null || id<1){
            throw new IllegalArgumentException("Id should be non null and positive");
        }
        var violations = validator.validate(x, CategoryRequest.class);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var category = repo.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("no category was found for id: " + id))
                .setName(x.name()).setDesc(x.desc().orElse(null));
        try{
            var saved = repo.save(category);
            return mapper.toDTO(saved);
        }catch(DataIntegrityViolationException e){
            throw new CategoryNameAlreadyExistsException("A category with this name already exists.");
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
