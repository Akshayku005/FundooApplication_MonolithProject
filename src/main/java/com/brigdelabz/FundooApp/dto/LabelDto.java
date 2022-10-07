package com.brigdelabz.FundooApp.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class LabelDto {
	@Pattern(regexp = "^[A-Z]{1,}[a-z\\s]{2,}$", message = "Label name is invalid, first letter should be uppercase!")
	private String labelName;
}