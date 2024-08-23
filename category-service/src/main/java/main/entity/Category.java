/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author hp
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@Table(name="category",
        schema="category_schema",
        indexes={
            @Index(name="idx_category_id",columnList="id"),
            @Index(name="idx_category_name",columnList="name")
        })
@Entity
@DynamicInsert
@DynamicUpdate
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @Column(name="name",nullable=false,unique=true,length=100)
    private String name;
    
    @Column(name="desc",nullable=true,length=500)
    private String desc;
    
}
