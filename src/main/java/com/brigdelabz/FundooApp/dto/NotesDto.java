package com.brigdelabz.FundooApp.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public @Data class NotesDto {
    @NotBlank(message = "Please fill the title !!")
    private String title;
    @NotEmpty(message = "Description should not be empty !!")
    private String description;
    private Long labelId;
    @Email(message = "Please enter your email correctly")
    private String emailId;
    @NotEmpty(message = "Color should not be empty !!")
    private String color;
}
