/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.client;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import main.dto.CategoryApiCallResponse;
import main.exception.CustomServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 *
 * @author hp
 */
@Service
@FieldDefaults(makeFinal=true,level=AccessLevel.PRIVATE)
public class CategoryApiClient {
    @NonFinal @Value("${category.api.url}")
    String baseUrl;
    
    RestClient client = RestClient.create(baseUrl);
    
    public CategoryApiCallResponse findById(Integer id){
        return client
                .get()
                .uri("/{categoryId}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request,response) -> {
                     throw new IllegalArgumentException("Either The Client Entered an Id that's below 1 or "
                             + "no category was found for id");
                 })
                .onStatus(HttpStatusCode::is5xxServerError, (request,response) -> {
                     throw new CustomServerException("Server is down");
                 })
                .toEntity(CategoryApiCallResponse.class)
                .getBody();
    }
}
