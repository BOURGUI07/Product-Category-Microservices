/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.repo;

import java.util.List;
import main.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hp
 */
@Repository
public interface ProductRepo extends JpaRepository<Product,Integer>{
    boolean existsByNameIgnoreCase(String productName);
    List<Product> findByCategoryId(Integer categoryId);
    @Override
    Page<Product> findAll(Pageable pageable);
}
