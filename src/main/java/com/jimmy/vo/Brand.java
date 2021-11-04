package com.jimmy.vo;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Brand {

    private Long id;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String location;
}
