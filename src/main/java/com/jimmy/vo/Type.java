package com.jimmy.vo;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Type {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Length(max = 255)
    private String description;
}
