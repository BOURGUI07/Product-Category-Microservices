/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Optional;
import main.validation.ValidOptionalString;

/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CategoryRequest(
        @NotBlank
        @Size(min=5,max=100,message="Category Name should be between 5 and 100 characters")
        String name,
        @ValidOptionalString(max=500,message="category description shouldn't exceed 500 characters")
        Optional<String> desc
        ) {

}
