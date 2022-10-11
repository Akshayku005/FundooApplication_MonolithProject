package com.brigdelabz.FundooApp.controller;

import com.brigdelabz.FundooApp.dto.LabelDto;
import com.brigdelabz.FundooApp.dto.NotesDto;
import com.brigdelabz.FundooApp.dto.ResponseDTO;
import com.brigdelabz.FundooApp.model.LabelModel;
import com.brigdelabz.FundooApp.model.Notes;
import com.brigdelabz.FundooApp.service.INotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/notes")
public class NotesController {

	@Autowired
	INotesService notesService;

	/**
	 *
	 * @param notesDto
	 * @param token
	 * @purpose to create notes
	 */
	@PostMapping("/createnote/{token}")
	public ResponseEntity<ResponseDTO> createNote(@Valid@RequestBody NotesDto notesDto, @PathVariable String token) {
		Notes notesModel = notesService.createNote(notesDto,token);
		ResponseDTO response = new ResponseDTO("Note created successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}

	/**
	 *
	 * @param notesDto
	 * @param id
	 * @param token
	 * @purpose to update the notes
	 */
	@PutMapping("/updatenote")
	public ResponseEntity<ResponseDTO> updateNote(@Valid@RequestBody NotesDto notesDto,@RequestHeader Long id,@RequestHeader String token) {
		Notes notesModel = notesService.updateNote(notesDto,id,token);
		ResponseDTO response = new ResponseDTO("Note updated successfully",  notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param token
	 * @purpose to read the notes
	 */
	@GetMapping("/readnotes/{token}")
	public ResponseEntity<ResponseDTO> readAllNotes(@RequestHeader String token){
		List<Notes> notesModel = notesService.readAllNotes(token);
		ResponseDTO response = new ResponseDTO("Fetching all notes successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @purpose to read notes by id
	 */
	@GetMapping("/readnotesbyid")
	public ResponseEntity<ResponseDTO> readNotesById(@RequestHeader Long id,@RequestHeader String token){
		Optional<Notes> notesModel = notesService.readNotesById(id,token);
		ResponseDTO response = new ResponseDTO("Fetching notes by id successfully",notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @return
	 */
	@PutMapping("/archeivenote")
	public ResponseEntity<ResponseDTO> archeiveNoteById(@RequestHeader Long id,@RequestHeader String token) {
		Notes notesModel = notesService.archieveNoteById(id,token);
		ResponseDTO response = new ResponseDTO("Archeived notes by id successfully",  notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @purpose to unarchieve the notes
	 */
	@PutMapping("/unarcheivenote")
	public ResponseEntity<ResponseDTO> unArcheiveNoteById(@RequestHeader Long id,@RequestHeader String token) {
		Notes notesModel = notesService.unArchieveNoteById(id,token);
		ResponseDTO response = new ResponseDTO("Unarcheived notes by id successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @purpose to put the notes to trash
	 */
	@PutMapping("/trashnote")
	public ResponseEntity<ResponseDTO> trashNote(@RequestHeader Long id,@RequestHeader String token) {
		Notes notesModel = notesService.trashNote(id,token);
		ResponseDTO response = new ResponseDTO("Notes added to trash successfully",notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @purpose to restore notes
	 */
	@PutMapping("/restorenote")
	public ResponseEntity<ResponseDTO> restoreNote(@RequestHeader Long id,@RequestHeader String token) {
		Notes notesModel = notesService.restoreNote(id,token);
		ResponseDTO response = new ResponseDTO("Note restored successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @purpose to delete notes
	 */
	@DeleteMapping("/deletenote")
	public ResponseEntity<ResponseDTO> deleteNote(@RequestHeader Long id,@RequestHeader String token) {
		Notes notesModel = notesService.deleteNote(id,token);
		ResponseDTO response = new ResponseDTO("Note deleted successfully",notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param color
	 * @param token
	 * @purpose to change color of the notes
	 */
	@PutMapping("/changenotecolor")
	public ResponseEntity<ResponseDTO> changeNoteColor(@RequestHeader Long id,@RequestParam String color,@RequestHeader  String token) {
		Notes notesModel = notesService.changeNoteColor(id,color,token);
		ResponseDTO response = new ResponseDTO("Note color changed successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @purpose to set pin notes
	 */
	@PutMapping("/pinnote")
	public ResponseEntity<ResponseDTO> pinNote(@RequestHeader Long id,@RequestHeader String token) {
		Notes notesModel = notesService.pinNote(id,token);
		ResponseDTO response = new ResponseDTO("Note pinned successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @purpose to set unpin notes
	 */
	@PutMapping("/unpinnote")
	public ResponseEntity<ResponseDTO> unPinNote(@RequestHeader Long id,@RequestHeader  String token) {
		Notes notesModel = notesService.unPinNote(id,token);
		ResponseDTO response = new ResponseDTO("Note unpinned successfully",notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param token
	 * @purpose to get all pinned notes
	 */
	@GetMapping("/getallpinnednoted")
	public ResponseEntity<ResponseDTO> getAllPinnedNotes(@RequestHeader String token) {
		List<Notes> notesModel = notesService.getAllPinnedNotes(token);
		ResponseDTO response = new ResponseDTO("Fetching all pinned notes successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param token
	 * @purose to get all archieve notes data
	 */
	@GetMapping("/getallarchievednotes")
	public ResponseEntity<ResponseDTO> getAllArchievedNotes(@RequestHeader String token) {
		List<Notes> notesModel = notesService.getAllArchievedNotes(token);
		ResponseDTO response = new ResponseDTO("Fetching all archieved notes sucessfully",notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param token
	 * @purpose to get all trash data
	 */
	@GetMapping("/getalltrashtotes")
	public ResponseEntity<ResponseDTO> getAllTrashNotes(@RequestHeader String token) {
		List<Notes> notesModel = notesService.getAllTrashNotes(token);
		ResponseDTO response = new ResponseDTO("Fetching all notes from trash successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param notesId
	 * @param labelId
	 * @param token
	 * @purpose to display notes and label list
	 */
	@GetMapping("/notesLabelList")
	public ResponseEntity<ResponseDTO> notesLabelList(@RequestParam Long notesId,@RequestParam Long labelId,@RequestParam String token) {
		Notes notesModel = notesService.notesLabelList(notesId,labelId,token);
		ResponseDTO response = new ResponseDTO("notes and label mapping Successfull",  notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param notesId
	 * @param collaborator
	 * @param token
	 * @purpose to collaborate through email
	 */
	@PutMapping("/addcollaborator")
	public ResponseEntity<ResponseDTO> addCollaborator(@RequestParam Long notesId,@RequestHeader String collaborator,@RequestHeader String token ) {
		Notes notesModel = notesService.addCollaborator(notesId,collaborator,token);
		ResponseDTO response = new ResponseDTO("collaborated sucessfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param remainderTime
	 * @param token
	 * @param notesId
	 * @purpose to set remainder
	 */
	@PutMapping("/setremaindertime/{notesId}")
	public ResponseEntity<ResponseDTO> setRemainderTime(@RequestParam String remainderTime,@RequestHeader String token,@PathVariable Long notesId) {
		Notes notesModel = notesService.setRemainderTime(remainderTime,token,notesId);
		ResponseDTO response = new ResponseDTO("Remainder time set sucessfully",  notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@GetMapping("/getcollaboratedmails")
	public ResponseEntity<ResponseDTO> readNotesByCollaborators(@RequestHeader Long notesId, @RequestHeader String email) {
		Optional<Notes> notesModel = notesService.readNotesByCollaborator(notesId,email);
		ResponseDTO response = new ResponseDTO("collaborated mails", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}