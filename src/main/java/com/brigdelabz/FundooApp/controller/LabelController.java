package com.brigdelabz.FundooApp.controller;

import com.brigdelabz.FundooApp.dto.LabelDto;
import com.brigdelabz.FundooApp.dto.ResponseDTO;
import com.brigdelabz.FundooApp.model.LabelModel;
import com.brigdelabz.FundooApp.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/label")
public class LabelController {
	
	@Autowired
	ILabelService labelService;
	
	/**
	 * Purpose: Create label
	 * @Param: labelDto,token
	 */
	@PostMapping("/addlabel/{token}")
	public ResponseEntity<ResponseDTO> addLabel(@Valid @RequestBody LabelDto labelDto, @PathVariable String token) {
		LabelModel labelModel = labelService.addLabel(labelDto,token);
		ResponseDTO response = new ResponseDTO("Label created successfully",labelModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: Update labels
	 * @Param: labelDto,id,token
	 */
	@PutMapping("/updatelabel/{id}/{token}")
	public ResponseEntity<ResponseDTO> updateLabel(@Valid@RequestBody LabelDto labelDto,@PathVariable Long id ,@PathVariable  String token) {
		LabelModel labelModel = labelService.updateLabel(labelDto,id,token);
		ResponseDTO response = new ResponseDTO("Label updated successfully", labelModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


/**
 * Purpose: fetch all labels
 * @Param: token
 */
	@GetMapping("/getalllabels/{token}")
	public ResponseEntity<ResponseDTO> getAllLabels(@PathVariable  String token) {
		List<LabelModel> labelModel = labelService.getAllLabels(token);
		ResponseDTO response = new ResponseDTO("Fetching all labels successfully",labelModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose: Delete label by Id
	 * @Param: id,token
	 */
	@DeleteMapping("/deletelabel/{token}")
	public ResponseEntity<ResponseDTO> deleteLabel(@PathVariable Long id,@PathVariable String token) {
		ResponseDTO labelModel = labelService.deleteLabel(id,token);
		ResponseDTO response = new ResponseDTO("Label deleted successfully",  labelModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}