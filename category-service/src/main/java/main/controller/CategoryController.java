/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.CategoryRequest;
import main.dto.CategoryResponse;
import main.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:8080")
@Validated
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class CategoryController {
    CategoryService service;
    
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> findByid(@PathVariable Integer categoryId){
        return ResponseEntity.ok(service.findById(categoryId));
    }
    
    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> findAll(
            @RequestParam(required=false,defaultValue="0") int page,
            @RequestParam(required=false,defaultValue="10") int size
    ){
        var result = service.findAll(page, size);
        if(result.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
    
    
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryRequest x){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(x));
    }
    
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Integer categoryId, @RequestBody @Valid CategoryRequest x){
        return ResponseEntity.ok(service.update(categoryId,x));
    }
    
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(@PathVariable Integer categoryId){
        service.delete(categoryId);
        return ResponseEntity.noContent().build();
    }
}
