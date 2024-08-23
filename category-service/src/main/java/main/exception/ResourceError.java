/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.exception;

/**
 *
 * @author hp
 */
public record ResourceError(
        int status,
        String message,
        long timeStamp
        ) {

}
