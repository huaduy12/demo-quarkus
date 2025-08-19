package com.example.cim.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDto {
    private Long id;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String type;
    @NotBlank
    private String status;
}
