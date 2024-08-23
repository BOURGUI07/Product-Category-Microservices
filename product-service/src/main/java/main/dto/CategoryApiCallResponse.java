/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import java.util.Optional;

/**
 *
 * @author hp
 */
public record CategoryApiCallResponse(
        Integer id,
        String name,
        Optional<String> desc,
        Integer version
        ) {

}
