package com.example.forcookie.payload;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorDto {

    private String message;

    public ErrorDto(){
        super();
    }
}
