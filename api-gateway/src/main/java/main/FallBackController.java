/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 *
 * @author hp
 */
@RestController
public class FallBackController {
    
    @RequestMapping("/categoryFallBack")
    public Mono<String> categoryServiceFallBack(){
        return Mono.just("category service is taking too long to response or is down. Please try again later");
    }
    
    @RequestMapping("/productFallBack")
    public Mono<String> productServiceFallBack(){
        return Mono.just("product service is taking too long to response or is down. Please try again later");
    }
}
