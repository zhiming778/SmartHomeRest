package com.jimmy.vo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Device {

    private Long id;

    @NotBlank
    private String name;

    @Min(0)
    private int manufactureYear;

    @Max(12)
    @Min(0)
    private int manufactureMonth;

    @Max(31)
    @Min(0)
    private int manufactureDay;

    @Min(1)
    private Long typeId;

    @Min(1)
    private Long brandId;

}
